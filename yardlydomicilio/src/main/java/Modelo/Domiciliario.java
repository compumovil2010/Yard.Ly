package Modelo;

public class Domiciliario extends Usuario {
    String pedidoActual;
    public  static  final String PATH_DOM = "domiciliario/";
    public static final String PATH_PROFILE_PHOTO = "domiciliario/profile_photos";
    public double lat,longi;
    private String nombre;
    private String apellido;
    public Domiciliario() {
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

    @Override
    public String getNombre() {
        return nombre;
    }

    @Override
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }


    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
}
