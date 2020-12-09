package com.kornasdominika.appetize.authorization.forgotpassword.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.kornasdominika.appetize.R;
import com.kornasdominika.appetize.authorization.forgotpassword.utils.ForgotPassword;
import com.kornasdominika.appetize.authorization.forgotpassword.utils.IForgotPassword;
import com.kornasdominika.appetize.authorization.validation.Validator;

public class ForgotPasswordActivity extends AppCompatActivity implements IForgotPasswordActivity {

    private IForgotPassword forgotPassword;

    private ImageView ivBack;
    private EditText etEmail;
    private Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        forgotPassword = new ForgotPassword(this);

        findComponentsIds();
        setOnClickListeners();
    }

    private void findComponentsIds() {
        ivBack = findViewById(R.id.back);
        etEmail = findViewById(R.id.email);
        btnSend = findViewById(R.id.send);
    }

    private void setOnClickListeners() {
        ivBack.setOnClickListener(v -> {
            finish();
        });

        btnSend.setOnClickListener(v -> {
            forgotPassword.sendPasswordResetEmail(etEmail.getText().toString());

        });
    }

    @Override
    public boolean validateForgotPasswordForm() {
        String email = etEmail.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(ForgotPasswordActivity.this, "Email required.",
                    Toast.LENGTH_SHORT).show();
            return false;
        } else if (!Validator.emailValidation(email)) {
            Toast.makeText(ForgotPasswordActivity.this, "Incorrect email format.",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void makeMessage(String message){
        Toast.makeText(ForgotPasswordActivity.this, message,
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void finishActivity(){
        finish();
    }
}