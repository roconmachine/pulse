package com.edu.io.pulse.ui.auth;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.edu.io.pulse.apis.ApiClient;
import com.edu.io.pulse.apis.QuestionBankService;
import com.edu.io.pulse.apis.models.UserRegistration;
import com.edu.io.pulse.utils.HashUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterViewModel extends ViewModel {

    private final MutableLiveData<Boolean> _registerStatus = new MutableLiveData<>();
    private final MutableLiveData<String> _error = new MutableLiveData<>();
    private final MutableLiveData<Boolean> _usernameExists = new MutableLiveData<>();

    public LiveData<Boolean> getRegisterStatus() {
        return _registerStatus;
    }

    public LiveData<String> getError() {
        return _error;
    }

    public LiveData<Boolean> getUsernameExists() {
        return _usernameExists;
    }

    public void register(String username, String fullName, String email, String password) {
        QuestionBankService service = ApiClient.getClient().create(QuestionBankService.class);
        
        // First check if username exists
        service.checkUsername(username).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body()) {
                        _usernameExists.setValue(true);
                        _error.setValue("Username already exists");
                    } else {
                        // Username available, proceed with registration
                        performRegistration(service, username, fullName, email, password);
                    }
                } else {
                    _error.setValue("Error checking username: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                _error.setValue("Network error: " + t.getMessage());
            }
        });
    }

    private void performRegistration(QuestionBankService service, String username, String fullName, String email, String password) {
        String hashedPassword = HashUtils.sha1(password);
        if (hashedPassword == null) {
            _error.setValue("Error hashing password");
            return;
        }

        UserRegistration registration = new UserRegistration(username, fullName, email, hashedPassword);
        service.register(registration).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    _registerStatus.setValue(true);
                } else {
                    _error.setValue("Registration failed: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                _error.setValue("Network error: " + t.getMessage());
            }
        });
    }
}
