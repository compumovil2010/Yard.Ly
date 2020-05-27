import 'package:firebase_database/firebase_database.dart';
import 'package:firebase_storage/firebase_storage.dart';
import 'package:flutter/material.dart';
import 'package:image_picker/image_picker.dart';
import 'dart:io';

File _imageFile;

class AgregarProducto extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => AgregarProductoState();
}

class DropdownTipo extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => DropdownTipoState();
}

class DropdownTipoState extends State<DropdownTipo> {
  String currentValue;

  Widget build(BuildContext context) {
    return DropdownButton<String>(
      hint: Text('Seleccione Tipo'),
      items: <String>['Plato', 'Ropa'].map((String value) {
        return DropdownMenuItem<String>(
          value: value,
          child: new Text(value),
        );
      }).toList(),
      onChanged: (String newValue) {
        setState(() {
          this.currentValue = newValue;
        });
      },
      value: currentValue,
    );
  }
}

class AgregarProductoState extends State<AgregarProducto> {
  final nombre = TextEditingController();
  final descripcion = TextEditingController();
  final precio = TextEditingController();
  final dbRef = FirebaseDatabase.instance.reference().child("products");

  Future getImage(bool isCamera) async {
    File image;
    if (isCamera) {
      image = await ImagePicker.pickImage(source: ImageSource.camera);
    } else {
      image = await ImagePicker.pickImage(source: ImageSource.gallery);
    }
    setState(() {
      _imageFile = image;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.green,
        title: Text('Agregar Producto'),
      ),
      body: Column(children: <Widget>[
        Container(
          margin: EdgeInsets.only(left: 30.0, right: 30.0, top: 30.0),
          child: _imageFile == null
              ? Image.asset(
                  'assets/images/person.png',
                  height: 150,
                  width: 150,
                )
              : Image.file(_imageFile, height: 150, width: 150),
        ),
        Container(
            child: Row(
                mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                children: <Widget>[
              ButtonTheme(
                  child: RaisedButton(
                      shape: RoundedRectangleBorder(
                          borderRadius: BorderRadius.circular(17.0)),
                      color: Colors.green,
                      child: Icon(Icons.add_a_photo),
                      onPressed: () {
                        getImage(true);
                      })),
              ButtonTheme(
                  child: RaisedButton(
                      shape: RoundedRectangleBorder(
                          borderRadius: BorderRadius.circular(17.0)),
                      color: Colors.green,
                      child: Icon(Icons.add_photo_alternate),
                      onPressed: () {
                        getImage(false);
                      })),
            ])),
        Flexible(
            child: Container(
          child: Center(child: RegisterProduct()),
        )),
      ]),
    );
  }
}

class RegisterProduct extends StatefulWidget {
  RegisterProduct({Key key}) : super(key: key);
  @override
  _RegisterProductState createState() => _RegisterProductState();
}

class _RegisterProductState extends State<RegisterProduct> {
  final _formKey = GlobalKey<FormState>();
  final nombreController = TextEditingController();
  final precioController = TextEditingController();
  final descripcionController = TextEditingController();
  final dbRef = FirebaseDatabase.instance.reference();
  String key;

  Future uploadImage(BuildContext context) async {
    StorageReference firebaseStorageRef =
        FirebaseStorage.instance.ref().child(key);
    StorageUploadTask uploadTask = firebaseStorageRef.putFile(_imageFile);
    StorageTaskSnapshot taskSnapshot = await uploadTask.onComplete;
  }

  @override
  Widget build(BuildContext context) {
    return new Scaffold(
        body: Form(
            key: _formKey,
            child: SingleChildScrollView(
                child: Column(children: <Widget>[
              Padding(
                padding: EdgeInsets.all(20.0),
                child: TextFormField(
                  controller: nombreController,
                  decoration: InputDecoration(
                    labelText: "Nombre",
                  ),
                  validator: (value) {
                    if (value.isEmpty) {
                      return 'Ingrese el Nombre';
                    }
                    return null;
                  },
                ),
              ),
              Padding(
                padding: EdgeInsets.only(right: 20.0, left: 20.0),
                child: TextFormField(
                  controller: descripcionController,
                  decoration: InputDecoration(
                    labelText: "Descripción",
                  ),
                  maxLines: 4,
                  validator: (value) {
                    if (value.isEmpty) {
                      return 'Ingrese Descripción';
                    }
                    return null;
                  },
                ),
              ),
              Padding(
                padding: EdgeInsets.only(left: 20.0, right: 20.0),
                child: TextFormField(
                  keyboardType: TextInputType.number,
                  controller: precioController,
                  decoration: InputDecoration(
                    labelText: "Precio",
                  ),
                  validator: (value) {
                    if (value.isEmpty) {
                      return 'Ingrese Precio';
                    }
                    return null;
                  },
                ),
              ),
              Padding(
                  padding: EdgeInsets.all(20.0),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                    children: <Widget>[
                      ButtonTheme(
                          child: RaisedButton(
                        shape: RoundedRectangleBorder(
                            borderRadius: BorderRadius.circular(17.0)),
                        color: Colors.green,
                        child: Text("Agregar Producto"),
                        onPressed: () {
                          if (_formKey.currentState.validate()) {
                            uploadImage(context);
                            key = dbRef.child('products').push().key;
                            dbRef.child(key).set({
                              "nomProducto": nombreController.text,
                              "precio": precioController.text,
                              "descripcion": descripcionController.text,
                            }).then((_) {
                              Scaffold.of(context).showSnackBar(SnackBar(
                                  content: Text('Se ha añadido el producto')));
                              precioController.clear();
                              nombreController.clear();
                              descripcionController.clear();
                              _imageFile = new File('assets/images/person.png');
                            }).catchError((onError) {
                              Scaffold.of(context).showSnackBar(
                                  SnackBar(content: Text(onError)));
                            });
                          }
                        },
                      )),
                    ],
                  )),
            ]))));
  }
}
