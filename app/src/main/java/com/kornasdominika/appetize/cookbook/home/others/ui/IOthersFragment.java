package com.kornasdominika.appetize.cookbook.home.others.ui;

import com.kornasdominika.appetize.model.Recipe;

import java.util.List;

public interface IOthersFragment {

    void setListAdapter(List<Recipe> recipesList);

    void checkIfRecipesExists(boolean isRecipesExists, String message);
}
