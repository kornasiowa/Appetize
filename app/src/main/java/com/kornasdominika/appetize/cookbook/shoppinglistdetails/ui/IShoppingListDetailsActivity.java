package com.kornasdominika.appetize.cookbook.shoppinglistdetails.ui;

import com.kornasdominika.appetize.model.Item;
import com.kornasdominika.appetize.model.ShoppingList;

import java.util.List;

public interface IShoppingListDetailsActivity {

    void setComponentsView(ShoppingList shoppingList);

    void setListAdapter(List<Item> list);

    void showMessage(String message);

    void clear();

    void finishCurrentActivity();
}
