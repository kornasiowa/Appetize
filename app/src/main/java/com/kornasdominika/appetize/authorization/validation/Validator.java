package com.kornasdominika.appetize.authorization.validation;

public class Validator {

    public static boolean emailValidation(String email){
        return email.matches("^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
    }

    public static boolean nameValidation(String name) {
        return name.matches("^([a-zA-Z])+([\\w.])+$");
    }
}
