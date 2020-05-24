import 'Producto.dart';

class Empresa{
  String uid;
  String nombre;
  String direccion;
  List<Producto> productos;

  Empresa(String usId ) 
  {
    uid = usId;
  }
}