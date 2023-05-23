package org.adaschool.retrofit.storage;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class JWTInterceptor implements Interceptor {

    private SharedPreferencesStorage tokenStorage;

    public JWTInterceptor(SharedPreferencesStorage sharedPreferencesStorage){
        this.tokenStorage = sharedPreferencesStorage;
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request.Builder request = chain.request().newBuilder();
        String token = tokenStorage.getToken();
        if(token != null){
            request.addHeader("Authorization", "Bearer $token");
        }
        return chain.proceed(request.build());
    }
}
