package com.sklad.er71.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.sklad.er71.Repository.Repository;
import com.sklad.er71.util.Event;


public class ViewModelPassRecover extends AndroidViewModel {
    private final Repository repository;


    @SuppressWarnings({"FieldCanBeLocal"})
    private MutableLiveData<Event<Boolean>> fbRecover = new MutableLiveData<>();
    public ViewModelPassRecover(@NonNull Application application) {
        super(application);
         repository = new Repository();
    }
    public MutableLiveData<Event<Boolean>> getRepository(String email) {
        fbRecover =getFbRecover(email);
        return fbRecover;
    }
    private MutableLiveData<Event<Boolean>> getFbRecover(String email) {
        return repository.getRecover(email);
    }
}