package com.kornasdominika.appetize.cookbook.account.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kornasdominika.appetize.R;
import com.kornasdominika.appetize.authorization.login.ui.LoginActivity;
import com.kornasdominika.appetize.cookbook.account.utils.Account;
import com.kornasdominika.appetize.cookbook.account.utils.IAccount;

public class AccountFragment extends Fragment implements IAccountFragment {

    private IAccount account;

    private LinearLayout logOut,
            llPassword,
            llTheme;
    private TextView tvHeader;
    private ImageView ivExpand,
            ivExpand2;
    private EditText etCurrentPassword,
            etNewPassword,
            etConfirmPassword;
    private Button btnSave;
    private Switch orange,
            blue;

    private boolean passwordMode = false;
    private boolean themeMode = false;

    public AccountFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        account = new Account(this);

        findComponentsIds(view);
        setOnClick();
        setSwitches();
        account.getUserName();

        return view;
    }

    private void findComponentsIds(View view) {
        logOut = view.findViewById(R.id.log_out);
        tvHeader = view.findViewById(R.id.header);
        ivExpand = view.findViewById(R.id.expand);
        ivExpand2 = view.findViewById(R.id.expand2);
        llPassword = view.findViewById(R.id.ll_password);
        llTheme = view.findViewById(R.id.ll_theme);
        etCurrentPassword = view.findViewById(R.id.current_password);
        etNewPassword = view.findViewById(R.id.password);
        etConfirmPassword = view.findViewById(R.id.confirm_password);
        btnSave = view.findViewById(R.id.save);
        orange = view.findViewById(R.id.orange);
        blue = view.findViewById(R.id.blue);
    }

    private void setOnClick() {
        logOut.setOnClickListener(view -> {
            account.logOut();
        });

        ivExpand.setOnClickListener(view -> {
            showChangePasswordLayout();
        });

        ivExpand2.setOnClickListener(view -> {
            showChangeThemeLayout();
        });

        btnSave.setOnClickListener(view -> {
            account.changePassword(String.valueOf(etCurrentPassword.getText()), String.valueOf(etNewPassword.getText()));
        });
    }

    private void setSwitches() {
        SharedPreferences mPrefs = getActivity().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mPrefs.edit();

        BottomNavigationView navigationView = (BottomNavigationView) getActivity().findViewById(R.id.nav_view);

        boolean orangeTheme = mPrefs.getBoolean("ORANGE_THEME", false);
        if (orangeTheme) {
            orange.setChecked(true);
        } else {
            blue.setChecked(true);
        }

        orange.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                navigationView.getMenu().getItem(0).setChecked(true);
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                blue.setChecked(false);
                mEditor.putBoolean("ORANGE_THEME", true).apply();
            } else {
                if (!blue.isChecked()) {
                    orange.setChecked(true);
                }
            }
        });

        blue.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                navigationView.getMenu().getItem(0).setChecked(true);
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                orange.setChecked(false);
                mEditor.putBoolean("ORANGE_THEME", false).apply();
            } else {
                if (!orange.isChecked()) {
                    blue.setChecked(true);
                }
            }
        });

    }

    @Override
    public void startLoginActivity() {
        startActivity(new Intent(getContext(), LoginActivity.class));
        getActivity().finishAffinity();
    }

    @Override
    public void setWelcomeHeader(String user) {
        tvHeader.setText("Welcome " + user);
    }

    private void showChangePasswordLayout() {
        if (!passwordMode) {
            llPassword.setVisibility(View.VISIBLE);
            ivExpand.setImageResource(R.drawable.ic_expand_less);
            passwordMode = true;
        } else {
            llPassword.setVisibility(View.GONE);
            ivExpand.setImageResource(R.drawable.ic_expand_more);
            passwordMode = false;
        }
    }

    private void showChangeThemeLayout() {
        if (!themeMode) {
            llTheme.setVisibility(View.VISIBLE);
            ivExpand2.setImageResource(R.drawable.ic_expand_less);
            themeMode = true;
        } else {
            llTheme.setVisibility(View.GONE);
            ivExpand2.setImageResource(R.drawable.ic_expand_more);
            themeMode = false;
        }
    }

    @Override
    public boolean validateChangePasswordForm() {
        String currentPassword = etCurrentPassword.getText().toString();
        String password = etNewPassword.getText().toString();
        String passwordConfirmation = etConfirmPassword.getText().toString();

        if (TextUtils.isEmpty(currentPassword) || TextUtils.isEmpty(password)) {
            Toast.makeText(getContext(), "Empty fields.",
                    Toast.LENGTH_SHORT).show();
            return false;
        } else if (!password.equals(passwordConfirmation)) {
            Toast.makeText(getContext(), "Different passwords.",
                    Toast.LENGTH_SHORT).show();
            return false;
        } else if (password.length() < 6) {
            Toast.makeText(getContext(), "Password should have at least 6 characters long.",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void clear() {
        etNewPassword.setText("");
        etCurrentPassword.setText("");
        etConfirmPassword.setText("");
    }
}