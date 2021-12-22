package com.hikki.masakapanih.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.hikki.masakapanih.model.DetailResepModel;
import com.hikki.masakapanih.networking.ApiService;
import com.hikki.masakapanih.networking.RetroInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailResepRepository {
    private MutableLiveData<DetailResepModel> data;

    public DetailResepRepository(){
        if(data == null){
            data = new MutableLiveData<>();
        }
    }

    public void reqData(String key){
        ApiService api = new RetroInstance().getRetro().create(ApiService.class);
        api.getDetailResep(key).enqueue(new Callback<DetailResepModel>() {
            @Override
            public void onResponse(Call<DetailResepModel> call, Response<DetailResepModel> response) {
                response.body().getResults().getAuthor().setUser("By "+response.body().getResults().getAuthor().getUser());
                data.postValue(response.body());
            }

            @Override
            public void onFailure(Call<DetailResepModel> call, Throwable t) {
                data.postValue(null);
            }
        });
    }

    public LiveData<DetailResepModel> getData(String key){
        reqData(key);
        return data;
    }
}
