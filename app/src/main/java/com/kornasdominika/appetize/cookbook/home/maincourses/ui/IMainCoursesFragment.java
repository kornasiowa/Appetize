package com.kornasdominika.appetize.cookbook.home.maincourses.ui;

import com.kornasdominika.appetize.model.Recipe;

import java.util.List;

public interface IMainCoursesFragment {

    void setRecipeList(List<Recipe> recipeList);

    void setListAdapter(List<Recipe> recipesList);

    void checkIfRecipesExists(boolean isRecipesExists, String message);
}
