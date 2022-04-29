package com.example.mtg.ui.activities.mainActivity.mainFragments.profile;

import static android.app.Activity.RESULT_OK;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import com.example.mtg.R;
import com.example.mtg.databinding.FragmentProfileBinding;
import com.example.mtg.ui.activities.logActivity.LogActivity;
import com.example.mtg.ui.activities.mainActivity.mainFragments.MainFragmentDirections;
import com.example.mtg.ui.activities.mainActivity.mainFragments.profile.viewModel.ProfileViewModel;
import com.github.drjacky.imagepicker.ImagePicker;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

public class ProfileFragment extends Fragment {

    private FirebaseAuth mAuth;
    private FragmentProfileBinding binding;
    private ProfileViewModel profileViewModel;
    private FirebaseFirestore firebaseFirestore;
    private StorageReference storageReference;
    private String downloadUrl = "";
    private NavController navController;
    private String img;
    private static final String TAG = "MainActivity";
    private static final String SUCCESS = "Success";
    private static final String FAILED = "Failed";
    private static final String ADD = "add";
    private static final String DIV = "div";
    private static final String MULTI = "multi";
    private static final String SUB = "sub";
    private static final String USERS = "users";
    private static final String EMPTY_IMAGE = "empty image uri";
    private static final String NO_IMAGE = "no image";
    private ActivityResultLauncher<Intent> launcher;

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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.profileProgressBar.setVisibility(View.VISIBLE);
        setData();
        initListeners();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        binding.setLifecycleOwner(requireActivity());

        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        profileViewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);
        binding.setViewModel(profileViewModel);


        return binding.getRoot();
    }



    private void setData() {
        profileViewModel.getUser().observe(getViewLifecycleOwner(), data -> {
            binding.profileProgressBar.setVisibility(View.GONE);
            switch (data.status){
                case SUCCESS:
                    assert data.data != null;
                    img = data.data.getImageUrl();
                    break;
                case ERROR:
                    img = "no image";
                    Log.e(TAG,FAILED);
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
        Dialog dialog = new Dialog(requireActivity());
        dialog.setContentView(R.layout.dialog_exit);

        MaterialButton cancel = dialog.findViewById(R.id.cancel_dialog_button);
        cancel.setOnClickListener(view -> dialog.dismiss());

        MaterialButton exit = dialog.findViewById(R.id.exit_dialog_button);
        exit.setOnClickListener(view -> {
            mAuth.signOut();
            startActivity(new Intent(getActivity(), LogActivity.class));
            requireActivity().finish();
        });

        dialog.show();
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
        binding.profileProgressBar.setVisibility(View.VISIBLE);
        if (imageUri != null) {
            new Thread(() -> {
                String nameImg = String.valueOf(System.currentTimeMillis());
                final StorageReference fileReference = storageReference.child(nameImg +
                        "." + fileExtension(imageUri));
                UploadTask uploadTask = fileReference.putFile(imageUri);
                uploadTask.continueWithTask(task -> {
                    if (!task.isSuccessful()) {
                        throw Objects.requireNonNull(task.getException());
                    }
                    return fileReference.getDownloadUrl();
                }).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        if (!img.isEmpty() && !img.equals("no image")) {
                            String[] strings = img.split("\\?");
                            strings = strings[0].split("/o/");
                            storageReference.child(strings[1]).delete().addOnCompleteListener(task16 -> {
                                if (task16.isSuccessful()) {
                                    Log.i(TAG, SUCCESS);
                                } else {
                                    Log.i(TAG, FAILED);
                                }
                            });
                        }

                        Uri downloadUri = task.getResult();
                        assert downloadUri != null;
                        downloadUrl = downloadUri.toString();

                        firebaseFirestore.collection(ADD).document(Objects.requireNonNull(mAuth.getCurrentUser()).getUid())
                                .update("imageUrl", downloadUrl);
                        firebaseFirestore.collection(SUB).document(Objects.requireNonNull(mAuth.getCurrentUser()).getUid())
                                .update("imageUrl", downloadUrl);
                        firebaseFirestore.collection(DIV).document(Objects.requireNonNull(mAuth.getCurrentUser()).getUid())
                                .update("imageUrl", downloadUrl);
                        firebaseFirestore.collection(MULTI).document(Objects.requireNonNull(mAuth.getCurrentUser()).getUid())
                                .update("imageUrl", downloadUrl);
                        firebaseFirestore.collection(USERS).document(Objects.requireNonNull(mAuth.getCurrentUser()).getUid())
                                .update("imageUrl", downloadUrl);



                        try {
                            requireActivity().runOnUiThread(() -> binding.profileProgressBar.setVisibility(View.GONE));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else {
                        try {
                            requireActivity().runOnUiThread(() -> binding.profileProgressBar.setVisibility(View.GONE));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }).start();
        } else {
            Log.i(TAG, EMPTY_IMAGE);
            binding.profileProgressBar.setVisibility(View.GONE);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}