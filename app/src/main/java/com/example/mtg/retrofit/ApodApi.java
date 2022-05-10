package com.example.mtg.retrofit;

import com.example.mtg.models.apodModel.ApodModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApodApi {
    @GET("apod?api_key=DEMO_KEY&count=20")
    Call<ArrayList<ApodModel>> getApodArray();
}
