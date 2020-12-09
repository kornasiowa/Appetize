package com.kornasdominika.appetize.cookbook.addrecipe.ui;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.kornasdominika.appetize.R;
import com.kornasdominika.appetize.cookbook.addrecipe.utils.AddRecipe;
import com.kornasdominika.appetize.cookbook.addrecipe.utils.IAddRecipe;
import com.kornasdominika.appetize.model.Ingredient;
import com.kornasdominika.appetize.model.Recipe;
import com.kornasdominika.appetize.model.Step;

import java.util.ArrayList;
import java.util.List;

public class AddRecipeActivity extends AppCompatActivity implements IAddRecipeActivity {

    private IAddRecipe addRecipe;

    private ImageView ivRecipeImage,
            ivDeleteImage;
    private EditText etRecipeName,
            etCookingTime,
            etCalories,
            etPortions;
    private Spinner spnCategory;
    private Switch swTimeMode;
    private Button btnAddRecipe;
    private ProgressBar progress;
    private Toolbar toolbar;

    private LinearLayout llIngredients,
            llSteps,
            llCookingTime;
    private TextView tvNewIngredient,
            tvNewStep,
            tvRemoveIngredient,
            tvRemoveStep;

    private List<View> viewListOfIngredients;
    private List<View> viewListOfSteps;

    private String uid,
            name,
            category,
            calories,
            portions,
            cookingTime;
    private boolean timeMode = false;

    private List<Step> listOfSteps;
    private List<Ingredient> listOfIngredients;

    private static final int IMAGE_REQUEST = 1;
    private Uri imageUri;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        addRecipe = new AddRecipe(this, getApplicationContext());
        uid = addRecipe.getUserId();

        viewListOfIngredients = new ArrayList<>();
        viewListOfSteps = new ArrayList<>();
        listOfIngredients = new ArrayList<>();
        listOfSteps = new ArrayList<>();

