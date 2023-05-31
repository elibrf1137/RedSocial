package com.example.redsocial;

import java.util.ArrayList;

public class Usuarios {
    private String correo;
    private String nombre;
    private String psw;
    private ArrayList<Publicacion> listaPublicaciones;



    public Usuarios(String correo, String nombre, String psw) {
        this.correo = correo;
        this.nombre = nombre;
        this.psw = psw;
        listaPublicaciones = new ArrayList<>();
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }

    public ArrayList<Publicacion> getListaPublicaciones() {
        return listaPublicaciones;
    }

    public void setListaPublicaciones(ArrayList<Publicacion> listaPublicaciones) {
        this.listaPublicaciones = listaPublicaciones;
    }
}
