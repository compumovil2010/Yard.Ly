import 'package:firebase_database/firebase_database.dart';
import 'package:yardlyEmpresa/Model/Empresa.dart';
import 'package:yardlyEmpresa/Model/Pedido.dart';
import 'package:yardlyEmpresa/Services/auth.dart';

class PedidosService{
  final FirebaseDatabase _reference = FirebaseDatabase.instance;


  Future getPedidos()async{
    var dbRef = await _reference.reference().child(Empresa.pathEmpresas).child(AuthService.uid).child("pedidosSinD").once(); 
    var pedidosJson = dbRef.value;
    List<String> lista = new List<String>.from(pedidosJson);
    return lista;

  }
  Future listaPedidos()async{
    List<String> ped = await getPedidos() as List<String>;
    List<Pedido> pedidosFinal = List<Pedido>();
    if(ped != null){
      for (var item in ped) {
        var dbRef = await _reference.reference().child(Pedido.pathPedidos).child(item).once();
        Map<String,dynamic> pedido =Map<String,dynamic>.from(dbRef.value);
        Pedido pedidoFinal = Pedido.fromJson(pedido,item);
        pedidosFinal.add(pedidoFinal);
      }
    }
    print(pedidosFinal.toString());
    return pedidosFinal;
  }
  Future listaDom()async{
    var dbRef = await _reference.reference().child("domiciliario").once();
    Map<String, dynamic> doms = new Map<String, dynamic>.from(dbRef.value);
    List<Empresa> lista = new List<Empresa>();
    doms.forEach((key, value) {
      Empresa emp = new Empresa(nombre: value['nombre'],uid: key);
      lista.add(emp);
    });
    return lista;
  }

  void asignar(Empresa domEscogido)async{
    var dbRef = await _reference.reference().child("domiciliario").child(domEscogido.uid).once();
    Map<String, dynamic> domi = new Map<String, dynamic>.from(dbRef.value);
    //domi.update('pedidoActual', (value) => domEscogido.descripcion);
    domi['pedidoActual'] = domEscogido.descripcion;
    _reference.reference().child("domiciliario").child(domEscogido.uid).update(domi);
    dbRef = await _reference.reference().child("pedido").child(domEscogido.descripcion).once();
    Map<String, dynamic> pedido = new Map<String, dynamic>.from(dbRef.value);
    pedido['domi'] = domEscogido.uid;
    _reference.reference().child("pedido").child(domEscogido.descripcion).update(pedido);

  }  

}