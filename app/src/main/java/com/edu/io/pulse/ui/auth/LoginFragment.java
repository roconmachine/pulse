package com.edu.io.pulse.ui.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.edu.io.pulse.R;
import com.edu.io.pulse.databinding.FragmentLoginBinding;
import com.edu.io.pulse.utils.AppSharedPreference;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    private LoginViewModel loginViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        binding.loginButton.setOnClickListener(v -> {
            String email = binding.emailEditText.getText().toString();
            String password = binding.passwordEditText.getText().toString();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(getContext(), "Please enter email and password", Toast.LENGTH_SHORT).show();
                return;
            }

            binding.loginButton.setEnabled(false);
            binding.loginProgress.setVisibility(View.VISIBLE);
            loginViewModel.login(email, password);
        });

        binding.signupButton.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_auth_login_to_auth_register);
        });

        loginViewModel.getLoginStatus().observe(getViewLifecycleOwner(), success -> {
            binding.loginButton.setEnabled(true);
            binding.loginProgress.setVisibility(View.GONE);
            if (success) {
                // Store username in SharedPreferences
                String username = binding.emailEditText.getText().toString();
                AppSharedPreference.getInstance(requireContext()).saveString("user", username);

                Toast.makeText(getContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_auth_login_to_nav_home);
            }
        });

        loginViewModel.getError().observe(getViewLifecycleOwner(), error -> {
            binding.loginButton.setEnabled(true);
            binding.loginProgress.setVisibility(View.GONE);
            Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
