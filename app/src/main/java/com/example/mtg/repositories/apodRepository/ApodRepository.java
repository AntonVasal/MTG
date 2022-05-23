package com.example.mtg.repositories.apodRepository;

import androidx.annotation.NonNull;

import com.example.mtg.App;
import com.example.mtg.models.apodModel.ApodModel;
import com.example.mtg.repositories.errorHandlerResourse.ErrorHandlingRepositoryData;
import com.example.mtg.repositories.repositoryCallbacks.ApodListCallback;
import com.example.mtg.retrofit.ApodApi;
import com.example.mtg.retrofit.RetrofitClient;

import java.util.ArrayList;

import io.reactivex.functions.BiConsumer;
import io.reactivex.observers.DisposableSingleObserver;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ApodRepository {
    private static final String FAILED = "Failed";
    public static final String BASE_URl = "https://api.nasa.gov/planetary/";
    Retrofit retrofit;
    ApodApi apodApi;

    public ApodRepository(){
        retrofit = RetrofitClient.getInstance(BASE_URl,App.instance);
        apodApi = retrofit.create(ApodApi.class);
    }

    public void getApodLists(ApodListCallback callback){
        new Thread(() -> apodApi.getApodArray().subscribe((apodModels, throwable) -> {
            if (throwable!=null){
                callback.apodListFromRepository(ErrorHandlingRepositoryData.error(FAILED,null));
            }else{
                callback.apodListFromRepository(ErrorHandlingRepositoryData.success(apodModels));
            }
        })).start();

//                new Callback<ArrayList<ApodModel>>() {
//            @Override
//            public void onResponse(@NonNull Call<ArrayList<ApodModel>> call, @NonNull Response<ArrayList<ApodModel>> response) {
//                if (response.isSuccessful()){
//                    assert response.body() != null;
//                    callback.apodListFromRepository(ErrorHandlingRepositoryData.success(response.body()));
//                }else{
//                    callback.apodListFromRepository(ErrorHandlingRepositoryData.error(FAILED,null));
//                }
//            }
//            @Override
//            public void onFailure(@NonNull Call<ArrayList<ApodModel>> call, @NonNull Throwable t) {
//                callback.apodListFromRepository(ErrorHandlingRepositoryData.error(FAILED,null));
//            }
//        });
    }
}
