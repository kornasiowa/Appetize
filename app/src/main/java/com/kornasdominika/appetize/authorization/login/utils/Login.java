package com.kornasdominika.appetize.authorization.login.utils;

import android.util.Log;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.kornasdominika.appetize.authorization.login.ui.ILoginActivity;

public class Login implements ILogin {

    private ILoginActivity loginActivity;

    private FirebaseAuth mAuth;
    private FirebaseUser user;

    private static final String TAG = "Login";

    public Login(ILoginActivity loginActivity) {
        this.loginActivity = loginActivity;
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void isUserLogged() {
        user = mAuth.getCurrentUser();
        if (user != null) {
            loginActivity.goToMainActivity();
        }
    }

    @Override
    public void loginIntoAccount(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!loginActivity.validateLoginForm()) {
            return;
        }
        loginActivity.showProgress();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, go to main application activity
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            Log.d(TAG, "signInWithEmail:success");
                            loginActivity.goToMainActivity();
                        }
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        loginActivity.makeMessage("Authentication failed.");
                    }
                    loginActivity.hideProgress();
                });
    }

    @Override
    public void firebaseAuthWithGoogle(String idToken) {
        loginActivity.showProgress();
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithCredential:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        loginActivity.goToMainActivity();
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithCredential:failure", task.getException());
                        loginActivity.makeMessage("Authentication failed.");
                    }
                    loginActivity.hideProgress();
                });
    }
}
