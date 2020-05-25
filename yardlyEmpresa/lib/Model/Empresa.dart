import 'Pedidos.dart';
import 'Producto.dart';

class Empresa{
  final String uid;
  String nombre;
  String direccion;
  List<Producto> productos;
  List<Pedidos> pedidos;
  Empresa({this.uid});  
}