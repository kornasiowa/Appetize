package com.kornasdominika.appetize.cookbook.editrecipe.ui;

import com.kornasdominika.appetize.model.Recipe;

public interface IEditRecipeActivity {

    void setComponentsView(Recipe recipe);

    void setSpinnerAtPosition(Recipe recipe);

    void setImage(Recipe recipe);

    void setIngredients(Recipe recipe);

    void setSteps(Recipe recipe);

    void showProgress();

    void dismissProgress();

    void finishActivity();

    void showMessage(String message);
}
