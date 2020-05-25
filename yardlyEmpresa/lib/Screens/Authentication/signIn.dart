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
        backgroundColor: Colors.green[300],
        title: Text(
          'Yardly'
        ),
      ),
      body: Center(
        child:SingleChildScrollView(
        padding: EdgeInsets.symmetric(vertical: 50, horizontal: 50),
          child: Form(
            key: _formkey,
            child: Column(
              children: <Widget>[
                SizedBox(height: 20,),
                TextFormField(
                      decoration: InputDecoration(labelText: 'Correo electrónico',
                        labelStyle: TextStyle(
                          color: Colors.green,
                          fontSize: 20,
                        ),
                        focusedBorder: UnderlineInputBorder(      
                          borderSide: BorderSide(color: Colors.green[300]),   
                        ),
                        enabledBorder: new UnderlineInputBorder(
                          borderSide: BorderSide(color: Colors.green[300], 
                            width: 1.0)
                        ),
                      ),
                      style: TextStyle(
                        color: Colors.black87,
                        fontSize: 17,
                        fontFamily: 'AvenirLight'
                      ),
                      onChanged: (val){
                        setState(() => email = val);
                      },
                      validator: (value) => value.isEmpty ? 'Escribe el email' : null,
                ),
                SizedBox(height: 20,),
                TextFormField(
                      decoration: InputDecoration(labelText: 'Contraseña',
                        labelStyle: TextStyle(
                          color: Colors.green,
                          fontSize: 17,
                        ),
                        focusedBorder: UnderlineInputBorder(      
                          borderSide: BorderSide(color: Colors.purple),   
                        ),
                        enabledBorder: new UnderlineInputBorder(
                          borderSide: BorderSide(color: Colors.green[300], 
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
                  color: Colors.green[300],
                  child: Text(
                    'Inicia Sesión',
                    style: TextStyle(color: Colors.white)
                  ),
                  onPressed: () async{
                    if(_formkey.currentState.validate()){
                      dynamic result = await _service.signInWithEmailAndPass(email, password);
                      if (result == null){
                        setState(() => error = 'No fue posible ingresar con esas credenciales');
                      }
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
      ),
    );
  }
}