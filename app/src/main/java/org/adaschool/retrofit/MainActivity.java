package org.adaschool.retrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.bumptech.glide.Glide;

import org.adaschool.retrofit.databinding.ActivityMainBinding;

import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.adaschool.retrofit.network.RetrofitInstance;
import org.adaschool.retrofit.network.dto.BreedsImgDto;
import org.adaschool.retrofit.network.dto.BreedsListDto;
import org.adaschool.retrofit.network.service.DogApiService;
import org.adaschool.retrofit.storage.EncryptedStorage;
import org.adaschool.retrofit.storage.JWTInterceptor;
import org.adaschool.retrofit.storage.SharedPreferencesStorage;

import java.security.NoSuchAlgorithmException;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ActivityMainBinding binding;

    final String SHARED_PREFERENCES_FILE_NAME = "my_prefs";

    private TextView textView;
    private ImageView imageView;

    private SharedPreferencesStorage sharedPreferencesStorage;

    private EncryptedStorage encryptedStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            init();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        DogApiService dogApiService = RetrofitInstance.getRetrofitInstance(new JWTInterceptor(encryptedStorage)).create(DogApiService.class);

        Call<BreedsImgDto> img = dogApiService.getImage();
        img.enqueue(new Callback<BreedsImgDto>() {
            @Override
            public void onResponse(Call<BreedsImgDto> call, Response<BreedsImgDto> response) {
                if (response.isSuccessful()) {
                    loadDogInfo(response.body().getMessage());
                } else {
                    Log.e(TAG, "Error en la respuesta de la API");
                }
            }

            @Override
            public void onFailure(Call<BreedsImgDto> call, Throwable t) {
                Log.e(TAG, "Error al llamar a la API", t);
            }
        });

        Call<BreedsListDto> call = dogApiService.getAllBreeds();
        call.enqueue(new Callback<BreedsListDto>() {
            @Override
            public void onResponse(Call<BreedsListDto> call, Response<BreedsListDto> response) {
                if (response.isSuccessful()) {
                    Map<String, String[]> breeds = response.body().getMessage();
                    for (Map.Entry<String, String[]> entry : breeds.entrySet()) {
                        Log.d(TAG, "Raza: " + entry.getKey());
                        for (String subRaza : entry.getValue()) {
                            Log.d(TAG, "Subraza: " + subRaza);
                        }
                    }
                } else {
                    Log.e(TAG, "Error en la respuesta de la API");
                }
            }

            @Override
            public void onFailure(Call<BreedsListDto> call, Throwable t) {
                Log.e(TAG, "Error al llamar a la API", t);
            }
        });
    }

    private void init() throws NoSuchAlgorithmException {
        textView = findViewById(R.id.textView);
        imageView = findViewById(R.id.imageView);
        sharedPreferencesStorage = new SharedPreferencesStorage(getSharedPreferences(SHARED_PREFERENCES_FILE_NAME,MODE_PRIVATE));
        encryptedStorage = new EncryptedStorage(getSharedPreferences(SHARED_PREFERENCES_FILE_NAME,MODE_PRIVATE));
    }
    private void loadDogInfo(String image) {
        String dogName = "Pitbull";
        textView.setText(dogName);
        Glide.with(this)
                .load(image)
                .centerCrop()
                .into(imageView);
    }


}