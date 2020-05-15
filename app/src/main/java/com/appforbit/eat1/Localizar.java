package com.appforbit.eat1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class Localizar extends AppCompatActivity {
    private LocationManager locManager;
    private Location loc;
    public Location localizado;
    private String lat;
    private String lon;
    private TextView latitudTv;
    private TextView longitudTv;
    static final int REQUEST_LOCALIZAR = 1;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_localizar);
        latitudTv = (TextView) findViewById(R.id.tvLatitud);
        longitudTv = (TextView) findViewById(R.id.tvLongitud);
        intent = getIntent();
        localizar(intent);
    }

    protected void localizar(Intent data){
        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION}, 2000);
            }
            locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            loc = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (loc != null) {
                lat = String.valueOf(loc.getLatitude());
                lon = String.valueOf(loc.getLongitude());
                latitudTv.setText(lat);
                longitudTv.setText(lon);
                if (latitudTv!=null && longitudTv!=null && data!=null){
                    data.putExtra("latitud", loc.getLatitude());
                    data.putExtra("longitud", loc.getLongitude());
                    setResult(RESULT_OK, data);
                } else {
                    setResult(RESULT_CANCELED);
                }
            }
        } catch(Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

}
