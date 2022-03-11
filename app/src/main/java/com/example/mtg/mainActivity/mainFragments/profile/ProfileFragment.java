package com.example.mtg.mainActivity.mainFragments.profile;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.mtg.R;
import com.example.mtg.databinding.FragmentProfileBinding;
import com.example.mtg.logActivity.LogActivity;
import com.example.mtg.logActivity.models.UserRegisterProfileModel;
import com.example.mtg.mainActivity.mainFragments.profile.viewModel.ProfileViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Objects;

public class ProfileFragment extends Fragment {

    private FirebaseAuth mAuth;
    private FragmentProfileBinding binding;
    private ProfileViewModel profileViewModel;
    private FirebaseFirestore firebaseFirestore;
    private StorageReference storageReference;
    private ActivityResultLauncher<String> mGetContent;
    private String downloadUrl = "";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGetContent = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                this::uploadFile
        );
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.profileProgressBar.setVisibility(View.VISIBLE);
        initListeners();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        binding.setLifecycleOwner(requireActivity());

        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        profileViewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);
        binding.setViewModel(profileViewModel);

        setData();

        return binding.getRoot();
    }

    private void setData() {
        profileViewModel.getUser().observe(getViewLifecycleOwner(), userRegisterProfileModel -> {
            if (userRegisterProfileModel.getImageUrl() == null || userRegisterProfileModel.getImageUrl().equals("no image") || userRegisterProfileModel.getImageUrl().equals("")) {
                binding.userProfileImage.setImageResource(R.drawable.ic_baseline_person_150);
            } else {
                String img = userRegisterProfileModel.getImageUrl();
                Glide.with(this).load(img).into(binding.userProfileImage);
            }
            binding.profileProgressBar.setVisibility(View.GONE);
        });
    }


    private void initListeners() {
        binding.logOutButton.setOnClickListener(view1 -> showExitDialog());
        binding.userProfileImage.setOnClickListener(view -> {
            mGetContent.launch("image/*");
            binding.profileProgressBar.setVisibility(View.VISIBLE);
        });
        binding.changeProfileImgImage.setOnClickListener(view -> {
            mGetContent.launch("image/*");
            binding.profileProgressBar.setVisibility(View.VISIBLE);
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

    private String fileExtension(Uri uri) {
        ContentResolver contentResolver = requireActivity().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadFile(Uri imageUri) {
        if (imageUri != null) {
//            Glide.with(requireActivity()).load(imageUri).into(binding.userProfileImage);
            new Thread(() -> {
                String nameImg = String.valueOf(System.currentTimeMillis());
                final StorageReference fileReference = storageReference.child(nameImg +
                        "." + fileExtension(imageUri));
                UploadTask  uploadTask =  fileReference.putFile(imageUri);
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
                        firebaseFirestore.collection("users")
                                .document(Objects.requireNonNull(mAuth.getCurrentUser()).getUid())
                                .get().addOnSuccessListener(documentSnapshot -> {
                            UserRegisterProfileModel userRegisterProfileModel = documentSnapshot
                                    .toObject(UserRegisterProfileModel.class);
                            assert userRegisterProfileModel != null;
                            userRegisterProfileModel.setImageUrl("");
                            userRegisterProfileModel.setImageUrl(downloadUrl);
                            firebaseFirestore.collection("users").document(mAuth.getCurrentUser().getUid())
                                    .set(userRegisterProfileModel).addOnCompleteListener(task1 -> {
                                if (task1.isSuccessful()) {
                                    Log.i("MainActivity", "Success");
                                } else {
                                    Log.i("MainActivity", "Failed");
                                }
                            });
                        });
                    } else {
                        Log.i("MainActivity", "Failed");
                    }
                });
            }).start();
            Handler handler = new Handler();
            handler.postDelayed(() -> binding.profileProgressBar.setVisibility(View.GONE), 3000);
        } else {
            Log.i("MainActivity", "empty image uri");
            binding.profileProgressBar.setVisibility(View.GONE);
        }
    }

}