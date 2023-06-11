package com.example.redsocial.publicaciones;

import java.io.Serializable;

public class Publicacion  implements Serializable {
    String mensaje;

    public Publicacion(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
