package com.kornasdominika.appetize.cookbook.showrecipe.general.utils;

public interface IGeneral {

    void getRecipe(long rid);

    void updateRecipeAsFavorite(long rid, boolean isFavorite);

    void getUserShoppingLists();
}
