package com.hikki.masakapanih.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.hikki.masakapanih.model.ResultsSearch;
import com.hikki.masakapanih.model.SearchModel;
import com.hikki.masakapanih.repository.SearchRepository;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SearchVIewModel extends ViewModel {
    private LiveData<SearchModel> modelLiveData;
    private SearchRepository repo;
    private MutableLiveData<String> key;

    public void init(){
        if(repo == null) {
            repo = new SearchRepository();
        }
        if(key == null){
            key = new MutableLiveData<>();
        }

        modelLiveData = repo.getData();
    }

    public void reqSearch(String key){
        repo.reqSearch(key);
    }

    public LiveData<String> getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key.setValue(key);
    }

    public LiveData<SearchModel> getSearch(){
        return modelLiveData;
    }

}
