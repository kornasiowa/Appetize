package com.kornasdominika.appetize.cookbook.showrecipe.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kornasdominika.appetize.R;
import com.kornasdominika.appetize.cookbook.editrecipe.ui.EditRecipeActivity;
import com.kornasdominika.appetize.cookbook.showrecipe.general.ui.GeneralFragment;
import com.kornasdominika.appetize.cookbook.showrecipe.preparation.ui.PreparationFragment;
import com.kornasdominika.appetize.cookbook.showrecipe.utils.IShowRecipe;
import com.kornasdominika.appetize.cookbook.showrecipe.utils.ShowRecipe;

public class ShowRecipeActivity extends AppCompatActivity implements IShowRecipeActivity {

    private IShowRecipe showRecipe;

    private Toolbar toolbar;
    private ProgressBar progress;

    private BottomNavigationView navigationView;

    private long rid;
    private int cookingTime;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_recipe);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new GeneralFragment()).commit();

        showRecipe = new ShowRecipe(this);

        findComponentsByIds();
        rid = getRidFromParentFragment();
        showRecipe.getRecipe(rid);
        setOnClick();
    }

    private void findComponentsByIds() {
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.nav_view);
        progress = findViewById(R.id.progress);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void setOnClick() {
        toolbar.setNavigationOnClickListener(view -> finishCurrentActivity());

        navigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_general:
                    GeneralFragment generalFragment = new GeneralFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, generalFragment).commit();
                    break;
                case R.id.navigation_steps:
                    PreparationFragment preparationFragment = new PreparationFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, preparationFragment).commit();
                    break;
            }
            return true;
        });
    }

    public long getRidFromParentFragment() {
        return getIntent().getLongExtra("RID", 0);
    }

    public String getImageFromParentFragment() {
        return getIntent().getStringExtra("IMAGE");
    }

    @Override
    public void setCookingTime(int cookingTime) {
        this.cookingTime = cookingTime;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_recipe_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.set_timer:
                createTimerDialog(cookingTime);
                return true;
            case R.id.menu_edit:
                startEditActivity();
                return true;
            case R.id.menu_delete:
                createDeletingDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void showProgress() {
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void dismissProgress() {
        progress.setVisibility(View.GONE);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void finishCurrentActivity() {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
        finishCurrentActivity();
    }

    private void startEditActivity() {
        Intent intent = new Intent(this, EditRecipeActivity.class);
        intent.putExtra("RID", rid);
        startActivityForResult(intent, 8);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 8) {
            if (resultCode == Activity.RESULT_OK) {
                finish();
                startActivity(getIntent());
            }
        }
    }

    private void createDeletingDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Delete recipe");
        dialog.setMessage("This operation is irreversible. Are you sure you want to delete the recipe?");
        dialog.setPositiveButton("Yes", (dialogInterface, i) -> {
            showRecipe.deleteRecipe(rid, getImageFromParentFragment());
        });

        dialog.setNegativeButton("No", (dialogInterface, i) -> dialogInterface.dismiss());

        AlertDialog alertDialog = dialog.create();
        alertDialog.show();
    }

    private void createTimerDialog(int cookingTime) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Set timer");
        dialog.setMessage("Do you want to start the cooking time counting down " + cookingTime + " minutes?");
        dialog.setPositiveButton("Yes", (dialogInterface, i) -> {
            setTimer(cookingTime);
        });

        dialog.setNegativeButton("No", (dialogInterface, i) -> dialogInterface.dismiss());

        AlertDialog alertDialog = dialog.create();
        alertDialog.show();
    }

    private void setTimer(int cookingTime) {
        Intent intent = new Intent(AlarmClock.ACTION_SET_TIMER)
                .putExtra(AlarmClock.EXTRA_LENGTH, cookingTime)
                .putExtra(AlarmClock.EXTRA_SKIP_UI, true);
        startActivity(intent);
    }
}