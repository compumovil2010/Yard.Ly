import 'Pedido.dart';
import 'Producto.dart';

class Empresa{
  static final pathEmpresas = 'restaurants/';
  final String uid;
  String nombre;
  String direccion;
  List<Producto> productos;
  List<Pedido> pedidos;
  Empresa({this.uid});  
}