package com.kornasdominika.appetize.cookbook.showrecipe.ui;

public interface IShowRecipeActivity {

    void showProgress();

    void dismissProgress();

    void finishCurrentActivity();

    void showMessage(String message);
}
