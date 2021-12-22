package com.hikki.masakapanih.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.hikki.masakapanih.model.ResepModel;
import com.hikki.masakapanih.model.ResultsResep;
import com.hikki.masakapanih.networking.ApiService;
import com.hikki.masakapanih.networking.RetroInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

public class KategoriDetailRepository {
    private MutableLiveData<ResepModel> data;

    public KategoriDetailRepository(){
        if(data == null){
            data = new MutableLiveData<>();
        }
    }
    public void reqKategori(String key, int page){
        ApiService api = new RetroInstance().getRetro().create(ApiService.class);
        api.getKategori(key,page).enqueue(new Callback<ResepModel>() {
            @Override
            public void onResponse(Call<ResepModel> call, Response<ResepModel> response) {
                ResepModel model = response.body();
                int i = 1;
                List<ResultsResep> datas = new ArrayList<>();
                for(ResultsResep resep : response.body().getResults()){
                    resep.setTitle(String.valueOf(i)+". "+resep.getTitle());
                    datas.add(resep);
                    i++;
                }
                model.setResults(datas);
                data.postValue(response.body());
            }

            @Override
            public void onFailure(Call<ResepModel> call, Throwable t) {
                data.postValue(null);
            }
        });
    }

    public LiveData<ResepModel> getData(String key, int page){
        reqKategori(key,page);
        return data;

    }
}
