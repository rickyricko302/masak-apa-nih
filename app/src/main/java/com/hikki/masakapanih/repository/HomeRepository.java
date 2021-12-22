package com.hikki.masakapanih.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.hikki.masakapanih.model.KategoriModel;
import com.hikki.masakapanih.model.ResepModel;
import com.hikki.masakapanih.model.ResultsResep;
import com.hikki.masakapanih.networking.ApiService;
import com.hikki.masakapanih.networking.RetroInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

public class HomeRepository {
    private MutableLiveData<KategoriModel> kategoriLiveData;
    private MutableLiveData<ResepModel> resepLiveDatal;
    public HomeRepository(){
        if(kategoriLiveData == null){
            kategoriLiveData = new MutableLiveData<>();
        }
        if(resepLiveDatal == null){
            resepLiveDatal = new MutableLiveData<>();
        }
    }
    public void reqKategori(){
        ApiService api = new RetroInstance().getRetro().create(ApiService.class);
        api.getKategori().enqueue(new Callback<KategoriModel>() {
            @Override
            public void onResponse(Call<KategoriModel> call, Response<KategoriModel> response) {
                kategoriLiveData.postValue(response.body());
            }

            @Override
            public void onFailure(Call<KategoriModel> call, Throwable t) {
                kategoriLiveData.postValue(null);
            }
        });
    }
    public void reqResep(int page){
        ApiService api = new RetroInstance().getRetro().create(ApiService.class);
        api.getResep(page).enqueue(new Callback<ResepModel>() {
            @Override
            public void onResponse(Call<ResepModel> call, Response<ResepModel> response) {
                ResepModel model = response.body();
                int i = 1;
                List<ResultsResep> data = new ArrayList<>();
                for(ResultsResep resep : response.body().getResults()){
                    resep.setTitle(String.valueOf(i)+". "+resep.getTitle());
                    data.add(resep);
                    i++;
                }
                model.setResults(data);
                resepLiveDatal.postValue(model);
            }

            @Override
            public void onFailure(Call<ResepModel> call, Throwable t) {
                resepLiveDatal.postValue(null);
            }
        });
    }

    public LiveData<KategoriModel> getKategori(){
        reqKategori();
        return kategoriLiveData;
    }

    public LiveData<ResepModel> getResep(int page){
        reqResep(page);
        return resepLiveDatal;
    }
}
