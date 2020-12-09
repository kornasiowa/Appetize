package com.kornasdominika.appetize.cookbook.home.desserts.ui;

import com.kornasdominika.appetize.model.Recipe;

import java.util.List;

public interface IDessertsFragment {

    void setListAdapter(List<Recipe> recipesList);

    void checkIfRecipesExists(boolean isRecipesExists, String message);
}
