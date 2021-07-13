package com.sklad.er71.presentation.recipe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sklad.er71.Enum.Recipe_SNILS.MTablerowrecipe;
import com.sklad.er71.Enum.Recipe_SNILS.RecipeSNILSResponse;
import com.sklad.er71.Enum.ResiduesPharm.ResiduesPharmResponse;
import com.sklad.er71.R;
import com.sklad.er71.Repository.SingletonClassApp;
import com.sklad.er71.busines.BaseActivity;
import com.sklad.er71.presentation.maps.MapsActivity;
import com.sklad.er71.presentation.menu.MenuActivity;

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

public class RecipeActivity extends Fragment {

    private MTablerowrecipe item;
    private View root;
    private TextView lpu;
    private TextView doctor;
    private TextView endDate;
    private TextView name;
    private TextView mnn;
    private TextView medForm;
    private TextView doza;
    private TextView kolUp;
    private TextView kolV;
    private TextView info;
    private TextView date;
    private TextView status;
    private LinearLayout map;
    private LinearLayout qr;
    private MenuActivity mainActivity;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MenuActivity) {
            mainActivity = (MenuActivity) context;
        }
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.activity_recipe, container, false);

        initViews();
        initListeners();
        startRequest();

        return root;
    }

    private void initViews() {
        info = root.findViewById(R.id.info);
        date = root.findViewById(R.id.date);
        status = root.findViewById(R.id.status);
        lpu = root.findViewById(R.id.lpu);
        doctor = root.findViewById(R.id.doctor);
        endDate = root.findViewById(R.id.end_date);
        name = root.findViewById(R.id.name);
        mnn = root.findViewById(R.id.mnn);
        medForm = root.findViewById(R.id.med_form);
        doza = root.findViewById(R.id.doza);
        kolUp = root.findViewById(R.id.kol_up);
        kolV = root.findViewById(R.id.kol_v);
        map = root.findViewById(R.id.map_btn);
        qr = root.findViewById(R.id.qr_btn);
        item = (MTablerowrecipe) getArguments().getSerializable("recipe");

        info.setText("Рецепт " + item.getmSeries() + " " + item.getmNumber());
        date.setText("от " + item.getmData());
        lpu.setText(item.getmLPU());
        doctor.setText(item.getmDoctor());
        endDate.setText(item.getmSrok());
        name.setText(item.getmTrademark());
        mnn.setText(item.getmMNN());
        medForm.setText(item.getmLF());
        doza.setText(item.getmDosage());
        kolUp.setText(item.getmPack());
        kolV.setText(item.getmKolV().toString());

        switch (item.getmStatus()){
            case 1:
                status.setText("зарезервирован");
                break;
            case 11:
                status.setText("ожидание пациента");
                break;
            case 12:
                status.setText("неявка пациента");
                map.setVisibility(View.GONE);
                qr.setVisibility(View.GONE);
                break;
            case 13:
                status.setText("обслужен");
                map.setVisibility(View.GONE);
                qr.setVisibility(View.GONE);
                break;
            case 14:
                status.setText("отказ, нет в наличии");
                map.setVisibility(View.GONE);
                qr.setVisibility(View.GONE);
                break;
            case 15:
                status.setText("отсроченное обслуживание");
                break;
            case 3:
                status.setText("резерв отменен аптекой");
                break;
            case 2:
                status.setText("резерв отменен ЛПУ");
                break;
        }
    }

    private void initListeners() {

        map.setOnClickListener(v -> {
//            Uri gmmIntentUri = Uri.parse("https://www.google.com/maps/dir/?api=1&destination="
//                    + item.getmLPU() + "&travelmode=driving");
//            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
//            mapIntent.setPackage("com.google.android.apps.maps");

            Intent intent = new Intent(getActivity(), MapsActivity.class);
            startActivity(intent);




        });
        qr.setOnClickListener(v -> RecipeQRDialog.display(mainActivity.getSupportFragmentManager(), item.getmQRString()));
    }

    private void startRequest() {
        mainActivity.startLoader();
        Thread thread = new Thread(() -> {
            try {
                ResiduesPharmResponse obj = ResiduesPharm();

                setData(obj);
            } catch (Exception e) {
                mainActivity.showError(e.getMessage());
                stopLoaderUiThread();
            }
        });

        thread.start();
    }

    private void setData(ResiduesPharmResponse obj) {
        mainActivity.runOnUiThread(() -> {
            mainActivity.stopLoader();
        });
    }

    private void stopLoaderUiThread() {
        mainActivity.runOnUiThread(() -> mainActivity.stopLoader());
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
                    mainActivity.showError(e.getMessage());
                    stopLoaderUiThread();
                }

                Log.d("JSON_2", jsonObj.toString());

                Gson gson = new GsonBuilder().create();

                Recipe = gson.fromJson(jsonObj.toString(), ResiduesPharmResponse.class);

                SingletonClassApp.getInstance().recipe=Recipe;


            } catch (IOException e) {
                mainActivity.showError(e.getMessage());
                stopLoaderUiThread();
            }

        } catch (Exception e) {
            mainActivity.showError(e.getMessage());
            stopLoaderUiThread();
        }
        return Recipe;
    }
}