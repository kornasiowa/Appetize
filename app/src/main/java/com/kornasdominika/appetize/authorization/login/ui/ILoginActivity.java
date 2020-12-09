package com.kornasdominika.appetize.authorization.login.ui;

public interface ILoginActivity {

    boolean validateLoginForm();

    void makeMessage(String message);

    void goToMainActivity();

    void showProgress();

    void hideProgress();
}
