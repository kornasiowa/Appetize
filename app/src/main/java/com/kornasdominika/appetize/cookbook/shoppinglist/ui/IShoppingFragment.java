package com.kornasdominika.appetize.cookbook.shoppinglist.ui;

import com.kornasdominika.appetize.model.ShoppingList;

import java.util.List;

public interface IShoppingFragment {

    void showMessage(String message);

    void setListAdapter(List<ShoppingList> shoppingList);

    void checkIfShoppingListsExists(boolean isRecipesExists, String message);

    void changeAddingView();
}
