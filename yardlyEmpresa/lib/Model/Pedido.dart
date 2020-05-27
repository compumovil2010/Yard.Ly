class Pedido{
  static final String pathPedidos = 'pedido/';
  String fecha;
  String nombreProducto;
  int precio;
  List<String> productos;
  int cantProd;
  String usuPedido;
  String domi;
  String idChat;
  String dirUsu;
  String empresa;
  String comentarios;
  String uid;
  
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
    this.usuPedido,
    this.uid});

  factory Pedido.fromJson(Map<String, dynamic> parsedJson, String uidd) {
    var productosJson = parsedJson['productos'];
    List<String> productosList = new List<String>.from(productosJson);
    var pedido = new Pedido(
          cantProd: productosList.length,
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
          uid: uidd
        );
   return pedido;
  }
}