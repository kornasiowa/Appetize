package com.kornasdominika.appetize.cookbook.account;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.kornasdominika.appetize.R;
import com.kornasdominika.appetize.authorization.login.ui.LoginActivity;


public class AccountFragment extends Fragment {

    private FirebaseAuth mAuth;

    public AccountFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        Button btn = view.findViewById(R.id.sign_out);

        mAuth = FirebaseAuth.getInstance();

        btn.setOnClickListener(view1 -> {
            mAuth.signOut();
            startActivity(new Intent(getContext(), LoginActivity.class));
            getActivity().finishAffinity();
        });

        return view;

    }
}