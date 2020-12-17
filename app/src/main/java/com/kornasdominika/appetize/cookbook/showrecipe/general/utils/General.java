package com.kornasdominika.appetize.cookbook.showrecipe.general.utils;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kornasdominika.appetize.cookbook.shoppinglist.utils.Shopping;
import com.kornasdominika.appetize.cookbook.showrecipe.general.ui.IGeneralFragment;
import com.kornasdominika.appetize.model.Recipe;
import com.kornasdominika.appetize.model.ShoppingList;
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

    private List<ShoppingList> shoppingLists;

    public static boolean isFavorite;
    public static List<String> shoppingListsNames;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public General(IGeneralFragment generalFragment) {
        this.generalFragment = generalFragment;
        initService();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initService() {
        recipeService = APIUtils.getRecipeService();
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

    private String getUserId() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            return user.getUid();
        }
        return null;
    }

    public void getUserShoppingLists(){
        shoppingLists = new ArrayList<>();

        Call<List<ShoppingList>> call = recipeService.getUsersShoppingLists(getUserId());
        call.enqueue(new Callback<List<ShoppingList>>() {
            @Override
            public void onResponse(Call<List<ShoppingList>> call, Response<List<ShoppingList>> response) {
                if(response.isSuccessful()){
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

    private void getShoppingListsNames(List<ShoppingList> shoppingLists){
        shoppingListsNames = new ArrayList<>();

        for (ShoppingList sl : shoppingLists){
            shoppingListsNames.add(sl.getName());
            System.out.println(sl.getName());
        }
    }
}
