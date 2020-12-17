package com.kornasdominika.appetize.cookbook.showrecipe.general.utils;


import java.util.List;

public interface IGeneral {

    void getRecipe(long rid);

    void updateRecipeAsFavorite(long rid, boolean isFavorite);

    void getUserShoppingLists();

    void updateShoppingList(String listName, List<String> items);
}
