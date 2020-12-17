package com.kornasdominika.appetize.cookbook.shoppinglistdetails.utils;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.kornasdominika.appetize.cookbook.shoppinglistdetails.ui.IShoppingListDetailsActivity;
import com.kornasdominika.appetize.model.Item;
import com.kornasdominika.appetize.model.ShoppingList;
import com.kornasdominika.appetize.service.ShoppingListService;
import com.kornasdominika.appetize.service.rest.APIUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShoppingListDetails implements IShoppingListDetails{

    private IShoppingListDetailsActivity shoppingListDetailsActivity;

    private ShoppingListService shoppingListService;

    public static List<Item> itemList;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ShoppingListDetails(IShoppingListDetailsActivity shoppingListDetailsActivity) {
        this.shoppingListDetailsActivity = shoppingListDetailsActivity;
        initService();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initService() {
        shoppingListService = APIUtils.getShoppingListService();
    }

    @Override
    public void getShoppingListDetails(long lid){
        itemList = new ArrayList<>();

        Call<ShoppingList> call = shoppingListService.getShoppingListById(lid);
        call.enqueue(new Callback<ShoppingList>() {
            @Override
            public void onResponse(Call<ShoppingList> call, Response<ShoppingList> response) {
                if(response.isSuccessful()){
                    ShoppingList shoppingList = response.body();
                    shoppingListDetailsActivity.setComponentsView(shoppingList);
                    itemList = shoppingList.getItemsList();
                    shoppingListDetailsActivity.setListAdapter(itemList);
                }
            }

            @Override
            public void onFailure(Call<ShoppingList> call, Throwable t) {
                Log.d("MyApp", "Error during loading shopping list details");
            }
        });

    }

    @Override
    public void deleteShoppingList(long lid){
        Call<Boolean> call = shoppingListService.deleteShoppingList(lid);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.isSuccessful()){
                    shoppingListDetailsActivity.showMessage("The shopping list has been deleted successfully.");
                } else {
                    shoppingListDetailsActivity.showMessage("Shopping list deletion failed.");
                }
                shoppingListDetailsActivity.finishCurrentActivity();
            }
            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.d("MyApp", "Error during deleting shopping list");
                shoppingListDetailsActivity.showMessage("Server error during deleting shopping list.");
                shoppingListDetailsActivity.finishCurrentActivity();
            }
        });
    }

    @Override
    public void addNewItemToList(long lid, String item){
        itemList.add(new Item(false, item));

        Call<ShoppingList> call = shoppingListService.updateShoppingList(lid, itemList);
        call.enqueue(new Callback<ShoppingList>() {
            @Override
            public void onResponse(Call<ShoppingList> call, Response<ShoppingList> response) {
                Log.d("MyApp", "Success during adding new item to shopping list");
                getShoppingListDetails(lid);
                shoppingListDetailsActivity.clear();
            }

            @Override
            public void onFailure(Call<ShoppingList> call, Throwable t) {
                Log.d("MyApp", "Error during  adding new item to shopping list");
            }
        });
    }

    @Override
    public void updateItemsList(long lid, List<Item> list){
        Call<ShoppingList> call = shoppingListService.updateShoppingList(lid, list);
        call.enqueue(new Callback<ShoppingList>() {
            @Override
            public void onResponse(Call<ShoppingList> call, Response<ShoppingList> response) {
                Log.d("MyApp", "Success during updating shopping list");
                getShoppingListDetails(lid);
            }

            @Override
            public void onFailure(Call<ShoppingList> call, Throwable t) {
                Log.d("MyApp", "Error during updating shopping list");
            }
        });
    }
}
