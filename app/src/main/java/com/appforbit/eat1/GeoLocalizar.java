package com.appforbit.eat1;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;


public class GeoLocalizar extends Activity implements ActivityCompat.OnRequestPermissionsResultCallback {
    private TextView tvLatitud, tvLongitud, tvAltura, tvPrecision;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geo_localizar);
        try {
            Intent localiza = new Intent(this, Localizar.class);
            startActivityForResult(localiza, 1000);
        }catch(Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

}
