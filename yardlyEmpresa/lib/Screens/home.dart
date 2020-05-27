import 'package:flutter/material.dart';
import 'package:yardlyEmpresa/Screens/Perfil.dart';

class Home extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: DefaultTabController(
        length: 3,
        child: Scaffold(
          appBar: AppBar(
            centerTitle: true,
            backgroundColor: Colors.green,
            bottom: TabBar(
              tabs: [
                Tab(icon: Icon(Icons.check)),
                Tab(icon: Icon(Icons.import_contacts)),
                Tab(icon: Icon(Icons.account_circle)),
              ],
            ),
            title: Text('Yardly'),
          ),
          body: TabBarView(
            children: [
              Icon(Icons.sentiment_satisfied),
              Icon(Icons.sentiment_neutral),
              Perfil(),
            ],
          ),
        ),
      ),
    );
  }
}