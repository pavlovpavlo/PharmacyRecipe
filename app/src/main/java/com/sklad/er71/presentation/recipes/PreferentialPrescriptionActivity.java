package com.sklad.er71.presentation.recipes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sklad.er71.Enum.Recipe_SNILS.MTablerowrecipe;
import com.sklad.er71.Enum.Recipe_SNILS.RecipeSNILSResponse;
import com.sklad.er71.R;
import com.sklad.er71.busines.BaseActivity;
import com.sklad.er71.presentation.recipe.RecipeActivity;
import com.sklad.er71.util.LocalSharedUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.sklad.er71.util.Util.METHODNAME;
import static com.sklad.er71.util.Util.NAMESPACE;
import static com.sklad.er71.util.Util.SOAP_ACTION;
import static com.sklad.er71.util.Util.USER_PASSWORD;
import static com.sklad.er71.util.Util.WSDL;

public class PreferentialPrescriptionActivity extends BaseActivity implements RecipesAdapter.OnRecipeClickListener {

    private static String TAG = "soap";
    private RecipesAdapter adapter;
    private RecyclerView recipes;
    private TextView noRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferential_prescription);
        initViews();
    }

    private void initViews() {
        recipes = findViewById(R.id.recipes);
        noRecipes = findViewById(R.id.no_recipes);
        adapter = new RecipesAdapter();
        adapter.setListener(this);

        recipes.setLayoutManager(new LinearLayoutManager(this));

        recipes.setAdapter(adapter);

        startLoader();
        Thread thread = new Thread(() -> {
            try {
                RecipeSNILSResponse obj = GetRecipe_SNILS();

                setData(obj);
            } catch (Exception e) {
                showError(e.getMessage());
                stopLoaderUiThread();
            }


        });

        thread.start();
    }

    private void stopLoaderUiThread() {
        runOnUiThread(this::stopLoader);
    }

    private void setData(RecipeSNILSResponse obj) {
        runOnUiThread(() -> {
            try {
                if (obj.getSoapEnvelope().getSoapBody().getmGetRecipeSNILSResponse().getmReturn().getmTablerowrecipe().size() > 0) {
                    adapter.setList(obj.getSoapEnvelope().getSoapBody().getmGetRecipeSNILSResponse().getmReturn().getmTablerowrecipe());
                    recipes.setVisibility(View.VISIBLE);
                    noRecipes.setVisibility(View.GONE);
                } else {
                    recipes.setVisibility(View.GONE);
                    noRecipes.setVisibility(View.VISIBLE);
                }
            } catch (Exception e) {
                recipes.setVisibility(View.GONE);
                noRecipes.setVisibility(View.VISIBLE);
            }


            stopLoader();
        });
    }

    public RecipeSNILSResponse GetRecipe_SNILS() {
        String responseDump = "";
        RecipeSNILSResponse Recipe = new RecipeSNILSResponse();
        try {
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

            SoapObject request = new SoapObject(NAMESPACE, METHODNAME);
            request.addProperty("SNILS", LocalSharedUtil.getSnilsParameter(getApplicationContext()));


            envelope.bodyOut = request;
            HttpTransportSE transport = new HttpTransportSE(WSDL);
            transport.debug = true;

            List<HeaderProperty> headerList = new ArrayList<HeaderProperty>();
            headerList.add(new HeaderProperty("Authorization", "Basic " + org.kobjects.base64.Base64.encode(USER_PASSWORD.getBytes())));

            try {
                transport.call(SOAP_ACTION, envelope, headerList);
                String requestDump = transport.requestDump;
                responseDump = transport.responseDump;
                Log.e(TAG, requestDump);
                Log.e(TAG, responseDump);


                JSONObject jsonObj = null;
                try {
                    jsonObj = XML.toJSONObject(responseDump);
                } catch (JSONException e) {
                    e.printStackTrace();
                    showError(e.getMessage());
                    stopLoaderUiThread();
                }

                Gson gson = new GsonBuilder().create();

                Recipe = gson.fromJson(jsonObj.toString(), RecipeSNILSResponse.class);


            } catch (IOException e) {
                e.printStackTrace();
                showError(e.getMessage());
                stopLoaderUiThread();
            }

        } catch (Exception e) {
            e.printStackTrace();
            showError(e.getMessage());
            stopLoaderUiThread();
        }
        return Recipe;
    }

    @Override
    public void serviceSelected(MTablerowrecipe item) {
        Intent intent = new Intent(this, RecipeActivity.class);
        intent.putExtra("recipe", item);
        startActivity(intent);
    }
}