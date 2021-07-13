package com.sklad.er71.Repository;

import android.location.Location;
import android.net.Uri;


import com.sklad.er71.Enum.ResiduesPharm.ResiduesPharmResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SingletonClassApp implements Serializable {

    private static volatile SingletonClassApp sSoleInstance;

    public ResiduesPharmResponse recipe=new ResiduesPharmResponse();



    //private constructor.
    private SingletonClassApp(){
        //Prevent form the reflection api.
        if (sSoleInstance != null){
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }




    public static SingletonClassApp getInstance() {
        if (sSoleInstance == null) { //if there is no instance available... create new one
            synchronized (SingletonClassApp.class) {
                if (sSoleInstance == null) sSoleInstance = new SingletonClassApp();
            }
        }
        return sSoleInstance;
    }

    //Make singleton from serialize and deserialize operation.
    protected SingletonClassApp readResolve() {
        return getInstance();
    }
}
