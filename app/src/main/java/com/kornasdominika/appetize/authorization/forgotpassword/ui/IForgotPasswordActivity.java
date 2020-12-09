package com.kornasdominika.appetize.authorization.forgotpassword.ui;

public interface IForgotPasswordActivity {

    boolean validateForgotPasswordForm();

    void makeMessage(String message);

    void finishActivity();
}
