package com.kornasdominika.appetize.cookbook.shoppinglist.ui;

import com.kornasdominika.appetize.model.ShoppingList;

import java.util.List;

public interface IShoppingFragment {

    void setShoppingLists(List<ShoppingList> shoppingLists);

    void showMessage(String message);

    void setListAdapter(List<ShoppingList> shoppingList);

    void checkIfRecipesExists(boolean isRecipesExists, String message);

    void changeAddingView();
}
