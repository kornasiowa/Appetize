package com.kornasdominika.appetize.cookbook.shoppinglist.utils;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kornasdominika.appetize.cookbook.shoppinglist.ui.IShoppingFragment;
import com.kornasdominika.appetize.model.Item;
import com.kornasdominika.appetize.model.ShoppingList;
import com.kornasdominika.appetize.service.RecipeService;
import com.kornasdominika.appetize.service.rest.APIUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Shopping implements IShopping {

    private IShoppingFragment shoppingFragment;

    private RecipeService recipeService;

    public static List<ShoppingList> shoppingLists;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Shopping(IShoppingFragment shoppingFragment) {
        this.shoppingFragment = shoppingFragment;
        initService();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initService() {
        recipeService = APIUtils.getRecipeService();
    }

    private String getUserId() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            return user.getUid();
        }
        return null;
    }

    @Override
    public void addNewShoppingList(String name) {
        Call<ShoppingList> call = recipeService.addList(initializeNewShoppingListValues(name));
        call.enqueue(new Callback<ShoppingList>() {
            @Override
            public void onResponse(Call<ShoppingList> call, Response<ShoppingList> response) {
                if (response.isSuccessful()) {
                    shoppingFragment.showMessage("The shopping list has been added successfully.");
                    shoppingFragment.changeAddingView();
                    getAllUserShoppingLists();
                } else {
                    shoppingFragment.showMessage("Error adding a new shopping list.");
                }
            }

            @Override
            public void onFailure(Call<ShoppingList> call, Throwable t) {
                Log.d("MyApp", "Error adding a new shopping list");
            }
        });
    }

    private ShoppingList initializeNewShoppingListValues(String name) {
        ShoppingList newShoppingList = new ShoppingList();
        newShoppingList.setName(name);
        newShoppingList.setUid(getUserId());
        List<Item> itemsList = new ArrayList<>();
        newShoppingList.setItemsList(itemsList);
        
        return newShoppingList;
    }

    @Override
    public void getAllUserShoppingLists(){
        shoppingLists = new ArrayList<>();
        Call<List<ShoppingList>> call = recipeService.getUsersShoppingLists(getUserId());
        call.enqueue(new Callback<List<ShoppingList>>() {
            @Override
            public void onResponse(Call<List<ShoppingList>> call, Response<List<ShoppingList>> response) {
                if(response.isSuccessful()){
                    shoppingLists = response.body();
                    shoppingFragment.setListAdapter(shoppingLists);
                    if (shoppingLists.isEmpty()) {
                        shoppingFragment.checkIfShoppingListsExists(false, "No shopping lists found");
                    } else {
                        shoppingFragment.checkIfShoppingListsExists(true, null);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ShoppingList>> call, Throwable t) {
                Log.d("MyApp", "Error downloading shopping lists");
            }
        });
    }
}