        findComponentsIds();
        setSpinner();
        setSwitch();
        setOnClickListeners();
    }

    private void findComponentsIds() {
        progress = findViewById(R.id.progress);
        ivRecipeImage = findViewById(R.id.recipe_image);
        etRecipeName = findViewById(R.id.recipe_name);
        etCookingTime = findViewById(R.id.recipe_time);
        etCalories = findViewById(R.id.recipe_calories);
        etPortions = findViewById(R.id.recipe_portions);
        spnCategory = findViewById(R.id.recipe_category);
        swTimeMode = findViewById(R.id.time_mode);
        btnAddRecipe = findViewById(R.id.add_recipe);
        llIngredients = findViewById(R.id.ingredient_layout);
        llSteps = findViewById(R.id.step_layout);
        tvNewIngredient = findViewById(R.id.add_ingredient);
        tvNewStep = findViewById(R.id.add_step);
        tvRemoveIngredient = findViewById(R.id.delete_ingredient);
        tvRemoveStep = findViewById(R.id.delete_step);
        llCookingTime = findViewById(R.id.cooking_time_layout);
        toolbar = findViewById(R.id.toolbar);
        ivDeleteImage = findViewById(R.id.delete_image);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void setSpinner() {
        spnCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                category = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void setSwitch() {
        swTimeMode.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                llCookingTime.setVisibility(View.GONE);
                timeMode = true;
            } else {
                llCookingTime.setVisibility(View.VISIBLE);
                timeMode = false;
            }
        });
    }

    private void setOnClickListeners() {
        toolbar.setNavigationOnClickListener(view -> finish());

        ivRecipeImage.setOnClickListener(view -> startNewIntent());

        ivDeleteImage.setOnClickListener(view -> deleteImage());

        tvNewIngredient.setOnClickListener(view -> createNewIngredient());

        tvNewStep.setOnClickListener(view -> createNewStep());

        tvRemoveIngredient.setOnClickListener(view -> {
            deleteLastViewListElement(viewListOfIngredients, llIngredients);
        });

        tvRemoveStep.setOnClickListener(view -> {
            deleteLastViewListElement(viewListOfSteps, llSteps);
        });

        btnAddRecipe.setOnClickListener(view -> {
            if (validateData()) {
                Recipe newRecipe = new Recipe();
                newRecipe.setUid(uid);
                newRecipe.setName(name);
                newRecipe.setCategory(category);
                newRecipe.setCookingTime(Integer.parseInt(cookingTime));
                newRecipe.setCalories(Integer.parseInt(calories));
                newRecipe.setPortions(Integer.parseInt(portions));
                newRecipe.setFavorite(false);
                newRecipe.setIngredientList(listOfIngredients);
                newRecipe.setStepList(listOfSteps);
                addRecipe.addRecipe(newRecipe, imageUri);
            }
        });
    }

    private void deleteImage() {
        imageUri = null;
        ivRecipeImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
        ivRecipeImage.setImageResource(R.drawable.ic_add_image);
    }

    private void startNewIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(intent, "Select a recipes picture"), IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            ivRecipeImage.setClipToOutline(true);
            ivRecipeImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
            ivRecipeImage.setImageURI(imageUri);
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
    public void finishActivity() {
        finish();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    //create new ingredient or step functions
    private void createNewIngredient() {
        LayoutInflater inflater = LayoutInflater.from(AddRecipeActivity.this);
        View row = inflater.inflate(R.layout.ingredient_layout, null);
        viewListOfIngredients.add(row);
        llIngredients.addView(row);
    }

    private void createNewStep() {
        LayoutInflater inflater = LayoutInflater.from(AddRecipeActivity.this);
        View row = inflater.inflate(R.layout.step_layout, null);
        viewListOfSteps.add(row);
        initValueOfStepNumberAndTime(row, viewListOfSteps);
        llSteps.addView(row);
    }

    private void initValueOfStepNumberAndTime(View view, List<View> list) {
        TextView count = view.findViewById(R.id.step_count);
        count.setText(String.valueOf(list.size()));
        EditText stepTime = view.findViewById(R.id.step_time);
        stepTime.setText("0");
    }

    //delete ingredient or step functions
    private void deleteLastViewListElement(List<View> list, LinearLayout ll) {
        if (list != null && !list.isEmpty()) {
            View view = list.get(list.size() - 1);
            ll.removeView(view);
            list.remove(list.size() - 1);
        }
    }

    //data validation functions
    private boolean validateData() {
        name = String.valueOf(etRecipeName.getText());
        calories = String.valueOf(etCalories.getText());
        portions = String.valueOf(etPortions.getText());
        cookingTime = String.valueOf(etCookingTime.getText());

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(calories) || TextUtils.isEmpty(portions)
                || (TextUtils.isEmpty(cookingTime) && !timeMode)) {
            Toast.makeText(getApplicationContext(), "The form cannot contain empty fields.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(category)) {
            Toast.makeText(getApplicationContext(), "The recipe must be in the selected category.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (viewListOfIngredients.isEmpty()) {
            Toast.makeText(getApplicationContext(), "The recipe should contain at least one ingredient.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (viewListOfSteps.isEmpty()) {
            Toast.makeText(getApplicationContext(), "The recipe should contain at least one step.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!checkIfIngredientsListContainEmptyFields(viewListOfIngredients)) {
            Toast.makeText(getApplicationContext(), "The list of ingredients contains empty fields.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!checkIfStepsListContainEmptyFields(viewListOfSteps)) {
            Toast.makeText(getApplicationContext(), "The list of steps contains empty fields.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            getRecipeFromForm();
            btnAddRecipe.setEnabled(false);
            return true;
        }

    }

    private boolean checkIfIngredientsListContainEmptyFields(List<View> ingredientsList) {
        boolean isNotEmpty = true;
        if (!ingredientsList.isEmpty()) {
            for (View v : ingredientsList) {
                EditText amount = v.findViewById(R.id.amount);
                EditText ingredient = v.findViewById(R.id.ingredient);
                if (TextUtils.isEmpty(String.valueOf(amount.getText())) || TextUtils.isEmpty(String.valueOf(ingredient.getText()))) {
                    isNotEmpty = false;
                }
            }
        }
        return isNotEmpty;
    }

    private boolean checkIfStepsListContainEmptyFields(List<View> stepsList) {
        boolean isNotEmpty = true;
        if (!stepsList.isEmpty()) {
            for (View v : stepsList) {
                EditText description = v.findViewById(R.id.description);
                EditText stepTime = v.findViewById(R.id.step_time);
                if (TextUtils.isEmpty(String.valueOf(description.getText())) || TextUtils.isEmpty(String.valueOf(stepTime.getText())))
                    isNotEmpty = false;
            }
        }
        return isNotEmpty;
    }

    //get all data functions
    private void getRecipeFromForm() {
        getAllIngredientsFromForm(viewListOfIngredients);
        String timeFromSteps = getAllStepsFromFormAndTotalCookingTime(viewListOfSteps);

        if (timeMode) {
            cookingTime = timeFromSteps;
        }
    }

    private void getAllIngredientsFromForm(List<View> viewListOfIngredients) {
        for (View v : viewListOfIngredients) {
            EditText amount = v.findViewById(R.id.amount);
            EditText ingredient = v.findViewById(R.id.ingredient);

            Ingredient newIngredient = new Ingredient(String.valueOf(amount.getText()), String.valueOf(ingredient.getText()));
            listOfIngredients.add(newIngredient);
        }
    }

    private String getAllStepsFromFormAndTotalCookingTime(List<View> viewListOfSteps) {
        int stepCount = 0,
                totalTime = 0;
        for (View v : viewListOfSteps) {
            stepCount++;
            EditText description = v.findViewById(R.id.description);
            EditText stepTime = v.findViewById(R.id.step_time);

            int currentStep = Integer.parseInt(String.valueOf(stepTime.getText()));
            totalTime = +currentStep;

            Step newStep = new Step(stepCount, String.valueOf(description.getText()), currentStep);
            listOfSteps.add(newStep);
        }
        return String.valueOf(totalTime);
    }

}