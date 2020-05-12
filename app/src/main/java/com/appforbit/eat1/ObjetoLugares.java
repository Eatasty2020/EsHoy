package com.appforbit.eat1;

public class ObjetoLugares {
    private int idLug =0;
    private String nombreLug ="";
    private String tipoLug ="";
    private String paisLug ="";
    private String ciudadLug ="";
    private String calleLug ="";
    private String telefonoLug ="";
    private float calificacionLug =0;
    private byte[] fotoLug;

    //Constructores
    public ObjetoLugares() {
    }

    public ObjetoLugares(int idLug,String nombreLug, String tipoLug, String paisLug,
                         String ciudadLug, String calleLug, String telefonoLug, float calificacionLug,
                         byte[] fotoLug) {
        this.idLug = idLug;
        this.nombreLug = nombreLug;
        this.tipoLug = tipoLug;
        this.paisLug = paisLug;
        this.ciudadLug = ciudadLug;
        this.calleLug = calleLug;
        this.telefonoLug = telefonoLug;
        this.calificacionLug = calificacionLug;
        this.fotoLug = fotoLug;
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

    public byte[] getFotoLug() {
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

    public void setFotoLug(byte[] fotoLug) {
        this.fotoLug = fotoLug;
    }

}