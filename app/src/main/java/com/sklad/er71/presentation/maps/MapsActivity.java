package com.sklad.er71.presentation.maps;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sklad.er71.Enum.ResiduesPharm.MTablerowapp;
import com.sklad.er71.R;
import com.sklad.er71.Repository.SingletonClassApp;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Circle;
import com.yandex.mapkit.geometry.LinearRing;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.geometry.Polygon;
import com.yandex.mapkit.geometry.Polyline;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.CircleMapObject;
import com.yandex.mapkit.map.IconStyle;
import com.yandex.mapkit.map.MapObject;
import com.yandex.mapkit.map.MapObjectCollection;
import com.yandex.mapkit.map.MapObjectTapListener;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.mapkit.map.PolygonMapObject;
import com.yandex.mapkit.map.PolylineMapObject;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.runtime.image.AnimatedImageProvider;
import com.yandex.runtime.image.ImageProvider;
import com.yandex.runtime.ui_view.ViewProvider;

import java.util.ArrayList;
import java.util.Random;

public class MapsActivity extends AppCompatActivity {


    private final Point CAMERA_TARGET = new Point(59.952, 30.318);
    private MapView mapView;
    private MapObjectCollection mapObjects;
    private Handler animationHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            MapKitFactory.setApiKey("0f1778cf-197b-4120-9de1-70b3c3e27ba0");

        } catch (AssertionError e) {
        }
        ;

        MapKitFactory.initialize(this);
        setContentView(R.layout.activity_maps);
        super.onCreate(savedInstanceState);
        mapView = (MapView) findViewById(R.id.mapview);

        ArrayList<MTablerowapp> Recipi = new ArrayList<>();
        try {
            Recipi.addAll(SingletonClassApp.getInstance().recipe.getSoapEnvelope().getSoapBody().getmResiduesPharmResponse().getmReturn().getmTablerowapp());
        } catch (NullPointerException e) {
        }

        try {
            mapView.getMap().move(
                    new CameraPosition(new Point(Recipi.get(0).getmDrugstoreY(), Recipi.get(0).getmDrugstoreX()), 8.0f, 0.0f, 0.0f));
            mapObjects = mapView.getMap().getMapObjects().addCollection();
            animationHandler = new Handler();
            createMapObjects();
        } catch (IndexOutOfBoundsException e) {

            mapView.getMap().move(
                    new CameraPosition(CAMERA_TARGET, 8.0f, 0.0f, 0.0f));
            mapObjects = mapView.getMap().getMapObjects().addCollection();
            animationHandler = new Handler();
            createMapObjects();
        }
    }

    @Override
    protected void onStop() {
        mapView.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        MapKitFactory.getInstance().onStart();
        mapView.onStart();
    }

    private void createMapObjects() {


        ArrayList<MTablerowapp> Recipi = new ArrayList<>();

        try {
            Recipi.addAll(SingletonClassApp.getInstance().recipe.getSoapEnvelope().getSoapBody().getmResiduesPharmResponse().getmReturn().getmTablerowapp());

            for (MTablerowapp r : Recipi
            ) {


                PlacemarkMapObject mark = mapObjects.addPlacemark(new Point(r.getmDrugstoreY(), r.getmDrugstoreX()));
                mark.setOpacity(0.5f);
                mark.setIcon(ImageProvider.fromResource(this, R.drawable.icon_marker));
                mark.setDraggable(true);
                mark.setUserData(new CircleMapObjectUserData(r.getmTN(), r.getmLF(), r.getmDosage(), String.valueOf(r.getmKSo())));
                mark.addTapListener(circleMapObjectTapListener);

                //   createPlacemarkMapObjectWithViewProvider();
                createAnimatedPlacemark();
            }
        } catch (NullPointerException e) {
        }

    }

    // Strong reference to the listener.
    private MapObjectTapListener circleMapObjectTapListener = new MapObjectTapListener() {
        @Override
        public boolean onMapObjectTap(MapObject mapObject, Point point) {
            if (mapObject instanceof PlacemarkMapObject) {
                PlacemarkMapObject circle = (PlacemarkMapObject) mapObject;
                float randomRadius = 100.0f + 50.0f * new Random().nextFloat();


                Object userData = circle.getUserData();
                if (userData instanceof CircleMapObjectUserData) {
                    CircleMapObjectUserData circleUserData = (CircleMapObjectUserData) userData;

                    Toast toast = Toast.makeText(
                            getApplicationContext(),
                            circleUserData.tn + " "
                                    + circleUserData.lf + " " + circleUserData.dosoge + " " +
                                    circleUserData.k_so + " ",
                            Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
            return true;
        }
    };

    private class CircleMapObjectUserData {

        final String tn;
        final String lf;
        final String dosoge;
        final String k_so;

        CircleMapObjectUserData(String tn, String lf, String dosoge, String k_so) {
            this.tn = tn;
            this.lf = lf;
            this.dosoge = dosoge;
            this.k_so = k_so;
        }
    }


    private void createAnimatedPlacemark() {
        ArrayList<MTablerowapp> Recipi = new ArrayList<>();
        try {
            Recipi.addAll(SingletonClassApp.getInstance().recipe.getSoapEnvelope().getSoapBody().getmResiduesPharmResponse().getmReturn().getmTablerowapp());
        } catch (NullPointerException e) {
        }
        AnimatedImageProvider imageProvider =
                AnimatedImageProvider.fromAsset(this, "animation.png");
        PlacemarkMapObject animatedPlacemark =
                mapObjects.addPlacemark(new Point(Recipi.get(0).getmDrugstoreY(), Recipi.get(0).getmDrugstoreX()), imageProvider, new IconStyle());
        animatedPlacemark.useAnimation().play();
    }
}