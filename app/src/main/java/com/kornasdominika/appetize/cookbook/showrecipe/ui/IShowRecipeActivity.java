package com.kornasdominika.appetize.cookbook.showrecipe.ui;

public interface IShowRecipeActivity {

    void showProgress();

    void dismissProgress();

    void finishActivity();

    void showMessage(String message);
}
