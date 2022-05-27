package com.example.mtg.ui.activities.mainActivity.mainFragments.profile;

import static android.app.Activity.RESULT_OK;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.MimeTypeMap;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.Glide;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.mtg.R;
import com.example.mtg.databinding.DialogConnectionBinding;
import com.example.mtg.databinding.FragmentProfileBinding;
import com.example.mtg.ui.activities.logActivity.LogActivity;
import com.example.mtg.ui.activities.mainActivity.mainFragments.MainFragmentDirections;
import com.example.mtg.ui.activities.mainActivity.mainFragments.profile.viewModel.ProfileViewModel;
import com.example.mtg.ui.dialogs.connectionDialog.ConnectionDialog;
import com.example.mtg.utility.networkDetection.NetworkStateManager;
import com.github.drjacky.imagepicker.ImagePicker;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

public class ProfileFragment extends Fragment {

    private FirebaseAuth mAuth;
    private FragmentProfileBinding binding;
    private DialogConnectionBinding connectionBinding;
    private ProfileViewModel profileViewModel;
    private NavController navController;
    private NetworkStateManager networkStateManager;
    private String img = "";
    private ConnectionDialog connectionDialog;
    private static int counter = 0;
    private Dialog dialog;
    private static final String TAG = "MainActivity";
    private static final String FAILED = "Failed";
    private static final String UPLOADING_FAILED = "uploading failed";
    private ActivityResultLauncher<Intent> launcher;
    private static final String UPLOADING_FAILED_EVERYWHERE = "uploading failed everywhere";

