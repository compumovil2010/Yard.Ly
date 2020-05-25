import 'dart:ffi';

class Producto{
  String nombre;
  String descripcion;
  Double precio;
  var foto; 
  
  Producto(String nombre, String descripcion, Double precio, var foto) {
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

  Double get getPrecio {
    return this.precio;
  }

  set setPrecio (Double precio) {
    this.precio = precio;
  }
}