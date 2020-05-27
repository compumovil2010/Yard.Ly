import 'dart:ffi';

class Pedido{
  static final String pathPedidos = 'pedido/';
  String fecha;
  String nombreProducto;
  Float precio;
  List<String> productos;
  List<Int32> cantProd;
  String usuPedido;
  String domi;
  String idChat;
  String dirUsu;
  String empresa;
  String comentarios;
  
  Pedido({
    this.cantProd,
    this.comentarios,
    this.dirUsu,
    this.domi,
    this.empresa,
    this.fecha,
    this.idChat,
    this.nombreProducto,
    this.precio,
    this.productos,
    this.usuPedido});

  factory Pedido.fromJson(Map<String, dynamic> parsedJson) {
    var productosJson = parsedJson['productos'];
    List<String> productosList = new List<String>.from(productosJson);
    var cantProdJson = parsedJson['cantProd'];
    List<Int32> cantProdList = new List<Int32>.from(cantProdJson);
    var pedido = new Pedido(
          cantProd: cantProdList,
          comentarios: parsedJson['comentarios'],
          dirUsu: parsedJson['dirUsu'],
          domi: parsedJson['domi'],
          empresa: parsedJson['empresa'],
          fecha: parsedJson['fecha'],
          idChat: parsedJson['idChat'],
          nombreProducto: parsedJson['nombreProducto'],
          precio: parsedJson['precio'],
          productos: productosList,
          usuPedido: parsedJson['usuPedido'],
        );
   return pedido;
  }
}