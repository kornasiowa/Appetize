package com.kornasdominika.appetize.cookbook.shoppinglistdetails.utils;


import com.kornasdominika.appetize.model.Item;

import java.util.List;

public interface IShoppingListDetails {

    void getShoppingListDetails(long lid);

    void deleteShoppingList(long lid);

    void addNewItemToList(long lid, String item);

    void updateItemsList(long lid, List<Item> list);
}
