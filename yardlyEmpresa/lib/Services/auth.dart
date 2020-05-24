import 'package:firebase_auth/firebase_auth.dart';

class AuthService{
  final FirebaseAuth _auth = FirebaseAuth.instance;

  //User _userFromFirebaseUser(FirebaseUser user){

  //}


  Future signInWithEmailAndPass(String email, String pass)async{
    try {
      AuthResult res = await _auth.signInWithEmailAndPassword(email: email, password: pass);
      FirebaseUser user = res.user;
      
    } catch (e) {
    }
  }
}