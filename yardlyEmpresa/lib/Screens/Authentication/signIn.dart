import 'package:flutter/material.dart';
import 'package:yardlyEmpresa/Services/auth.dart';

class SingIn extends StatefulWidget {
  @override
  _SingInState createState() => _SingInState();
}

class _SingInState extends State<SingIn> {
  final AuthService _service =AuthService();
  final _formkey = GlobalKey<FormState>();
  String email = '';
  String password = '';
  String error = '';

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.white,
      appBar: AppBar(
        centerTitle: true,
        backgroundColor: Colors.green,
        title: Text(
          'Yardly'
        ),
      ),
      body: Container(
        padding: EdgeInsets.symmetric(vertical: 150, horizontal: 50),
        child: Form(
          key: _formkey,
          child: Column(
            children: <Widget>[
              SizedBox(height: 20,),
              TextFormField(
                    decoration: InputDecoration(labelText: 'Correo electrónico',
                      labelStyle: TextStyle(
                        color: Colors.black87,
                        fontSize: 17,
                      ),
                      focusedBorder: UnderlineInputBorder(      
                        borderSide: BorderSide(color: Colors.green),   
                      ),
                      enabledBorder: new UnderlineInputBorder(
                        borderSide: BorderSide(color: Colors.green, 
                          width: 1.0)
                      ),
                    ),
                    style: TextStyle(
                      color: Colors.black87,
                      fontSize: 17,
                      fontFamily: 'AvenirLight'
                    ),
                    obscureText: true,
                    onChanged: (val){
                      setState(() => email = val);
                    },
                    validator: (value) => value.isEmpty ? 'Escribe el email' : null,
              ),
              SizedBox(height: 20,),
              TextFormField(
                    decoration: InputDecoration(labelText: 'Contraseña',
                      labelStyle: TextStyle(
                        color: Colors.black87,
                        fontSize: 17,
                      ),
                      focusedBorder: UnderlineInputBorder(      
                        borderSide: BorderSide(color: Colors.purple),   
                      ),
                      enabledBorder: new UnderlineInputBorder(
                        borderSide: BorderSide(color: Colors.green, 
                          width: 1.0)
                      ),
                    ),
                    style: TextStyle(
                      color: Colors.black87,
                      fontSize: 17,
                      fontFamily: 'AvenirLight'
                    ),
                    obscureText: true,
                    onChanged: (val){
                      setState(() => password = val);
                    },
                    validator: (value) => value.length < 6 ? 'Ingresa una contraseña más larga': null,
              ),
              SizedBox(height: 20,),
              RaisedButton(
                color: Colors.green[100],
                child: Text(
                  'Inicia Sesión',
                  style: TextStyle(color: Colors.white)
                ),
                onPressed: () async{
                  if(_formkey.currentState.validate()){
                    //dynamic result = await _service.toString();
                    //if (result == null){
                      //setState(() => error = 'Por favor pon un correo válido');
                    //}
                  }
                },
              ),
              SizedBox(height: 20,),
              Text(
                error,
                style: TextStyle(
                  color: Colors.red, 
                  fontSize: 16.0),
              )
            ],
          ),
        ),
      ),
    );
  }
}