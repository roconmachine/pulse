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

import com.edu.io.pulse.databinding.FragmentRegisterBinding;

public class RegisterFragment extends Fragment {

    private FragmentRegisterBinding binding;
    private RegisterViewModel registerViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        binding.registerButton.setOnClickListener(v -> {
            String username = binding.usernameEditText.getText().toString();
            String fullName = binding.fullnameEditText.getText().toString();
            String email = binding.emailEditText.getText().toString();
            String password = binding.passwordEditText.getText().toString();

            if (username.isEmpty() || fullName.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            binding.registerButton.setEnabled(false);
            binding.registerProgress.setVisibility(View.VISIBLE);
            registerViewModel.register(username, fullName, email, password);
        });

        registerViewModel.getRegisterStatus().observe(getViewLifecycleOwner(), success -> {
            binding.registerButton.setEnabled(true);
            binding.registerProgress.setVisibility(View.GONE);
            if (success) {
                Toast.makeText(getContext(), "Registration Successful", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(view).popBackStack();
            }
        });

        registerViewModel.getError().observe(getViewLifecycleOwner(), error -> {
            binding.registerButton.setEnabled(true);
            binding.registerProgress.setVisibility(View.GONE);
            Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
