import 'package:flutter/material.dart';
import 'package:yardlyEmpresa/Services/auth.dart';

class Home extends StatefulWidget {
  @override
  _HomeState createState() => _HomeState();
}

class _HomeState extends State<Home> {
  final AuthService _auth  = AuthService();
  @override
  Widget build(BuildContext context) {
    return Scaffold(     
      backgroundColor: Colors.white,
      appBar: AppBar(
        title: Text('Lista De Pedidos'),
        backgroundColor: Colors.green[300],
        actions: <Widget>[
          FlatButton.icon(
            icon: Icon(Icons.exit_to_app),
            label: null,
            onPressed: ()async{
              await _auth.signOut();
            },
          ),
          FlatButton.icon(
            onPressed: null, 
            icon: Icon(Icons.restaurant), 
            label: null)
        ],
      ),
    );
  }
}