package com.example.app_truyen_do_an.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Truyenviewmodel extends ViewModel {
    public final MutableLiveData<truyen> truyenchitiet = new MutableLiveData<>();
    public LiveData<truyen> gettruyenchitiet(){
        return truyenchitiet;
    }
    public void setTruyenchitiet(truyen truyenObj){
        truyenchitiet.setValue(truyenObj);
    }
}