    @Override
    public void onStart() {
        super.onStart();
        MeowBottomNavigation meowBottomNavigation = requireActivity().findViewById(R.id.main_bottom_navigation);
        if (meowBottomNavigation.getDefaultIconColor() != R.color.blue) {
            meowBottomNavigation.setBackground(AppCompatResources.getDrawable(requireContext(), R.drawable.gradient_for_bottom_navigation));
            meowBottomNavigation.setDefaultIconColor(Color.parseColor("#112CBF"));
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), (ActivityResult result) -> {
            if (result.getResultCode() == RESULT_OK) {
                assert result.getData() != null;
                Uri uri = result.getData().getData();
                uploadFile(uri);
            } else if (result.getResultCode() == ImagePicker.RESULT_ERROR) {
                Log.i(TAG, FAILED);
            }
        });
        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_container_view);
        navController = Objects.requireNonNull(navHostFragment).getNavController();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        connectionBinding = DialogConnectionBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        profileViewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);
        networkStateManager = NetworkStateManager.getInstance();
        binding.setViewModel(profileViewModel);
        mAuth = FirebaseAuth.getInstance();
        binding.profileProgressBar.setVisibility(View.VISIBLE);
        binding.infoName.setSelected(true);
        binding.infoNickname.setSelected(true);
        binding.infoSurname.setSelected(true);
        binding.infoEmail.setSelected(true);
        binding.userEmail.setSelected(true);
        binding.userNickname.setSelected(true);
        initListeners();
        detectConnection();
        setData();
    }

    private void detectConnection() {
        connectionDialog = new ConnectionDialog(requireContext(), connectionBinding, requireContext().getString(R.string.connection_lost_in_main));
        networkStateManager.getNetworkConnectivityStatus().observe(getViewLifecycleOwner(),
                aBoolean -> {
                    if (!aBoolean) {
                        Log.e(TAG, "LOST Internet");
                        binding.settingsButton.setOnClickListener(view -> openConnectionDialog());
                        binding.userProfileImage.setOnClickListener(view -> openConnectionDialog());
                        binding.changeProfileImgImage.setOnClickListener(view -> openConnectionDialog());
                    } else {
                        Log.e(TAG, "Have Internet");
                        binding.userProfileImage.setOnClickListener(view -> changeImage());
                        binding.changeProfileImgImage.setOnClickListener(view -> changeImage());
                        binding.settingsButton.setOnClickListener(view -> {
                            NavDirections action = MainFragmentDirections.actionMainFragment2ToProfileSettingsFragment();
                            if (Objects.requireNonNull(navController.getCurrentDestination()).getId() == R.id.mainFragment2) {
                                navController.navigate(action);
                            }
                        });
                    }
                });
    }

    private void openConnectionDialog() {
        connectionDialog.show();
        Window window = connectionDialog.getWindow();
        window.setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
    }

    private void setData() {
        profileViewModel.getUser().observe(getViewLifecycleOwner(), data -> {
            binding.profileProgressBar.setVisibility(View.GONE);
            switch (data.status) {
                case SUCCESS:
                    assert data.data != null;
                    img = data.data.getImageUrl();
                    if (!img.isEmpty() && !img.equals("no image")) {
                        String[] strings = img.split("\\?");
                        strings = strings[0].split("/o/");
                        img = strings[1];
                    }
                    break;
                case ERROR:
                    img = "";
                    Log.e(TAG, FAILED);
            }
        });
    }

    private void initListeners() {
        binding.logOutButton.setOnClickListener(view1 -> showExitDialog());
        binding.userProfileImage.setOnClickListener(view -> changeImage());
        binding.changeProfileImgImage.setOnClickListener(view -> changeImage());
        binding.settingsButton.setOnClickListener(view -> {
            NavDirections action = MainFragmentDirections.actionMainFragment2ToProfileSettingsFragment();
            if (Objects.requireNonNull(navController.getCurrentDestination()).getId() == R.id.mainFragment2) {
                navController.navigate(action);
            }
        });
    }

    private void showExitDialog() {
        dialog = new Dialog(requireActivity());
        dialog.setContentView(R.layout.dialog_exit);

        MaterialButton cancel = dialog.findViewById(R.id.cancel_dialog_button);
        cancel.setOnClickListener(view -> dialog.dismiss());

        MaterialButton exit = dialog.findViewById(R.id.exit_dialog_button);
        dialog.show();
        exit.setOnClickListener(view -> {
            mAuth.signOut();
            startActivity(new Intent(getActivity(), LogActivity.class));
            requireActivity().finish();
        });
    }

    private void changeImage() {
        ImagePicker.Companion.with(requireActivity())
                .crop()
                .cropOval()
                .maxResultSize(512, 512, true)
                .createIntentFromDialog(new Function1() {
                    public Object invoke(Object var1) {
                        this.invoke((Intent) var1);
                        return Unit.INSTANCE;
                    }

                    public final void invoke(@NotNull Intent it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        launcher.launch(it);
                    }
                });
    }

    private String fileExtension(Uri uri) {
        ContentResolver contentResolver = requireActivity().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }


    private void uploadFile(Uri imageUri) {
        if (imageUri != null) {
            String extension = fileExtension(imageUri);
            String nameImg;
            if (extension != null) {
                nameImg = System.currentTimeMillis() + "." + extension;
            } else {
                nameImg = System.currentTimeMillis() + "." + "jpg";
            }
            updateImage(imageUri, nameImg);
        }
    }

    private void updateImage(Uri imageUri, String nameImg) {
        binding.profileProgressBar.setVisibility(View.VISIBLE);
        profileViewModel.updateUserImage(imageUri, img, nameImg, userField -> {
            switch (userField.status) {
                case SUCCESS:
                    binding.profileProgressBar.setVisibility(View.GONE);
                    Glide.with(requireContext()).load(imageUri).placeholder(R.drawable.ic_user_main)
                            .error(R.drawable.ic_user_main).into(binding.userProfileImage);
                    break;
                case ERROR:
                    if (userField.message != null) {
                        if (userField.message.equals(UPLOADING_FAILED_EVERYWHERE) || userField.message.equals(UPLOADING_FAILED)) {
                            Log.e(TAG, UPLOADING_FAILED_EVERYWHERE);

                        } else {
                            Log.e(TAG, FAILED);
                            Glide.with(requireContext()).load(imageUri).placeholder(R.drawable.ic_user_main)
                                    .error(R.drawable.ic_user_main).into(binding.userProfileImage);
                        }
                    }
                    binding.profileProgressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        profileViewModel.removeListenerRegistration();
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Boolean isConnect = networkStateManager.getNetworkConnectivityStatus().getValue();
        if (isConnect != null && isConnect && counter > 0) {
            profileViewModel.loadData();
        }
        counter++;
    }

//    implementation 'androidx.emoji:emoji:1.0.0'

//
//    Andrey Skakunenko18:17
//    implementation 'com.github.d-max:spots-dialog:0.7@aar'
//    Andrey Skakunenko18:19
//    implementation 'com.github.ybq:Android-SpinKit:1.4.0'
//    https://jitpack.io/p/ybq/android-SpinKit

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        connectionBinding = null;
        binding = null;
    }

}