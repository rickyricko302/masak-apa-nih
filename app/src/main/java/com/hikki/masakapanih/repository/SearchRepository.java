package com.hikki.masakapanih.repository;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.hikki.masakapanih.model.ResultsSearch;
import com.hikki.masakapanih.model.SearchModel;
import com.hikki.masakapanih.networking.ApiService;
import com.hikki.masakapanih.networking.RetroInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

public class SearchRepository {
    private MutableLiveData<SearchModel> data;
    public SearchRepository(){
        if(data == null) {
            data = new MutableLiveData<>();
        }
    }


    public void reqSearch(String key){
       // Log.d("KEY", "reqSearch: "+key);
        ApiService api = new RetroInstance().getRetro().create(ApiService.class);
        api.getSearch(key).enqueue(new Callback<SearchModel>() {
            @Override
            public void onResponse(Call<SearchModel> call, Response<SearchModel> response) {
               // Log.d("PESAN REQ", "onResponse: "+response.body());
                List<ResultsSearch> datas = new ArrayList<>();
                int i = 1;
                for(ResultsSearch result : response.body().getResults()){
                    if(result.getTitle().toLowerCase().contains(key.toLowerCase())){
                        result.setTitle(String.valueOf(i)+". "+result.getTitle());
                        datas.add(result);
                        i++;
                    }
                }
                SearchModel model = response.body();
                model.setResults(datas);
                data.postValue(model);
            }

            @Override
            public void onFailure(Call<SearchModel> call, Throwable t) {
             //   Log.d("PESAN REQ", "onResponse: NULL");
                data.postValue(null);
            }
        });
    }

    public LiveData<SearchModel> getData(){
        return data;
    }
}
