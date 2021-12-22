package com.hikki.masakapanih.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.hikki.masakapanih.model.DetailResepModel;
import com.hikki.masakapanih.repository.DetailResepRepository;

public class DetailResepViewModel extends ViewModel {
    private LiveData<DetailResepModel> data;

    private DetailResepRepository repo;

    private DetailResepRepository getRepo(){
        if(repo == null){
            repo = new DetailResepRepository();
        }
        return repo;
    }

    public void reqData(String key){
        data = getRepo().getData(key);
    }

    public LiveData<DetailResepModel> getData(String key){
        if(data == null){
            reqData(key);
        }
        return data;
    }
}
