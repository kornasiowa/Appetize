package com.kornasdominika.appetize.cookbook.showrecipe.general.utils;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kornasdominika.appetize.cookbook.showrecipe.general.ui.IGeneralFragment;
import com.kornasdominika.appetize.model.Ingredient;
import com.kornasdominika.appetize.model.Item;
import com.kornasdominika.appetize.model.Recipe;
import com.kornasdominika.appetize.model.ShoppingList;
import com.kornasdominika.appetize.service.ShoppingListService;
import com.kornasdominika.appetize.service.rest.APIUtils;
import com.kornasdominika.appetize.service.RecipeService;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class General implements IGeneral {

    private IGeneralFragment generalFragment;

    private RecipeService recipeService;
    private ShoppingListService shoppingListService;

    private List<ShoppingList> shoppingLists;
    private List<Item> itemsList;

    public static List<String> shoppingListsNames;
    public static String[] items;
    public static boolean isFavorite;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public General(IGeneralFragment generalFragment) {
        this.generalFragment = generalFragment;
        initService();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initService() {
        recipeService = APIUtils.getRecipeService();
        shoppingListService = APIUtils.getShoppingListService();
    }

    private String getUserId() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            return user.getUid();
        }
        return null;
    }

    @Override
    public void getRecipe(long rid) {
        Call<Recipe> call = recipeService.getRecipe(rid);
        call.enqueue(new Callback<Recipe>() {
            @Override
            public void onResponse(@NotNull Call<Recipe> call, @NotNull Response<Recipe> response) {
                if (response.isSuccessful()) {
                    Recipe recipe = response.body();
                    generalFragment.setComponentsView(recipe);
                    generalFragment.setImagesViews(recipe);
                    generalFragment.setListView(recipe);
                    isFavorite = recipe.isFavorite();
                    getItemsFromRecipe(recipe);
                }
            }

            @Override
            public void onFailure(@NotNull Call<Recipe> call, @NotNull Throwable t) {
                Log.d("MyApp", "Error during download of recipe details");
            }
        });
    }

    @Override
    public void updateRecipeAsFavorite(long rid, boolean isFavorite) {
        Call<Recipe> call = recipeService.updateFavorite(rid, isFavorite);
        call.enqueue(new Callback<Recipe>() {
            @Override
            public void onResponse(Call<Recipe> call, Response<Recipe> response) {
                if (response.isSuccessful()) {
                    Log.d("MyApp", "Recipe set as favorite" + isFavorite);
                }
            }

            @Override
            public void onFailure(Call<Recipe> call, Throwable t) {
                Log.d("MyApp", "Error during set recipe as favorite");
            }
        });
    }

    @Override
    public void getUserShoppingLists() {
        shoppingLists = new ArrayList<>();

        Call<List<ShoppingList>> call = shoppingListService.getUsersShoppingLists(getUserId());
        call.enqueue(new Callback<List<ShoppingList>>() {
            @Override
            public void onResponse(Call<List<ShoppingList>> call, Response<List<ShoppingList>> response) {
                if (response.isSuccessful()) {
                    shoppingLists = response.body();
                    getShoppingListsNames(shoppingLists);
                }
            }

            @Override
            public void onFailure(Call<List<ShoppingList>> call, Throwable t) {
                Log.d("MyApp", "Error during download shopping lists");
            }
        });
    }

    private void getShoppingListsNames(List<ShoppingList> list) {
        shoppingListsNames = new ArrayList<>();

        if (list.isEmpty()) {
            shoppingListsNames.add("No shopping lists found");
        } else {
            for (ShoppingList l : list) {
                shoppingListsNames.add(l.getName());
            }
        }
    }

    private void getItemsFromRecipe(Recipe recipe) {
        items = new String[recipe.getIngredientList().size()];

        int index = 0;
        for (Ingredient ingredient : recipe.getIngredientList()) {
            items[index] = ingredient.getAmount() + " " + ingredient.getIngredientName();
            index++;
        }
    }

    @Override
    public void updateShoppingList(String listName, List<String> items){
        chooseCorrectItemsList(listName);
        addNewItemsToList(items);

        Call<Boolean> call = shoppingListService.updateShoppingList(listName, itemsList);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.isSuccessful()){
                    Log.d("MyApp", "Items add to shopping list successfully");
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.d("MyApp", "Error during adding items to shopping list");
            }
        });
    }

    private void chooseCorrectItemsList(String listName) {
        for (ShoppingList list : shoppingLists) {
            if (list.getName().equals(listName)) {
                itemsList = list.getItemsList();
            }
        }
    }

    private void addNewItemsToList(List<String> items) {
        for (String name : items) {
            Item newItem = new Item(false, name);
            itemsList.add(newItem);
        }
    }
}
