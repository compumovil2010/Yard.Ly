import 'package:firebase_auth/firebase_auth.dart';
import 'package:yardlyEmpresa/Model/Empresa.dart';

class AuthService{
  final FirebaseAuth _auth = FirebaseAuth.instance;

  Empresa _empresaFromFirebaseUser(FirebaseUser user){
    return user != null? Empresa(uid: user.uid): null;
  }

  Stream<Empresa> get user{
    return _auth.onAuthStateChanged
    .map(_empresaFromFirebaseUser);
  }
  Future signInWithEmailAndPass(String email, String pass)async{
    try {
      AuthResult res = await _auth.signInWithEmailAndPassword(email: email, password: pass);
      FirebaseUser user = res.user;
      return _empresaFromFirebaseUser(user);
      
    } catch (e) {
      print(e.toString());
      return null;
    }
  }
  Future signOut() async{
    try {
      return await _auth.signOut();
    } catch (e) {
      print(e.toString());
      return null;
    }
  }
}