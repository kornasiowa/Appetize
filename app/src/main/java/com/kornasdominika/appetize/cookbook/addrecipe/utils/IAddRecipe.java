package com.kornasdominika.appetize.cookbook.addrecipe.utils;

import android.net.Uri;

import com.kornasdominika.appetize.model.Recipe;

public interface IAddRecipe {

    String getUserId();

    void addRecipe(Recipe recipe, Uri uri);
}
