package com.appforbit.eat1;

public class ObjetoTipoLugar {
    private int idTl = 0;
    private String nombreTl = null;

    //Constructores
    public ObjetoTipoLugar() {
    }

    public ObjetoTipoLugar(int idTl, String nombreTl) {
        this.idTl = idTl;
        this.nombreTl = nombreTl;
    }

    //Getters
    public int getIdTl() {
        return idTl;
    }

    public String getNombreTl() {
        return nombreTl;
    }

    //Setters
    public void setIdTl(int idTl) {
        this.idTl = idTl;
    }

    public void setNombreTl(String nombreTl) {
        this.nombreTl = nombreTl;
    }
}


