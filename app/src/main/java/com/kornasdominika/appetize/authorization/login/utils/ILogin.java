package com.kornasdominika.appetize.authorization.login.utils;

public interface ILogin {

    void isUserLogged();

    void loginIntoAccount(String email, String password);

    void firebaseAuthWithGoogle(String idToken);
}
