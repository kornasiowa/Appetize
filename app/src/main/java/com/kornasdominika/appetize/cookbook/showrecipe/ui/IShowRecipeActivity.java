package com.kornasdominika.appetize.cookbook.showrecipe.ui;

public interface IShowRecipeActivity {

    void setCookingTime(int cookingTime);

    void showProgress();

    void dismissProgress();

    void finishCurrentActivity();

    void showMessage(String message);
}
