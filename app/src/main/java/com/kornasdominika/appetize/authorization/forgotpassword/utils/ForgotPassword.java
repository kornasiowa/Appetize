package com.kornasdominika.appetize.authorization.forgotpassword.utils;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.kornasdominika.appetize.authorization.forgotpassword.ui.IForgotPasswordActivity;

public class ForgotPassword implements IForgotPassword {

    private IForgotPasswordActivity forgotPasswordActivity;

    private FirebaseAuth mAuth;

    private static final String TAG = "Reset password";

    public ForgotPassword(IForgotPasswordActivity forgotPasswordActivity) {
        this.forgotPasswordActivity = forgotPasswordActivity;
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void sendPasswordResetEmail(String email) {
        Log.d(TAG, "reset password:" + email);
        if (!forgotPasswordActivity.validateForgotPasswordForm()) {
            return;
        }

        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "Email sent.");
                        forgotPasswordActivity.makeMessage("Email has been sent to your address.");
                        forgotPasswordActivity.finishActivity();
                    } else {
                        Log.d(TAG, "Email was not sent.");
                        forgotPasswordActivity.makeMessage("An error occured. Try again.");
                    }
        });
    }
}
