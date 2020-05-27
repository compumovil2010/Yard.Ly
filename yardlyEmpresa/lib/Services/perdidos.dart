import 'package:firebase_database/firebase_database.dart';
import 'package:yardlyEmpresa/Model/Empresa.dart';
import 'package:yardlyEmpresa/Model/Pedido.dart';
import 'package:yardlyEmpresa/Services/auth.dart';

class PedidosService{
  final FirebaseDatabase _reference = FirebaseDatabase.instance;
  final AuthService _auth = AuthService();


  Future getPedidos()async{
    var dbRef = await _reference.reference().child(Empresa.pathEmpresas).orderByChild(_auth.getUid()).orderByChild("PedidosSinD").once(); 
    Map<String,dynamic> lista = dbRef.value;
    List<String> pedidos = List<String>();
    lista.forEach((key, value) => pedidos.add(value));
    return pedidos;
  }
  Future listaPedidos()async{
    List<String> ped = await getPedidos() as List<String>;
    List<Pedido> pedidosFinal = List<Pedido>();
    if(ped != null){
      for (var item in ped) {
        var dbRef = await _reference.reference().child(Pedido.pathPedidos).child(item).once();
        Map<String,dynamic> pedido =dbRef.value;
        Pedido pedidoFinal = Pedido.fromJson(pedido);
        pedidosFinal.add(pedidoFinal);
      }
    }
    print(pedidosFinal.toString());
    return pedidosFinal;
  }

  

}