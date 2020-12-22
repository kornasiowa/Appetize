package com.kornasdominika.appetize.cookbook;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kornasdominika.appetize.authorization.login.ui.LoginActivity;
import com.kornasdominika.appetize.cookbook.favorite.ui.FavoriteFragment;
import com.kornasdominika.appetize.R;
import com.kornasdominika.appetize.cookbook.account.ui.AccountFragment;
import com.kornasdominika.appetize.cookbook.home.HomeFragment;
import com.kornasdominika.appetize.cookbook.shoppinglist.ui.ShoppingFragment;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigationView = findViewById(R.id.nav_view);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();
        setOnClick();
    }

    @Override
    protected void onStart() {
        super.onStart();
        isUserLogged();
    }

    private void setOnClick() {
        navigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();
                    break;
                case R.id.navigation_favorite:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new FavoriteFragment()).commit();
                    break;
                case R.id.navigation_shopping:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new ShoppingFragment()).commit();
                    break;
                case R.id.navigation_account:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new AccountFragment()).commit();
                    break;
            }
            return true;
        });
    }

    private void isUserLogged(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finishAffinity();
        }
    }
}