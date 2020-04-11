package com.appforbit.eat1;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.Image;

import java.sql.Blob;

public class ObjetoLugares {
    private int idLug =0;
    private String nombreLug ="";
    private String tipoLug ="";
    private String paisLug ="";
    private String ciudadLug ="";
    private String calleLug ="";
    private String telefonoLug ="";
    private float calificacionLug =0;
    private Bitmap fotoLug;

    public ObjetoLugares() {
    }

    public ObjetoLugares(int idL, String nombreL, String tipoL, String paisL, String ciudadL, String calleL,
                         String telefonoL, float calificacionL, Bitmap fotoL) {
        this.idLug = idL;
        this.nombreLug = nombreL;
        this.tipoLug = tipoL;
        this.paisLug = paisL;
        this.ciudadLug = ciudadL;
        this.calleLug = calleL;
        this.telefonoLug = telefonoL;
        this.calificacionLug = calificacionL;
        this.fotoLug = fotoL;
    }

    //Getters
    public int getIdLug() {
        return idLug;
    }

    public String getNombreLug() {
        return nombreLug;
    }

    public String getTipoLug() {
        return tipoLug;
    }

    public String getPaisLug() {
        return paisLug;
    }

    public String getCiudadLug() {
        return ciudadLug;
    }

    public String getCalleLug() {
        return calleLug;
    }

    public String getTelefonoLug() {
        return telefonoLug;
    }

    public float getCalificacionLug() {
        return calificacionLug;
    }

    public Bitmap getFotoLug() {
        return fotoLug;
    }

    //Setters
    public void setIdLug(int idLug) {
        this.idLug = idLug;
    }

    public void setNombreLug(String nombreLug) {
        this.nombreLug = nombreLug;
    }

    public void setTipoLug(String tipoLug) {
        this.tipoLug = tipoLug;
    }

    public void setPaisLug(String paisLug) {
        this.paisLug = paisLug;
    }

    public void setCiudadLug(String ciudadLug) {
        this.ciudadLug = ciudadLug;
    }

    public void setCalleLug(String calleLug) {
        this.calleLug = calleLug;
    }

    public void setTelefonoLug(String telefonoLug) {
        this.telefonoLug = telefonoLug;
    }

    public void setCalificacionLug(float calificacionLug) {
        this.calificacionLug = calificacionLug;
    }

    public void setFotoLug(Bitmap fotoLug) {
        this.fotoLug = fotoLug;
    }

}