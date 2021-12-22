package com.hikki.masakapanih.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.hikki.masakapanih.model.ResepModel;
import com.hikki.masakapanih.repository.KategoriDetailRepository;

public class DetailKategoriViewModel extends ViewModel {

    private LiveData<ResepModel> data;
    private Integer page;
    private KategoriDetailRepository repo;

    public KategoriDetailRepository getRepo(){
        if(repo == null){
            repo = new KategoriDetailRepository();
        }
        return repo;
    }

    public void reqResep(String key,int page){
        data = getRepo().getData(key,page);
    }
    public LiveData<ResepModel> getResepData(String key){
        if(data == null){
            reqResep(key,getPage());
        }
        return data;
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
