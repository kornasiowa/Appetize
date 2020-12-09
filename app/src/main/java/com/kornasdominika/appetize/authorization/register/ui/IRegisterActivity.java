package com.kornasdominika.appetize.authorization.register.ui;

public interface IRegisterActivity {

    boolean validateRegistrationForm();

    void makeMessage(String message);

    void backToLoginActivity();

    void showProgress();

    void hideProgress();
}
