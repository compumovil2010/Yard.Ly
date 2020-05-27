import 'dart:ffi';

class Producto{
  static final pathProducts = 'products/';
  String nombre;
  String descripcion;
  double precio;
  String nomEstab;
  var foto; 

  Producto({
    this.nombre,
    this.descripcion,
    this.precio,
    this.nomEstab,
    this.foto});

  factory Producto.fromJson(Map<String, dynamic> parsedJson) {
    var producto = new Producto(
          descripcion: parsedJson['descripcion'],
          nombre: parsedJson['nomProducto'],
          precio: parsedJson['precio'],
          nomEstab: parsedJson['nomEstab'],
        );
   return producto;
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