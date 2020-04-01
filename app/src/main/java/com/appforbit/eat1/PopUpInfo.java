package com.appforbit.eat1;

import android.os.Bundle;
import android.util.DisplayMetrics;

import androidx.appcompat.app.AppCompatActivity;

public class PopUpInfo extends AppCompatActivity {

    @Override
    protected void onCreate (Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popupinfo);

        DisplayMetrics medidasventana = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(medidasventana);

        int ancho = medidasventana.widthPixels;
        int alto = medidasventana.widthPixels;

        getWindow().setLayout((int) (ancho * 0.75), (int) (alto * 0.4));


    }


}