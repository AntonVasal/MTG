package com.example.mtg.MainActivity.MainFragments.Profile;

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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.mtg.LogActivity.LogActivity;
import com.example.mtg.LogActivity.Models.UserRegisterProfileModel;
import com.example.mtg.MainActivity.MainFragments.Profile.ViewModel.ProfileViewModel;
import com.example.mtg.R;
import com.example.mtg.databinding.FragmentProfileBinding;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

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
        profileViewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);
        setData();
        initListeners();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false);

        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference("images");



        return binding.getRoot();
    }

    private void setData() {
        profileViewModel.getUser().observe(getViewLifecycleOwner(), userRegisterProfileModel -> {
            binding.userNickname.setText(userRegisterProfileModel.getNickname());
            binding.userEmail.setText(userRegisterProfileModel.getEmail());
            binding.infoCountry.setText(userRegisterProfileModel.getCountry());
            binding.infoEmail.setText(userRegisterProfileModel.getEmail());
            binding.infoName.setText(userRegisterProfileModel.getName());
            binding.infoSurname.setText(userRegisterProfileModel.getSurname());
            binding.infoNickname.setText(userRegisterProfileModel.getNickname());
            if(userRegisterProfileModel.getImageUrl() == null || userRegisterProfileModel.getImageUrl().equals("no image") ){
                binding.userProfileImage.setImageResource(R.drawable.ic_baseline_person_150);
            }else{
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
            new Thread(() -> {
                StorageReference fileReference = storageReference.child(System.currentTimeMillis() +
                        "." + fileExtension(imageUri));
                fileReference.putFile(imageUri)
                        .addOnSuccessListener(taskSnapshot -> {
                                    fileReference.getDownloadUrl().addOnSuccessListener(uri -> downloadUrl = uri.toString());
                                    firebaseFirestore.collection("users")
                                            .document(Objects.requireNonNull(mAuth.getCurrentUser()).getUid())
                                            .get().addOnSuccessListener(documentSnapshot -> {
                                                UserRegisterProfileModel userRegisterProfileModel = documentSnapshot
                                                        .toObject(UserRegisterProfileModel.class);
                                        assert userRegisterProfileModel != null;
                                        userRegisterProfileModel.setImageUrl("");
                                        userRegisterProfileModel.setImageUrl(downloadUrl);
                                        firebaseFirestore.collection("users").document(mAuth.getCurrentUser().getUid())
                                                .set(userRegisterProfileModel).addOnCompleteListener(task -> {
                                                    if(task.isSuccessful()){
                                                        Log.i("MainActivity","Success");
                                                    }else {
                                                        Log.i("MainActivity","Failed");
                                                    }
                                        });
                                    });
                                }
                        );
            }).start();
        } else {
            Log.i("MainActivity", "empty image uri");
        }
        Handler handler = new Handler();
        handler.postDelayed((Runnable) () -> binding.profileProgressBar.setVisibility(View.GONE),3000);

    }

}