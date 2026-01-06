package com.edu.io.pulse.apis;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.util.concurrent.TimeUnit;

public class ApiClient {

    private static final String BASE_URL = "http://10.0.2.2:8080/";
    private static Retrofit retrofit = null;

    private ApiClient() {}


    public static Retrofit getClient() {
        if (retrofit == null) {
            synchronized (ApiClient.class) {
                if (retrofit == null) {

                    // 1. Create the Logging Interceptor
                    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                    // Level.BODY shows headers, request body, and response body
                    logging.setLevel(HttpLoggingInterceptor.Level.BODY);

                    // 2. Create the OkHttpClient and add the interceptor
                    OkHttpClient client = new OkHttpClient.Builder()
                            .addInterceptor(logging)
                            .connectTimeout(30, TimeUnit.SECONDS) // Optional: increase timeouts
                            .readTimeout(30, TimeUnit.SECONDS)
                            .build();

                    // 3. Build Retrofit using the OkHttpClient
                    retrofit = new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(client) // Connect the client here
                            .build();
                }
            }
        }
        return retrofit;
    }
}
