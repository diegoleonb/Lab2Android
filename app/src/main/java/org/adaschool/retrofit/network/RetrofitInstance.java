package org.adaschool.retrofit.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.adaschool.retrofit.storage.JWTInterceptor;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    private static final String BASE_URL = "https://dog.ceo/";
    private static Retrofit retrofit;

    public static Retrofit getRetrofitInstance(JWTInterceptor jwtInterceptor) {
        if (retrofit == null) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .addInterceptor(jwtInterceptor)
                    .writeTimeout(0, TimeUnit.MILLISECONDS)
                    .readTimeout(2, TimeUnit.MINUTES)
                    .connectTimeout(1, TimeUnit.MINUTES).build();

            Gson gsonBuilder = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ssX")
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gsonBuilder))
                    .build();
        }
        return retrofit;
    }
}
