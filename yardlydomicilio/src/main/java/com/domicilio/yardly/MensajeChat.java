package com.domicilio.yardly;

import java.io.Serializable;
import java.time.LocalDateTime;

public class MensajeChat implements Serializable {
    private String texto;
    private String usuario;
    private String  fechayhora;
    private String IdUsuario;

    public String getIdUsuario() {
        return IdUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        IdUsuario = idUsuario;
    }

    public MensajeChat() {

    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getFechayhora() {
        return fechayhora;
    }

    public void setFechayhora(String fechayhora) {
        this.fechayhora = fechayhora;
    }
}
