package com.sklad.er71.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.sklad.er71.R;
import com.sklad.er71.busines.BaseActivity;
import com.sklad.er71.presentation.login.LoginActivity;
import com.sklad.er71.presentation.reg.RegistrationActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    private void initViews() {
        LinearLayout reg = findViewById(R.id.reg);
        LinearLayout auth = findViewById(R.id.auth);

        reg.setOnClickListener(v ->
                startActivity(new Intent(this, RegistrationActivity.class)));
        auth.setOnClickListener(v ->
                startActivity(new Intent(this, LoginActivity.class)));
    }
}