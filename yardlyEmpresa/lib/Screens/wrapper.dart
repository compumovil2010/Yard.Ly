import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:yardlyEmpresa/Model/Empresa.dart';
import 'package:yardlyEmpresa/Screens/Authentication/signIn.dart';
import 'package:yardlyEmpresa/Screens/home.dart';

class Wrapper extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final empresa = Provider.of<Empresa>(context);
    if(empresa == null ){
      return SingIn();
    }else{
      return Home();
    }
    
  }
}