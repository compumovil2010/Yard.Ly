import 'package:firebase_database/firebase_database.dart';
import 'package:yardlyEmpresa/Model/Empresa.dart';
import 'package:yardlyEmpresa/Model/Pedido.dart';
import 'package:yardlyEmpresa/Services/auth.dart';

class PedidosService{
  final FirebaseDatabase _reference = FirebaseDatabase.instance;


  Future getPedidos()async{
    var uid = AuthService.uid;
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
        Pedido pedidoFinal = Pedido.fromJson(pedido);
        pedidosFinal.add(pedidoFinal);
      }
    }
    print(pedidosFinal.toString());
    return pedidosFinal;
  }

  

}