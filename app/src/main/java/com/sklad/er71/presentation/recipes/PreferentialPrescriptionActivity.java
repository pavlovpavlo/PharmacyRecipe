package com.sklad.er71.presentation.recipes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sklad.er71.Enum.Recipe_SNILS.MTablerowrecipe;
import com.sklad.er71.Enum.Recipe_SNILS.RecipeSNILSResponse;
import com.sklad.er71.R;
import com.sklad.er71.busines.BaseActivity;
import com.sklad.er71.presentation.menu.MenuActivity;
import com.sklad.er71.presentation.profile.ProfileActivity;
import com.sklad.er71.presentation.recipe.RecipeActivity;
import com.sklad.er71.presentation.recipe.RecipeQRDialog;
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

public class PreferentialPrescriptionActivity extends Fragment implements RecipesAdapter.OnRecipeClickListener {

    private static String TAG = "soap";
    private RecipesAdapter adapter;
    private RecyclerView recipes;
    private TextView noRecipes;
    private MenuActivity mainActivity;
    private View root;
    private NavController controller;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MenuActivity) {
            mainActivity = (MenuActivity) context;
        }
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.activity_preferential_prescription, container, false);

        initViews();

        return root;
    }

    private void initViews() {
        controller = NavHostFragment.findNavController(PreferentialPrescriptionActivity.this);
        recipes = root.findViewById(R.id.recipes);
        noRecipes = root.findViewById(R.id.no_recipes);
        adapter = new RecipesAdapter();
        adapter.setListener(this);

        recipes.setLayoutManager(new LinearLayoutManager(mainActivity));

        recipes.setAdapter(adapter);

        mainActivity.startLoader();
        Thread thread = new Thread(() -> {
            try {
                RecipeSNILSResponse obj = GetRecipe_SNILS();

                setData(obj);
            } catch (Exception e) {
                mainActivity.showError(e.getMessage());
                stopLoaderUiThread();
            }


        });

        thread.start();
    }

    private void stopLoaderUiThread() {
        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mainActivity.stopLoader();
            }
        });
    }

    private void setData(RecipeSNILSResponse obj) {
        mainActivity.runOnUiThread(() -> {
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


            mainActivity.stopLoader();
        });
    }

    public RecipeSNILSResponse GetRecipe_SNILS() {
        String responseDump = "";
        RecipeSNILSResponse Recipe = new RecipeSNILSResponse();
        try {
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

            SoapObject request = new SoapObject(NAMESPACE, METHODNAME);
            request.addProperty("SNILS", LocalSharedUtil.getSnilsParameter(mainActivity));


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
                    mainActivity.showError(e.getMessage());
                    stopLoaderUiThread();
                }

                Gson gson = new GsonBuilder().create();

                Recipe = gson.fromJson(jsonObj.toString(), RecipeSNILSResponse.class);


            } catch (IOException e) {
                e.printStackTrace();
                mainActivity.showError(e.getMessage());
                stopLoaderUiThread();
            }

        } catch (Exception e) {
            e.printStackTrace();
            mainActivity.showError(e.getMessage());
            stopLoaderUiThread();
        }
        return Recipe;
    }

    @Override
    public void serviceSelected(MTablerowrecipe item) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("recipe", item);
        controller.navigate(R.id.fragmentRecipe, bundle);
    }

    @Override
    public void openQr(MTablerowrecipe item) {
        RecipeQRDialog.display(mainActivity.getSupportFragmentManager(), item.getmQRString());
    }
}