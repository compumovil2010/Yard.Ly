import 'package:flutter/material.dart';
import 'package:firebase_auth/firebase_auth.dart';

class Perfil extends StatelessWidget {

  final FirebaseAuth auth = FirebaseAuth.instance;

  void inputData() async {
    final FirebaseUser user = await auth.currentUser();
    user.displayName;
    final uid = user.uid;
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
          backgroundColor: Colors.green,
          title: Text('Perfil'),
        ),
      body: 
      Center(
      child: Column(
        children: <Widget>[
          Container(
              margin: EdgeInsets.only(left: 30.0, right: 30.0, top: 50.0),
              child: Image.asset('assets/images/person.png', height: 150, width: 150,),
          ),
          Padding(
          padding: const EdgeInsets.only(top:100.0),
          child: Text(
            'Nombre',
            textAlign: TextAlign.center,
            overflow: TextOverflow.ellipsis,
            style: TextStyle(fontWeight: FontWeight.bold, fontSize: 20.0),
          )),
          Padding(
          padding: const EdgeInsets.only(top: 50.0, bottom: 120.0),
          child: Text(
            'Dirección',
            textAlign: TextAlign.center,
            overflow: TextOverflow.ellipsis,
            style: TextStyle(fontWeight: FontWeight.bold, fontSize: 20.0),
          )),
          ButtonTheme(
          child: RaisedButton(
          shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(17.0)),
          color: Colors.green,
          child: Text('Editar Perfil'),
          onPressed: () {

          }
          ))
        ],)
    ));
  }
}