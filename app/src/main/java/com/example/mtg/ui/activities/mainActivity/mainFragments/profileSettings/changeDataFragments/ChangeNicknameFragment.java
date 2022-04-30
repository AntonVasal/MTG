package com.example.mtg.ui.activities.mainActivity.mainFragments.profileSettings.changeDataFragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.mtg.R;
import com.example.mtg.core.ValidationTextWatcher;
import com.example.mtg.databinding.FragmentChangeDataBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;


public class ChangeNicknameFragment extends Fragment {

    private FragmentChangeDataBinding binding;
    private NavController navController;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private static final String TAG = "MainActivity";
    private static final String SUCCESS = "Success";
    private static final String FAILED = "Failed";
    private static final String USERS = "users";
    private static final String ADD = "add";
    private static final String DIV = "div";
    private static final String MULTI = "multi";
    private static final String SUB = "sub";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_container_view);
        navController = Objects.requireNonNull(navHostFragment).getNavController();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChangeDataBinding.inflate(inflater, container, false);
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListeners();
        setViewData();
        textChanged();
    }

    private void textChanged() {
        ValidationTextWatcher textWatcher = new ValidationTextWatcher(binding.changeEditText);
        binding.forChange.addTextChangedListener(textWatcher);
    }


    private void setViewData() {
        binding.changeEditText.setHint(R.string.nickname);
        binding.changeEditText.setStartIconDrawable(R.drawable.ic_baseline_star_24);
        binding.changeButton.setText(R.string.change_nickname);
    }

    private void initListeners() {
        binding.changeBackButton.setOnClickListener(view -> {
            if (Objects.requireNonNull(navController.getCurrentDestination()).getId() == R.id.changeNicknameFragment) {
                navController.popBackStack();
            }
        });

        binding.changeButton.setOnClickListener(view -> {
            if (Objects.requireNonNull(binding.forChange.getText()).toString().trim().isEmpty()) {
                binding.changeEditText.setError(getResources().getString(R.string.nickname_is_required));
                binding.changeEditText.requestFocus();
                return;
            }
            binding.changeDataProgressBar.setVisibility(View.VISIBLE);
            String nickname = binding.forChange.getText().toString().trim();
            String id = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
            new Thread(() -> sendNicknameToFirestore(nickname, id)).start();
        });

    }

    private void sendNicknameToFirestore(String nickname, String id) {
        firebaseFirestore.collection(USERS).document(id)
                .update("nickname", nickname)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        sendNicknameToCountFirestore(nickname, id);
                        if (Objects.requireNonNull(navController.getCurrentDestination()).getId() == R.id.changeNicknameFragment) {
                            requireActivity().runOnUiThread(() -> {
                                binding.changeDataProgressBar.setVisibility(View.GONE);
                                navController.popBackStack(R.id.profileSettingsPasswordConfirmationFragment, true);
                            });
                        }
                    } else {
                        requireActivity().runOnUiThread(() -> binding.changeDataProgressBar.setVisibility(View.GONE));
                    }
                });

    }

    private void sendNicknameToCountFirestore(String nickname, String id) {
        firebaseFirestore.collection(ADD).document(id)
                .update("nickname", nickname)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.i(TAG, SUCCESS);
                    } else {
                        Log.i(TAG, FAILED);
                    }
                });

        firebaseFirestore.collection(DIV).document(id)
                .update("nickname", nickname)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.i(TAG, SUCCESS);
                    } else {
                        Log.i(TAG, FAILED);
                    }
                });


        firebaseFirestore.collection(SUB).document(id)
                .update("nickname", nickname)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.i(TAG, SUCCESS);
                    } else {
                        Log.i(TAG, FAILED);
                    }
                });


        firebaseFirestore.collection(MULTI).document(id)
                .update("nickname",nickname)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.i(TAG, SUCCESS);
                    } else {
                        Log.i(TAG, FAILED);
                    }
                });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}