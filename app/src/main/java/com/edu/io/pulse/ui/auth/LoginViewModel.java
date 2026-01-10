package com.edu.io.pulse.ui.auth;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.edu.io.pulse.apis.ApiClient;
import com.edu.io.pulse.apis.QuestionBankService;
import com.edu.io.pulse.utils.HashUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {

    private final MutableLiveData<Boolean> _loginStatus = new MutableLiveData<>();
    private final MutableLiveData<String> _error = new MutableLiveData<>();

    public LiveData<Boolean> getLoginStatus() {
        return _loginStatus;
    }

    public LiveData<String> getError() {
        return _error;
    }

    public void login(String username, String password) {
        String hashedPassword = HashUtils.sha1(password);
        if (hashedPassword == null) {
            _error.setValue("Error hashing password");
            return;
        }

        QuestionBankService service = ApiClient.getClient().create(QuestionBankService.class);
        service.login(username, hashedPassword).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    _loginStatus.setValue(true);
                } else {
                    _error.setValue("Login failed: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                _error.setValue("Network error: " + t.getMessage());
            }
        });
    }
}
