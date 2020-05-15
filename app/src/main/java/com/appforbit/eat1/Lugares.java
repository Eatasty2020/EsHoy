package com.appforbit.eat1;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Lugares extends AppCompatActivity {

    public Context contexto;
    private RecyclerView recyclerViewLugares;
    private RecyclerLugarAdapter adapterLugares;
    private Button btnAgregar;
    private Button btnFiltrar;
    private int usuario;
    private Bundle estadoInstancia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lugares);
        btnAgregar = (Button)findViewById(R.id.btnAgregar);
        btnFiltrar = (Button)findViewById(R.id.btnFiltrar);

        recyclerViewLugares = (RecyclerView)findViewById(R.id.recyclerLugares);
        recyclerViewLugares.setLayoutManager(new LinearLayoutManager(this));
        adapterLugares = new RecyclerLugarAdapter(obtenerLugarBD());
        recyclerViewLugares.setAdapter(adapterLugares);
        try {
            btnAgregar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent agregarLugares = new Intent(getApplicationContext(), AgregarLugares.class);
                    usuario = RecuperarUsuario();
                    agregarLugares.putExtra("idUsuario", usuario);
                    startActivity(agregarLugares);
                }
            });
            btnFiltrar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    Intent lugares = new Intent(getApplicationContext(), Lugares.class);
                    //startActivity(lugares);
                    Toast.makeText(getApplicationContext(), "Sin implementar", Toast.LENGTH_SHORT).show();
                    }
                });
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public List<ObjetoLugares> obtenerLugarBD(){
        List<ObjetoLugares> lugares = new ArrayList<>();
        Resources res = getResources();
        Drawable drawable = null;
        Bitmap fotoBit = null;
        byte[] fotoByte = null;
        String tipoL = "";
        Connection conexion = ConexionBD.conexionBD(this);
        String consulta ="SELECT idL, nombreL, tipoL, paisL, ciudadL, calleL, telefonoL, calificacionL, fotoL FROM lugares";
        try{
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(consulta);
            while (rs.next()){
                if (rs.getBytes("fotoL")!=null){
                    fotoByte = rs.getBytes("fotoL");
                }else{
                    fotoBit = BitmapFactory.decodeResource(getResources(), R.drawable.camara);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    fotoBit.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    fotoByte = stream.toByteArray();
                }
                tipoL=obtenerTipoLugar(rs.getInt("tipoL"));
                lugares.add(new ObjetoLugares(rs.getInt("idL"), rs.getString("nombreL"),
                        tipoL, rs.getString("paisL"),
                        rs.getString("ciudadL"), rs.getString("calleL"),
                        rs.getString("telefonoL"), rs.getFloat("calificacionL"),
                        fotoByte));
            }
            conexion.close();
        }
        catch(Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
        return lugares;
    }

    private String obtenerTipoLugar(int tipoLugar){
        String tLugar ="";
        Connection conexion = ConexionBD.conexionBD(this);
        String consulta ="SELECT nombreTl FROM tipoLugar WHERE idTl = "+tipoLugar;
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(consulta);
            while (rs.next()){
                tLugar = rs.getString("nombreTl");
            }
            conexion.close();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return tLugar;
    }
    public int RecuperarUsuario(){
        int user =0;
        if (estadoInstancia==null) {
            Bundle extras = getIntent().getExtras();
            if (extras != null){
                user = extras.getInt("idUsuario");
            }
        }
        return user;
    }
}
