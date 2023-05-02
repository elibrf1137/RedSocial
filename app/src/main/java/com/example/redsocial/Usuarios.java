package com.example.redsocial;

public class Usuarios {
    private String correo;
    private String nombre;
    private String psw;

    public Usuarios(String correo, String nombre, String psw) {
        this.correo = correo;
        this.nombre = nombre;
        this.psw = psw;
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
}
