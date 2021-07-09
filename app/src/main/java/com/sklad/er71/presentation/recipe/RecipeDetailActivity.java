package com.sklad.er71.presentation.recipe;

import android.os.Bundle;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sklad.er71.Enum.ResiduesPharm.MReturn;
import com.sklad.er71.R;
import com.sklad.er71.busines.BaseActivity;

public class RecipeDetailActivity extends BaseActivity {

    private RecipeDetailAdapter adapter;
    private MReturn mReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        initViews();
    }

    private void initViews(){
        mReturn = (MReturn) getIntent().getSerializableExtra("recipe");

        RecyclerView recipes = findViewById(R.id.recipes);
        TextView noRecipes = findViewById(R.id.no_recipes);
        adapter = new RecipeDetailAdapter();

        recipes.setLayoutManager(new LinearLayoutManager(this));

        recipes.setAdapter(adapter);
        adapter.setList(mReturn.getmTablerowapp());

    }
}