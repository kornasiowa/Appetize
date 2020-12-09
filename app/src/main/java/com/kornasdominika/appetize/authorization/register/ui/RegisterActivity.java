package com.kornasdominika.appetize.authorization.register.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.kornasdominika.appetize.R;
import com.kornasdominika.appetize.authorization.register.utils.Register;
import com.kornasdominika.appetize.authorization.register.utils.IRegister;
import com.kornasdominika.appetize.authorization.validation.Validator;

public class RegisterActivity extends AppCompatActivity implements IRegisterActivity {

    private IRegister register;

    private EditText etEmail,
            etPassword,
            etConfirmPassword;
    private Button btnSign,
            btnLog;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register = new Register(this);

        findComponentsIds();
        setOnClickListeners();
    }

    private void findComponentsIds() {
        etEmail = findViewById(R.id.email);
        etPassword = findViewById(R.id.password);
        etConfirmPassword = findViewById(R.id.confirm_password);
        btnSign = findViewById(R.id.sign_up);
        btnLog = findViewById(R.id.log_in);
        progressBar = findViewById(R.id.progress);
    }

    private void setOnClickListeners() {
        btnSign.setOnClickListener(v -> {
            register.createAccount(etEmail.getText().toString(), etPassword.getText().toString());
        });

        btnLog.setOnClickListener(v -> {
            backToLoginActivity();
        });
    }

    @Override
    public boolean validateRegistrationForm() {
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        String passwordConfirmation = etConfirmPassword.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(RegisterActivity.this, "Email required.",
                    Toast.LENGTH_SHORT).show();
            return false;
        } else if (!Validator.emailValidation(email)) {
            Toast.makeText(RegisterActivity.this, "Incorrect email format.",
                    Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(RegisterActivity.this, "Password required.",
                    Toast.LENGTH_SHORT).show();
            return false;
        } else if (!password.equals(passwordConfirmation)) {
            Toast.makeText(RegisterActivity.this, "Different passwords.",
                    Toast.LENGTH_SHORT).show();
            return false;
        } else if (password.length() < 6) {
            Toast.makeText(RegisterActivity.this, "Password should have at least 6 characters long.",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void makeMessage(String message) {
        Toast.makeText(RegisterActivity.this, message,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void backToLoginActivity() {
        finish();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

}