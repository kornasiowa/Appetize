package com.kornasdominika.appetize.cookbook.account.ui;

public interface IAccountFragment {

    void startLoginActivity();

    void setWelcomeHeader(String user);

    boolean validateChangePasswordForm();

    void showMessage(String message);

    void clear();
}
