package com.example.mtg.mainActivity.mainFragments.profile;

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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mtg.R;
import com.example.mtg.databinding.FragmentProfileBinding;
import com.example.mtg.logActivity.LogActivity;
import com.example.mtg.logActivity.models.UserRegisterProfileModel;
import com.example.mtg.mainActivity.countFragment.countModels.AddResultsModel;
import com.example.mtg.mainActivity.countFragment.countModels.DivResultsModel;
import com.example.mtg.mainActivity.countFragment.countModels.MultiResultsModel;
import com.example.mtg.mainActivity.countFragment.countModels.SubResultsModel;
import com.example.mtg.mainActivity.mainFragments.MainFragmentDirections;
import com.example.mtg.mainActivity.mainFragments.profile.viewModel.ProfileViewModel;
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
        profileViewModel.getUser().observe(getViewLifecycleOwner(), userRegisterProfileModel -> {
            if (userRegisterProfileModel.getImageUrl() == null || userRegisterProfileModel.getImageUrl().equals(NO_IMAGE) || userRegisterProfileModel.getImageUrl().equals("")) {
                binding.userProfileImage.setImageResource(R.drawable.ic_baseline_person_150);
            } else {
                String img = userRegisterProfileModel.getImageUrl();
                Glide.with(this).load(img).apply(new RequestOptions().override(170, 170)).into(binding.userProfileImage);
            }
            binding.profileProgressBar.setVisibility(View.GONE);
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
        dialog.setContentView(R.layout.exit_dialog);

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
                        Uri downloadUri = task.getResult();
                        assert downloadUri != null;
                        downloadUrl = downloadUri.toString();
                        /////////////////////////////////////////////////
                        firebaseFirestore.collection(ADD).document(Objects.requireNonNull(mAuth.getCurrentUser()).getUid())
                                .get().addOnSuccessListener(documentSnapshot -> {
                            AddResultsModel addResultsModel = documentSnapshot.toObject(AddResultsModel.class);
                            assert addResultsModel != null;
                            addResultsModel.setImageUrl(downloadUrl);
                            firebaseFirestore.collection(ADD).document(mAuth.getCurrentUser().getUid())
                                    .set(addResultsModel).addOnCompleteListener(task12 -> {
                                if (task12.isSuccessful()) {
                                    Log.i(TAG, SUCCESS);
                                } else {
                                    Log.i(TAG, FAILED);
                                }
                            });
                        });
                        ////////////////////////////////////////////////
                        firebaseFirestore.collection(MULTI).document(mAuth.getCurrentUser().getUid())
                                .get().addOnSuccessListener(documentSnapshot -> {
                            MultiResultsModel multiResultsModel = documentSnapshot.toObject(MultiResultsModel.class);
                            assert multiResultsModel != null;
                            multiResultsModel.setImageUrl(downloadUrl);
                            firebaseFirestore.collection(MULTI).document(mAuth.getCurrentUser().getUid())
                                    .set(multiResultsModel).addOnCompleteListener(task13 -> {
                                if (task13.isSuccessful()) {
                                    Log.i(TAG, SUCCESS);
                                } else {
                                    Log.i(TAG, FAILED);
                                }
                            });
                        });
                        ///////////////////////////////////////////////
                        firebaseFirestore.collection(SUB).document(mAuth.getCurrentUser().getUid())
                                .get().addOnSuccessListener(documentSnapshot -> {
                            SubResultsModel subResultsModel = documentSnapshot.toObject(SubResultsModel.class);
                            assert subResultsModel != null;
                            subResultsModel.setImageUrl(downloadUrl);
                            firebaseFirestore.collection(SUB).document(mAuth.getCurrentUser().getUid())
                                    .set(subResultsModel).addOnCompleteListener(task14 -> {
                                if (task14.isSuccessful()) {
                                    Log.i(TAG, SUCCESS);
                                } else {
                                    Log.i(TAG, FAILED);
                                }
                            });
                        });
                        //////////////////////////////////////////////
                        firebaseFirestore.collection(DIV).document(mAuth.getCurrentUser().getUid())
                                .get().addOnSuccessListener(documentSnapshot -> {
                            DivResultsModel divResultsModel = documentSnapshot.toObject(DivResultsModel.class);
                            assert divResultsModel != null;
                            divResultsModel.setImageUrl(downloadUrl);
                            firebaseFirestore.collection(DIV).document(mAuth.getCurrentUser().getUid())
                                    .set(divResultsModel).addOnCompleteListener(task15 -> {
                                if (task15.isSuccessful()) {
                                    Log.i(TAG, SUCCESS);
                                } else {
                                    Log.i(TAG, FAILED);
                                }
                            });
                        });
                        //////////////////////////////////////////////
                        firebaseFirestore.collection(USERS)
                                .document(Objects.requireNonNull(mAuth.getCurrentUser()).getUid())
                                .get().addOnSuccessListener(documentSnapshot -> {
                            UserRegisterProfileModel userRegisterProfileModel = documentSnapshot
                                    .toObject(UserRegisterProfileModel.class);
                            assert userRegisterProfileModel != null;
                            userRegisterProfileModel.setImageUrl("");
                            userRegisterProfileModel.setImageUrl(downloadUrl);
                            firebaseFirestore.collection(USERS).document(mAuth.getCurrentUser().getUid())
                                    .set(userRegisterProfileModel).addOnCompleteListener(task1 -> {
                                if (task1.isSuccessful()) {
                                    Log.i(TAG, SUCCESS);
                                } else {
                                    Log.i(TAG, FAILED);
                                }
                            });
                            try {
                                requireActivity().runOnUiThread(() -> binding.profileProgressBar.setVisibility(View.GONE));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
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