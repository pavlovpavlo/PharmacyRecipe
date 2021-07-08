package com.sklad.er71.presentation.menu;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.sklad.er71.R;
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

        preferentialPrescription.setOnClickListener(v ->
                startActivity(new Intent(this, PreferentialPrescriptionActivity.class)));
        recipes.setOnClickListener(v ->
                startActivity(new Intent(this, RecipesActivity.class)));
    }
}