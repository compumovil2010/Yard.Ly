import 'package:flutter/material.dart';

class DropdownTipo extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return DropdownTipoState();
  }
}

class DropdownTipoState extends State<DropdownTipo> {
  String currentValue;
  Widget build(BuildContext context) {
    return DropdownButton <String>(
      hint: Text('Seleccione Tipo'),
      items: <String>['Plato','Ropa'].map((String value) {
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

class AgregarProducto extends StatelessWidget {
  String nombre="", descripcion ="", precio="";
  @override
  Widget build(BuildContext context) {
      return Scaffold(
        appBar: AppBar(
          title: Text('Agregar Producto'),
        ),
        body: 
        Container(
          padding: const EdgeInsets.all(30.0),
          margin: const EdgeInsets.only(top: 30.0),
          child: Center(
            child: Column(
              children: <Widget>[
                Image.asset('assets/images/person.png', height: 100, width: 100,),
                DropdownTipo(),
                TextField(decoration: InputDecoration(labelText: "Nombre"),
                onChanged: (texto) {
                  nombre = texto;
                }),
                TextField(decoration: InputDecoration(labelText: "Descripción"), maxLines: 4,
                onChanged: (texto) {
                  descripcion = texto;
                }),
                TextField(decoration: InputDecoration(labelText: "Precio"), keyboardType: TextInputType.number,
                onChanged: (texto) {
                  precio = texto;
                }),
                TextField(decoration: InputDecoration(labelText: "Colores")),
                TextField(decoration: InputDecoration(labelText: "Talla")),
                TextField(decoration: InputDecoration(labelText: "Material")),
              ],)
          ),
        ),
      );
  }
}