package com.kornasdominika.appetize.cookbook.editrecipe.utils;

import android.net.Uri;

import com.kornasdominika.appetize.model.Recipe;

public interface IEditRecipe {

    void getRecipe(long rid);

    void editRecipe(long rid, Recipe recipe, Uri uri, boolean isImageDeleted);
}
