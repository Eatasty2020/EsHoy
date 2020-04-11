package com.appforbit.eat1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Lugares extends AppCompatActivity {
    //private ListView listar = null;
    //private ResultSet rLugares = null;
    public Context contexto;
    private RecyclerView recyclerViewLugares;
    private RecyclerLugarAdapter adapterLugares;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lugares);

        recyclerViewLugares = (RecyclerView)findViewById(R.id.recyclerLugares);
        recyclerViewLugares.setLayoutManager(new LinearLayoutManager(this));
        adapterLugares = new RecyclerLugarAdapter(obtenerLugarBD());
        recyclerViewLugares.setAdapter(adapterLugares);
    }

    public List<ObjetoLugares> obtenerLugarBD(){
        List<ObjetoLugares> lugares = new ArrayList<>();
        Bitmap fotoBit=null;
        String tipoL = "";
        Connection conexion = ConexionBD.conexionBD(this);
        String consulta ="SELECT idL, nombreL, tipoL, paisL, ciudadL, calleL, telefonoL, calificacionL, fotoL FROM lugares";
        try{
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(consulta);
            while (rs.next()){
                if (rs.getBytes("fotoL")!=null){
                    fotoBit= BitmapFactory.decodeStream(rs.getAsciiStream("fotoL"));
                } else{
                    fotoBit=BitmapFactory.decodeResource(getResources(), R.drawable.camara);
                }
                tipoL=obtenerTipoLugar(rs.getInt("tipoL"));
                lugares.add(new ObjetoLugares(rs.getInt("idL"), rs.getString("nombreL"),
                        tipoL, rs.getString("paisL"),
                        rs.getString("ciudadL"), rs.getString("calleL"),
                        rs.getString("telefonoL"), rs.getFloat("calificacionL"),
                        fotoBit));
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
 }
