package com.kornasdominika.appetize.cookbook.account.utils;

import android.util.Log;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kornasdominika.appetize.cookbook.account.ui.IAccountFragment;

import static android.content.ContentValues.TAG;

public class Account implements IAccount {

    private final IAccountFragment accountFragment;

    private FirebaseAuth mAuth;

    private String userEmail;

    public Account(IAccountFragment accountFragment) {
        this.accountFragment = accountFragment;
    }

    @Override
    public void logOut() {
        mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        accountFragment.startLoginActivity();
    }

    @Override
    public void getUserName() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            userEmail = user.getEmail();
            accountFragment.setWelcomeHeader(userEmail);
        }
    }

    @Override
    public void changePassword(String currentPassword, String newPassword) {
        Log.d(TAG, "change password to account:" + userEmail);
        if (!accountFragment.validateChangePasswordForm()) {
            return;
        }

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        AuthCredential credential = EmailAuthProvider.getCredential(userEmail, currentPassword);

        user.reauthenticate(credential).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                user.updatePassword(newPassword).addOnCompleteListener(task1 -> {
                    if(task1.isSuccessful()){
                        Log.d(TAG, "Password updated");
                        accountFragment.showMessage("Password updated successfully.");
                        accountFragment.clear();
                    } else {
                        Log.d(TAG, "Error password not updated");
                    }
                });
            } else {
                Log.d(TAG, "Error auth failed");
                accountFragment.showMessage("Authentication failed.");
            }
        });
    }
}
