package com.kornasdominika.appetize.authorization.register.utils;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kornasdominika.appetize.authorization.register.ui.IRegisterActivity;

public class Register implements IRegister {

    private IRegisterActivity registerActivity;

    private FirebaseAuth mAuth;

    private static final String TAG = "Registartion";

    public Register(IRegisterActivity registerActivity) {
        this.registerActivity = registerActivity;
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        if (!registerActivity.validateRegistrationForm()) {
            return;
        }
        registerActivity.showProgress();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, verify email, go to login activity
                        Log.d(TAG, "createUserWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                           // sendVerificationEmail(user);
                            registerActivity.backToLoginActivity();
                        }
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        registerActivity.makeMessage("Authentication failed.");
                    }
                    registerActivity.hideProgress();
                });
    }

    private void sendVerificationEmail(FirebaseUser user) {
        user.sendEmailVerification().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                registerActivity.makeMessage("Verification email sent to " + user.getEmail());
            }
        });
    }

}
