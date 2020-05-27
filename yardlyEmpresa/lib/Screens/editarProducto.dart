import 'package:firebase_database/firebase_database.dart';
import 'package:flutter/material.dart';
import 'package:image_picker/image_picker.dart';
import 'dart:io';
import 'package:yardlyEmpresa/Model/Producto.dart';


FirebaseDatabase database = new FirebaseDatabase();
DatabaseReference databaseReference = database.reference().child('products');

class EditarProducto extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => EditarProductoState();
}

class EditarProductoState extends State<EditarProducto> {
  var nombre="", descripcion ="", precio="";
  Producto producto;

  File _imageFile;
    Future getImage (bool isCamera) async {
      File image;
      if(isCamera) {
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
          title: Text('Editar Producto'),
        ),
        body: 
        Column( 
          children: <Widget>[
            Container(
              margin: EdgeInsets.only(left: 30.0, right: 30.0, top: 30.0),
              child: _imageFile == null ? Image.asset('assets/images/person.png', height: 150, width: 150,) : Image.file(_imageFile, height: 150, width: 150),
            ),
            Container(
              child: Row(
                mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                children: <Widget>[
                  ButtonTheme(
                  child: RaisedButton(
                  child: Icon(Icons.add_a_photo),
                  onPressed: () {
                      getImage(true);
                    }
                  )),
                  ButtonTheme(
                  child: RaisedButton(
                  child: Icon(Icons.add_photo_alternate),
                  onPressed: () {
                      getImage(false);
                    }
                  )),
                ]
              )
            ),
            Container(
            padding: const EdgeInsets.all(30.0),
            child: Center(
              child: Column(
                children: <Widget>[
                  //DropdownTipo(),
                  TextField(decoration: InputDecoration(labelText: "Nombre"),
                  onChanged: (texto) {
                    producto.setNombre = texto;
                  }),
                  TextField(decoration: InputDecoration(labelText: "Descripción"), maxLines: 4,
                  onChanged: (texto) {
                    producto.setDescripcion = texto;
                  }),
                  TextField(decoration: InputDecoration(labelText: "Precio"), keyboardType: TextInputType.number,
                  onChanged: (texto) {
                    producto.setPrecio = double.parse(texto);
                  }),
                ],)
            ),
          ),
          Container(
            margin: EdgeInsets.all(30.0),
            child: ButtonTheme(
                    child: RaisedButton(
                    child: Text("Guardar Cambios"), 
                    onPressed: (){}
                  )),
            ),
        ]),
      );
  }
}