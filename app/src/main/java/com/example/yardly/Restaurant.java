package com.example.yardly;

import android.util.Pair;

import java.util.List;

public class Restaurant {

    private String nombreR;
    private String descripR;

    public Restaurant()
    {

    }

    public Restaurant(String pnombreR, String pdescripR )
    {
        this.nombreR = pnombreR;
        this.descripR = pdescripR;
    }


    public String getNombreR() {
        return nombreR;
    }

    public void setNombreR(String nombreR) {
        this.nombreR = nombreR;
    }

    public String getDescripR() {
        return descripR;
    }

    public void setDescripR(String descripR) {
        this.descripR = descripR;
    }


}
