package com.kornasdominika.appetize.cookbook.showrecipe.preparation.utils;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.kornasdominika.appetize.cookbook.showrecipe.preparation.ui.IPreparationFragment;
import com.kornasdominika.appetize.model.Recipe;
import com.kornasdominika.appetize.service.RecipeService;
import com.kornasdominika.appetize.service.rest.APIUtils;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Preparation implements IPreparation{

    private IPreparationFragment preparationFragment;

    private RecipeService recipeService;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Preparation(IPreparationFragment preparationFragment) {
        this.preparationFragment = preparationFragment;
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
                    preparationFragment.setListView(recipe);
                }
            }

            @Override
            public void onFailure(@NotNull Call<Recipe> call, @NotNull Throwable t) {
                Log.d("MyApp", "Error during download of recipe details");
            }
        });
    }
}
