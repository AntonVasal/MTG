package com.example.mtg.ui.activities.logActivity.logFragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.mtg.R;
import com.example.mtg.databinding.FragmentSignInBinding;
import com.example.mtg.ui.activities.logActivity.logViewModel.LogViewModel;
import com.example.mtg.ui.activities.mainActivity.MainActivity;
import com.example.mtg.utility.sharedPreferences.SharedPreferencesHolder;
import com.example.mtg.utility.textwatchers.ValidationTextWatcher;
import com.google.android.material.snackbar.Snackbar;
import com.ramotion.paperonboarding.PaperOnboardingFragment;
import com.ramotion.paperonboarding.PaperOnboardingPage;

import java.util.ArrayList;
import java.util.Objects;


public class SignInFragment extends Fragment {

    private FragmentSignInBinding binding;
    private NavController navController;
    private LogViewModel logViewModel;

//    @Override
//    public void onStart() {
//        super.onStart();
//        if (SharedPreferencesHolder.getInstance(requireContext()).isFirstOpening("isFirstOpening")) {
//            PaperOnboardingPage scr1 = new PaperOnboardingPage("MTG",
//                    "We are happy to see you in our first math app!",
//                    Color.parseColor("#00ddff"), R.drawable.ic_computer_browser, R.drawable.ic_star);
//            PaperOnboardingPage scr2 = new PaperOnboardingPage("Train you skills!",
//                    "We provide you opportunity to train your counting skills by using 12 grounds which generate 48 types of task!",
//                    Color.parseColor("#ffffff"), R.drawable.ic_mathematical, R.drawable.ic_calculator);
//            PaperOnboardingPage scr3 = new PaperOnboardingPage("Compete!",
//                    "You can compete with people all over the world! Become the best counter!",
//                    Color.parseColor("#00ddff"), R.drawable.ic_trophy, R.drawable.ic_trophy_pixel);
//            ArrayList<PaperOnboardingPage> elements = new ArrayList<>();
//            elements.add(scr1);
//            elements.add(scr2);
//            elements.add(scr3);
//
//            PaperOnboardingFragment onBoardingFragment = PaperOnboardingFragment.newInstance(elements);
//
//            FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
//            fragmentTransaction.add(R.id.sign_container, onBoardingFragment);
//            fragmentTransaction.commit();
//
//            onBoardingFragment.setOnRightOutListener(() -> requireActivity().getSupportFragmentManager().beginTransaction()
//                    .setCustomAnimations(R.anim.fragment_horizontal_close_animation,
//                            R.anim.fragment_to_left,
//                            R.anim.fragment_horizontal_open_animation,
//                            R.anim.fragment_to_right)
//                    .remove(onBoardingFragment).commit());
//        }
//    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.log_fragment_container_view);
        navController = Objects.requireNonNull(navHostFragment).getNavController();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignInBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        logViewModel = new ViewModelProvider(requireActivity()).get(LogViewModel.class);
        initListeners();
        textSelected();
    }

    private void textSelected() {
        ValidationTextWatcher passwordTextWatcher = new ValidationTextWatcher(binding.signInPasswordEditText);
        binding.password.addTextChangedListener(passwordTextWatcher);
        ValidationTextWatcher emailTextWatcher = new ValidationTextWatcher(binding.signInEmailEditText);
        binding.email.addTextChangedListener(emailTextWatcher);
    }

    private void initListeners() {
        binding.registerTextView.setOnClickListener(view -> {
            clearTextView();
            navController.navigate(R.id.action_signInFragment_to_registerFragment);
        });

        binding.signInButton.setOnClickListener(view -> userLogin());
        binding.resetPassword.setOnClickListener(view -> {
            clearTextView();
            navController.navigate(R.id.action_signInFragment_to_resetPasswordFragment);
        });
    }

    private void clearTextView() {
        binding.email.setText("");
        binding.password.setText("");
    }

    private void userLogin() {
        String email = Objects.requireNonNull(binding.email.getText()).toString().trim();
        String password = Objects.requireNonNull(binding.password.getText()).toString().trim();
        if (email.isEmpty()) {
            setEmailError(getResources().getString(R.string.email_is_required));
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            setEmailError(getResources().getString(R.string.pls_provide_valid_email));
            return;
        }
        if (password.isEmpty()) {
            setPasswordError(getResources().getString(R.string.password_is_required));
            return;
        }
        if (password.length() < 6) {
            setPasswordError(getResources().getString(R.string.min_password));
            return;
        }
        binding.signInProgressBar.setVisibility(View.VISIBLE);
        logViewModel.authUser(email, password, status -> {
            switch (status) {
                case SUCCESS:
                    binding.signInProgressBar.setVisibility(View.GONE);
                    startActivity(new Intent(getActivity(), MainActivity.class));
                    requireActivity().finish();
                    break;
                case ERROR:
                    binding.signInProgressBar.setVisibility(View.GONE);
                    setPasswordError(getResources().getString(R.string.check_your_email_and_password));
                    Snackbar snackbar = Snackbar.make(binding.getRoot(), R.string.confirmation_failed, Snackbar.LENGTH_LONG);
                    snackbar.show();
            }
        });
    }

    private void setPasswordError(String error) {
        binding.signInPasswordEditText.setError(error);
        binding.signInPasswordEditText.requestFocus();
    }

    private void setEmailError(String error) {
        binding.signInEmailEditText.setError(error);
        binding.signInEmailEditText.requestFocus();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}