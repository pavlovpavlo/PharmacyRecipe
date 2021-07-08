package com.sklad.er71.presentation.recipes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.sklad.er71.R;
import com.sklad.er71.busines.BaseActivity;

public class RecipesActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        initViews();
    }

    private void initViews(){
        RecyclerView recipes = findViewById(R.id.recipes);
        TextView noRecipes = findViewById(R.id.no_recipes);
        recipes.setVisibility(View.GONE);
        noRecipes.setVisibility(View.VISIBLE);
    }
}