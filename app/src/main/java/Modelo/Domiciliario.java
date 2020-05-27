package Modelo;

public class Domiciliario extends Usuario {
    String pedidoActual;
    public  static  final String PATH_DOM = "domiciliario/";
    public static final String PATH_PROFILE_PHOTO = "domiciliario/profile_photos";
    public double lat,longi;
    private float dist,tiempo;

    public Domiciliario() {
    }

    public float getDist() {
        return dist;
    }

    public void setDist(float dist) {
        this.dist = dist;
    }

    public float getTiempo() {
        return tiempo;
    }

    public void setTiempo(float tiempo) {
        this.tiempo = tiempo;
    }

    public String getPedidoActual() {
        return pedidoActual;
    }

    public void setPedidoActual(String pedidoActual) {
        this.pedidoActual = pedidoActual;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLongi() {
        return longi;
    }

    public void setLongi(double longi) {
        this.longi = longi;
    }
}
