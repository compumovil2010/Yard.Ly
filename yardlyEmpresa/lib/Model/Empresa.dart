import 'Pedido.dart';
import 'Producto.dart';

class Empresa{
  static final pathEmpresas = 'restaurants/';
  final String uid;
  String nombre;
  String direccion;
  String descripcion;
  List<Producto> productos;
  List<Pedido> pedidos;

  Empresa({
    this.uid,
    this.nombre,
    this.direccion,
    this.descripcion,
    this.productos,
    this.pedidos});

    factory Empresa.fromJson(Map<String, dynamic> parsedJson) {
    var pedidos = parsedJson['pedidosSinD'];
    List<Pedido> pedidosT = new List<Pedido>.from(pedidos);
    var empresa = new Empresa(
          descripcion: parsedJson['descripR'],
          direccion: parsedJson['direccion'],
          nombre: parsedJson['nombreR'],
          pedidos: pedidosT,
        );
   return empresa;
  }
}

