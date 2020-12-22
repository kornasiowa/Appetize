package com.kornasdominika.appetize.cookbook.account.utils;

public interface IAccount {

    void logOut();

    void getUserName();

    void changePassword(String currentPassword, String newPassword);
}
