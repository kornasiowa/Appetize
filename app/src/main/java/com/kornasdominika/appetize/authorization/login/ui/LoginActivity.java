package com.kornasdominika.appetize.authorization.login.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.kornasdominika.appetize.R;
import com.kornasdominika.appetize.authorization.forgotpassword.ui.ForgotPasswordActivity;
import com.kornasdominika.appetize.authorization.register.ui.RegisterActivity;
import com.kornasdominika.appetize.authorization.login.utils.Login;
import com.kornasdominika.appetize.authorization.login.utils.ILogin;
import com.kornasdominika.appetize.authorization.validation.Validator;
import com.kornasdominika.appetize.cookbook.MainActivity;

public class LoginActivity extends AppCompatActivity implements ILoginActivity {

    private ILogin login;

    private EditText etEmail,
            etPassword;
    private TextView tvForgotPassword;
    private Button btnLog,
            btnSign;
    private ImageView btnGoogleLog;
    private ProgressBar progressBar;

    private GoogleSignInClient mGoogleSignInClient;

    private static final int RC_SIGN_IN = 9001;
    private static final String TAG = "GoogleActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = new Login(this);

        findComponentsIds();
        setOnClickListeners();
        configureGoogleSignIn();
    }

    @Override
    protected void onStart() {
        super.onStart();
        login.isUserLogged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                login.firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }

    private void findComponentsIds() {
        etEmail = findViewById(R.id.email);
        etPassword = findViewById(R.id.password);
        tvForgotPassword = findViewById(R.id.forgot_pass);
        btnLog = findViewById(R.id.log_in);
        btnGoogleLog = findViewById(R.id.log_google);
        btnSign = findViewById(R.id.sign_up);
        progressBar = findViewById(R.id.progress);
    }

    private void setOnClickListeners() {
        tvForgotPassword.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
        });

        btnLog.setOnClickListener(v -> {
            login.loginIntoAccount(etEmail.getText().toString(), etPassword.getText().toString());
        });

        btnGoogleLog.setOnClickListener(v -> {
            signInWithGoogle();
        });

        btnSign.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });
    }

    private void configureGoogleSignIn(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void signInWithGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public boolean validateLoginForm() {
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(LoginActivity.this, "Email required.",
                    Toast.LENGTH_SHORT).show();
            return false;
        } else if (!Validator.emailValidation(email)) {
            Toast.makeText(LoginActivity.this, "Incorrect email format.",
                    Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(LoginActivity.this, "Password required.",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void makeMessage(String message){
        Toast.makeText(LoginActivity.this, message,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void goToMainActivity() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finishAffinity();
    }

    @Override
    public void showProgress(){
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress(){
        progressBar.setVisibility(View.INVISIBLE);
    }

}