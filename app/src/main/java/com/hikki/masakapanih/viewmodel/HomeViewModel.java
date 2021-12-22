package com.hikki.masakapanih.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.hikki.masakapanih.model.KategoriModel;
import com.hikki.masakapanih.model.ResepModel;
import com.hikki.masakapanih.repository.HomeRepository;

public class HomeViewModel extends ViewModel {
    private LiveData<KategoriModel> dataKategori;
    private LiveData<ResepModel> dataResep;
    private Integer page;
    private HomeRepository repository;

    private HomeRepository getRepo(){
        if(repository == null){
            repository = new HomeRepository();
        }
        return repository;
    }
    public void reqResep(){
        dataResep = getRepo().getResep(getPage());
    }

    public void reqkategori(){
        dataKategori = getRepo().getKategori();
    }

    public LiveData<KategoriModel> getKategoriData(){
        if(dataKategori == null){
           reqkategori();
        }
        return dataKategori;
    }
    public LiveData<ResepModel> getResepData(){
        if(dataResep == null){
            reqResep();
        }
        return dataResep;
    }

    public void setPage(int page){
        this.page = page;
    }
    public Integer getPage(){
        if(page == null){
            page = 1;
        }
        return page;
    }
}
