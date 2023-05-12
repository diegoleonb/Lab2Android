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
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ActivityMainBinding binding;

    private TextView textView;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        DogApiService dogApiService = RetrofitInstance.getRetrofitInstance().create(DogApiService.class);

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

    private void init(){
        textView = findViewById(R.id.textView);
        imageView = findViewById(R.id.imageView);
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