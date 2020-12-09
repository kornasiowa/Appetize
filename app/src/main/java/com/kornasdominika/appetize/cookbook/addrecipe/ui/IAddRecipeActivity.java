package com.kornasdominika.appetize.cookbook.addrecipe.ui;

public interface IAddRecipeActivity {

    void showProgress();

    void dismissProgress();

    void finishActivity();

    void showMessage(String message);
}
