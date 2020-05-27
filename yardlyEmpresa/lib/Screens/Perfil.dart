import 'package:flutter/material.dart';
import 'package:firebase_database/firebase_database.dart';
import 'package:yardlyEmpresa/Services/auth.dart';
import 'package:yardlyEmpresa/Model/Empresa.dart';

class Perfil extends StatelessWidget {
  final FirebaseDatabase _reference = FirebaseDatabase.instance;
  final AuthService _auth = AuthService();

  void inputData() async {
    var dbRef = await _reference.reference().child(Empresa.pathEmpresas).orderByChild(AuthService.uid).orderByChild("direccion").once(); 
    dbRef.value;
    print(dbRef.value);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          backgroundColor: Colors.green,
          title: Text('Perfil'),
        ),
        body: Center(
            child: Column(
          children: <Widget>[
            Container(
              margin: EdgeInsets.only(left: 30.0, right: 30.0, top: 50.0),
              child: Image.asset(
                'assets/images/person.png',
                height: 150,
                width: 150,
              ),
            ),
            Padding(
                padding: const EdgeInsets.only(top: 100.0),
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
                    shape: RoundedRectangleBorder(
                        borderRadius: BorderRadius.circular(17.0)),
                    color: Colors.green,
                    child: Text('Editar Perfil'),
                    onPressed: () {}))
          ],
        )));
  }
}
