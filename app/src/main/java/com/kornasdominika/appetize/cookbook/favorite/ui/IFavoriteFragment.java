package com.kornasdominika.appetize.cookbook.favorite.ui;

import com.kornasdominika.appetize.model.Recipe;

import java.util.List;

public interface IFavoriteFragment {

    void setListAdapter(List<Recipe> recipesList);

    void checkIfRecipesExists(boolean isRecipesExists, String message);
}
