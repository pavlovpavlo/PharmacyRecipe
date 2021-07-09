package com.sklad.er71.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.google.firebase.auth.FirebaseAuth;
import com.sklad.er71.presentation.menu.MenuActivity;
import com.sklad.er71.R;
import com.sklad.er71.util.LocalSharedUtil;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler(Looper.getMainLooper()).postDelayed(this::openNextScreen, 400L);
    }

    private void openNextScreen(){
        if (!LocalSharedUtil.getSnilsParameter(getApplicationContext()).equals("")
                && FirebaseAuth.getInstance().getCurrentUser()!= null){
            startActivity(new Intent(this, MenuActivity.class));
        }else{
            startActivity(new Intent(this, MainActivity.class));
        }
        finish();
    }
}