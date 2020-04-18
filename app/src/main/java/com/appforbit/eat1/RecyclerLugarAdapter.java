package com.appforbit.eat1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerLugarAdapter extends RecyclerView.Adapter<RecyclerLugarAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView nombreTV;
        private TextView tipoTV;
        private TextView paisTV;
        private TextView ciudadTV;
        private TextView calleTV;
        private TextView telefonoTV;
        private RatingBar calificacionTV;
        private ImageView fotoLug;

        public ViewHolder(View vistaItem){
            super (vistaItem);
            nombreTV        = (TextView) vistaItem.findViewById(R.id.nombreLugar);
            tipoTV          = (TextView) vistaItem.findViewById(R.id.tipoLugar);
            paisTV          = (TextView) vistaItem.findViewById(R.id.paisLugar);
            ciudadTV        = (TextView) vistaItem.findViewById(R.id.ciudadLugar);
            calleTV         = (TextView) vistaItem.findViewById(R.id.calleLugar);
            telefonoTV      = (TextView) vistaItem.findViewById(R.id.telefonoLugar);
            calificacionTV  = (RatingBar) vistaItem.findViewById(R.id.reputacionLugar);
            fotoLug         = (ImageView) vistaItem.findViewById(R.id.imagenLugar);
        }
    }

    public List<ObjetoLugares> listaLugares;

    public RecyclerLugarAdapter(List<ObjetoLugares> listaLugares) {
        this.listaLugares = listaLugares;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lugar, parent, false);
        ViewHolder viewHolder = new ViewHolder(vista);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        holder.nombreTV.setText(listaLugares.get(position).getNombreLug());
        holder.tipoTV.setText(listaLugares.get(position).getTipoLug());
        holder.calleTV.setText(listaLugares.get(position).getCalleLug());
        holder.ciudadTV.setText(listaLugares.get(position).getCiudadLug());
        holder.paisTV.setText(listaLugares.get(position).getPaisLug());
        holder.telefonoTV.setText(listaLugares.get(position).getTelefonoLug());
        holder.calificacionTV.setRating(listaLugares.get(position).getCalificacionLug());
        holder.fotoLug.setImageBitmap(BitmapFactory.decodeByteArray(listaLugares.get(position).getFotoLug(),
                                0,listaLugares.get(position).getFotoLug().length));
    }

    @Override
    public int getItemCount(){
        return listaLugares.size();
    }
}
