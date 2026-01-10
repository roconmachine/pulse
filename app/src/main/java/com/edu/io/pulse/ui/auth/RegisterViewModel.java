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

    public LiveData<Boolean> getRegisterStatus() {
        return _registerStatus;
    }

    public LiveData<String> getError() {
        return _error;
    }

    public void register(String username, String fullName, String email, String password) {
        String hashedPassword = HashUtils.sha1(password);
        if (hashedPassword == null) {
            _error.setValue("Error hashing password");
            return;
        }

        UserRegistration registration = new UserRegistration(username, fullName, email, hashedPassword);
        QuestionBankService service = ApiClient.getClient().create(QuestionBankService.class);
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
