package com.appforbit.eat1;

import android.app.Activity;
import android.content.Context;
import android.os.StrictMode;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class AdaptadorLugares extends BaseAdapter {
    private static LayoutInflater inflador = null;
    public Context contexto;
    //ResultSet setDatos;
    private Activity myActivity;
    private ArrayList<ObjetoLugares> myList;
    private static LayoutInflater myInflater = null;
    private ObjetoLugares tempValues = null;
    private int cantRegistros;

    public AdaptadorLugares(Activity actividad, ArrayList lista) {
        myActivity = actividad;
        myList = lista;
    }

    @Override
    public int getCount() {
        if (myList.size()<=0)
            return 1;
        return myList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder{
        public TextView nomLugar;
        public TextView tipLugar;
        public TextView paiLugar;
        public TextView ciuLugar;
        public TextView calLugar;
        public TextView telLugar;
        public ImageView fotLugar;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vista = convertView;
        ViewHolder holder;
        int tipoL = 0;
        Context context = null;
        context.getApplicationContext();
        if (convertView==null){
            vista =myInflater.inflate(R.layout.elemento_lugares,null);
            holder =new ViewHolder();
            holder.nomLugar = vista.findViewById(R.id.nombreLugar);
            holder.tipLugar = vista.findViewById(R.id.tipoLugar);
            holder.paiLugar = vista.findViewById(R.id.paisLugar);
            holder.ciuLugar = vista.findViewById(R.id.ciudadLugar);
            holder.calLugar = vista.findViewById(R.id.direccionLugar);
            holder.telLugar = vista.findViewById(R.id.telLugar);
            holder.fotLugar = vista.findViewById(R.id.imagenLugar);
            vista.setTag(holder);
        }
        else{
            holder = (ViewHolder) vista.getTag();
        }
        try{
            if (myList.size()<=0){
                holder.nomLugar.setText("Sin datos");
                holder.tipLugar.setText("Sin datos");
                holder.paiLugar.setText("Sin datos");
                holder.ciuLugar.setText("Sin datos");
                holder.calLugar.setText("Sin datos");
                holder.telLugar.setText("Sin datos");
                //holder.fotLugar.setImageDrawable(R.drawable.camara);
            }
            else{
                tempValues =null;
                tempValues =(ObjetoLugares) myList.get(position);
                holder.nomLugar.setText(tempValues.getNombreLug());
                holder.tipLugar.setText(obtenerTipoLugar(tipoL, context));
                holder.paiLugar.setText(tempValues.getPaisLug());
                holder.ciuLugar.setText(tempValues.getCiudadLug());
                holder.calLugar.setText(tempValues.getCalleLug());
                holder.telLugar.setText(tempValues.getTelefonoLug());
                //holder.fotLugar.setImageDrawable(foto);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return vista;
    }

    public String obtenerTipoLugar(int idL, Context cont){
        String tipoLugar = null;
        Statement stTL = null;
        ResultSet rsTL = null;

        Connection conexion = ConexionBD.conexionBD(cont);
        try{
            stTL = conexion.createStatement();
            rsTL = stTL.executeQuery("SELECT * FROM [eatasty].[dbo].[tipoLugar] WHERE idTl ="+idL);
            if (rsTL!=null){
                tipoLugar=rsTL.getString("nombreTl");
            }
        }catch(Exception c){
            c.printStackTrace();
        }
        return tipoLugar;
     }
}
