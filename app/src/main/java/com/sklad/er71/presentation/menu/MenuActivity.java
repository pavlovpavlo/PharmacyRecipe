package com.sklad.er71.presentation.menu;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.sklad.er71.R;
import com.sklad.er71.presentation.MainActivity;
import com.sklad.er71.presentation.change_password.ChangePasswordActivity;
import com.sklad.er71.presentation.contact_us.ContactUsActivity;
import com.sklad.er71.presentation.recipes.PreferentialPrescriptionActivity;
import com.sklad.er71.presentation.recipes.RecipesActivity;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        initViews();
    }

    private void initViews() {
        LinearLayout preferentialPrescription = findViewById(R.id.preferential_prescription);
        LinearLayout recipes = findViewById(R.id.recipes);
        LinearLayout recover_pass = findViewById(R.id.recover_pass);
        LinearLayout contact_us = findViewById(R.id.contact_us);
        LinearLayout quit = findViewById(R.id.quit);

        preferentialPrescription.setOnClickListener(v ->
                startActivity(new Intent(this, PreferentialPrescriptionActivity.class)));

        recipes.setOnClickListener(v ->
                startActivity(new Intent(this, RecipesActivity.class)));

        recover_pass.setOnClickListener(view -> {
            startActivity(new Intent(this, ChangePasswordActivity.class));
        });

        contact_us.setOnClickListener(view -> {
            startActivity(new Intent(this, ContactUsActivity.class));
        });

        quit.setOnClickListener(view -> {
            startActivity(new Intent(this, MainActivity.class));
            FirebaseAuth.getInstance().signOut();
            finish();
        });


    }
}