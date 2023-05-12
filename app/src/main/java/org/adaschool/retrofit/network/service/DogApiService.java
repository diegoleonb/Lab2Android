package org.adaschool.retrofit.network.service;

import android.media.Image;

import org.adaschool.retrofit.network.dto.BreedsImgDto;
import org.adaschool.retrofit.network.dto.BreedsListDto;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DogApiService {
    @GET("api/breeds/list/all")
    Call<BreedsListDto> getAllBreeds();

    @GET("api/breed/pitbull/images/random")
    Call<BreedsImgDto> getImage();
}
