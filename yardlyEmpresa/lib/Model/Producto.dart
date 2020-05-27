import 'dart:ffi';

class Producto{
  static final pathProducts = '/products/';
  String nombre;
  String descripcion;
  double precio;
  var foto; 
  
  Producto(String nombre, String descripcion, double precio, var foto) {
    this.nombre = nombre;
    this.descripcion = descripcion;
    this.precio = precio;
    this.foto = foto;
  }

  String get getNombre {
    return this.nombre;
  }

  set setNombre (String nombre) {
    this.nombre = nombre;
  }

  String get getDescripcion {
    return this.descripcion;
  }

  set setDescripcion (String descripcion) {
    this.descripcion = descripcion;
  }

  double get getPrecio {
    return this.precio;
  }

  set setPrecio (double precio) {
    this.precio = precio;
  }
}