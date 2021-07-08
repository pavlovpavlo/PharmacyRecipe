package com.sklad.er71.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.sklad.er71.Repository.Repository;
import com.sklad.er71.util.Event;


public class ViewModelChangePassword extends AndroidViewModel {
    private final Repository repository;


    @SuppressWarnings({"FieldCanBeLocal"})
    private MutableLiveData<Event<Boolean>> fbNewPas= new MutableLiveData<>();
    public ViewModelChangePassword(@NonNull Application application) {
        super(application);
         repository = new Repository();
    }
    public MutableLiveData< Event<Boolean>> getRepository(String email, String old_pass, String new_pass) {
        fbNewPas =getFbNewPass(email,old_pass,new_pass);
        return fbNewPas;
    }
    private MutableLiveData<Event<Boolean>> getFbNewPass(String email,String old_pass,String new_pass) {
        return repository.setNewPass(email,old_pass,new_pass);
    }
}