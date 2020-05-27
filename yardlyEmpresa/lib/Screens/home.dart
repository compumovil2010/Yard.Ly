import 'package:flutter/material.dart';
import 'package:yardlyEmpresa/Model/Empresa.dart';
import 'package:yardlyEmpresa/Model/Pedido.dart';
import 'package:yardlyEmpresa/Services/auth.dart';
import 'package:yardlyEmpresa/Services/perdidos.dart';

class Home extends StatefulWidget {
  @override
  _HomeState createState() => _HomeState();
}

class _HomeState extends State<Home> {
  final AuthService _auth  = AuthService();
  final PedidosService _pedidosService = PedidosService();
  Empresa domEscogido;
  String nombreEscogido;
  @override
  Widget build(BuildContext context) {
    return Scaffold(     
      backgroundColor: Colors.white,
      appBar: AppBar(
        title: Text('Pedidos'),
        backgroundColor: Colors.green[300],
        actions: <Widget>[
          FlatButton.icon(
            onPressed: null, 
            icon: Icon(
              Icons.restaurant,
              color: Colors.white
            ), 
            label: Text('')
          ),
          FlatButton.icon(
            icon: Icon(
              Icons.exit_to_app,
              color: Colors.white
            ),
            label: Text(''),
            onPressed: ()async{
              await _auth.signOut();
            },
          ),
        ],
      ),
    body: SingleChildScrollView(
        child: FutureBuilder(
          future: _pedidosService.listaPedidos(),
          builder: (context ,AsyncSnapshot<dynamic> snaopShot) {
            if(snaopShot.hasData){
              List<Pedido> values = snaopShot.data as List;
              return new ListView.builder(
                shrinkWrap: true,
                itemCount: values.length,
                itemBuilder: (BuildContext context, int index){
                  return Card(
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: <Widget>[
                        Text("Cantidad Productos: " + values[index].cantProd.toString()),
                        Text("Precio: "+ values[index].precio.toString()),
                        FutureBuilder(
                          future: _pedidosService.listaDom(),
                          builder: (context, AsyncSnapshot<dynamic> snapShot){
                            if(snapShot.hasData){
                              List<Empresa> vals = snapShot.data;
                              return new DropdownButton(
                                items: vals.map((e) => DropdownMenuItem(
                                  value: e.nombre,
                                  child: Text(
                                    e.nombre,
                                    style: TextStyle(color: Colors.green[300]),
                                    ),
                                  )
                                ).toList(),
                                value: null,
                                hint: Text('Seleccione un domiciliario'),
                                onChanged: (selectedDom){
                                  setState((){
                                    nombreEscogido = selectedDom;
                                    for (var item in vals) {
                                      if(item.nombre == nombreEscogido)
                                      {
                                        item.descripcion = values[index].uid;
                                        domEscogido = item;
                                      }
                                    }
                                  });
                                },
                              );
                            }
                            else{
                              return LinearProgressIndicator();
                            }
                          }),
                          RaisedButton(
                            child: Text('Asignar'),
                            onPressed: (){
                              _pedidosService.asignar(domEscogido);
                            },
                          )
                      ],
                    ),
                  );
                }
              );
            }
            return CircularProgressIndicator();
          },
          ),
      ),
    );
  }
}