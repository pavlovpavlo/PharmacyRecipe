package com.sklad.er71.presentation.recipe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sklad.er71.Enum.Recipe_SNILS.MTablerowrecipe;
import com.sklad.er71.Enum.Recipe_SNILS.RecipeSNILSResponse;
import com.sklad.er71.Enum.ResiduesPharm.ResiduesPharmResponse;
import com.sklad.er71.R;
import com.sklad.er71.busines.BaseActivity;

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

import static android.content.ContentValues.TAG;
import static com.sklad.er71.util.Util.METHODNAME_NEXT;
import static com.sklad.er71.util.Util.NAMESPACE;
import static com.sklad.er71.util.Util.SOAP_ACTION_NEXT;
import static com.sklad.er71.util.Util.USER_PASSWORD;
import static com.sklad.er71.util.Util.WSDL;

public class RecipeActivity extends BaseActivity {

    private MTablerowrecipe item;
    private TextView recipe;
    private LinearLayout map;
    private LinearLayout qr;
    private LinearLayout detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        recipe = findViewById(R.id.recipe);
        map = findViewById(R.id.map);
        qr = findViewById(R.id.qr);
        detail = findViewById(R.id.detail);
        item = (MTablerowrecipe) getIntent().getSerializableExtra("recipe");

        recipe.setText(item.toString());
        map.setOnClickListener(v -> {
            Uri gmmIntentUri = Uri.parse("https://www.google.com/maps/dir/?api=1&destination="
                    + item.getmLPU() + "&travelmode=driving");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        });

        qr.setOnClickListener(v -> RecipeQRDialog.display(getSupportFragmentManager(), item.getmQRString()));

        startLoader();
        Thread thread = new Thread(() -> {
            try {
                ResiduesPharmResponse obj = ResiduesPharm();

                setData(obj);
            } catch (Exception e) {
                showError(e.getMessage());
                stopLoaderUiThread();
            }
        });

        thread.start();

    }

    private void setData(ResiduesPharmResponse obj) {
        runOnUiThread(() -> {
            if (obj.getSoapEnvelope().getSoapBody().getmResiduesPharmResponse().getmReturn().getmTablerowapp() != null) {

                detail.setVisibility(View.VISIBLE);
                detail.setOnClickListener(v -> {
                    Intent intent = new Intent(this, RecipeDetailActivity.class);
                    intent.putExtra("recipe", obj.getSoapEnvelope().getSoapBody().getmResiduesPharmResponse().getmReturn());
                    startActivity(intent);
                });
            }else{
                detail.setVisibility(View.GONE);
                //recipe.setText("Нет данных о рецепте");
            }
            stopLoader();
        });
    }

    private void stopLoaderUiThread() {
        runOnUiThread(this::stopLoader);
    }

    public ResiduesPharmResponse ResiduesPharm() {
        ResiduesPharmResponse Recipe = new ResiduesPharmResponse();
        String responseDump = "";
        try {
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

            SoapObject request = new SoapObject(NAMESPACE, METHODNAME_NEXT);
            request.addProperty("date", item.getmData());
            request.addProperty("serial", "0" + item.getmSeries());
            request.addProperty("number", item.getmNumber());

            envelope.bodyOut = request;
            HttpTransportSE transport = new HttpTransportSE(WSDL);
            transport.debug = true;

            List<HeaderProperty> headerList = new ArrayList<HeaderProperty>();
            headerList.add(
                    new HeaderProperty("Authorization", "Basic "
                            + org.kobjects.base64.Base64.encode(USER_PASSWORD.getBytes())));

            try {
                transport.call(SOAP_ACTION_NEXT, envelope, headerList);
                String requestDump = transport.requestDump;
                responseDump = transport.responseDump;

                Log.e(TAG, requestDump);
                Log.e(TAG, responseDump);

                JSONObject jsonObj = null;
                try {
                    jsonObj = XML.toJSONObject(responseDump);
                } catch (JSONException e) {
                    showError(e.getMessage());
                    stopLoaderUiThread();
                }


                Log.d("JSON_2", jsonObj.toString());

                Gson gson = new GsonBuilder().create();

                Recipe = gson.fromJson(jsonObj.toString(), ResiduesPharmResponse.class);

            } catch (IOException e) {
                showError(e.getMessage());
                stopLoaderUiThread();
            }

        } catch (Exception e) {
            showError(e.getMessage());
            stopLoaderUiThread();
        }
        return Recipe;
    }
}