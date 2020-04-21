package com.example.yardly;

import android.util.Pair;

import java.util.List;

public class Restaurant {

    private String nombreR;
    private String descripR;
    private List < Pair< Double, Double>> sedesR;

    public Restaurant()
    {

    }

    public Restaurant(String pnombreR, String pdescripR, List< Pair < Double, Double > > psedesR )
    {
        this.nombreR = pnombreR;
        this.descripR = pdescripR;
        this.sedesR = psedesR;
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

    public List<Pair< Double, Double>> getSedesR() {
        return sedesR;
    }

    public void setSedesR(List<Pair< Double, Double>> sedesR) {
        this.sedesR = sedesR;
    }
}
