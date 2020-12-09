package com.kornasdominika.appetize.cookbook.showrecipe.general.utils;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.kornasdominika.appetize.cookbook.showrecipe.general.ui.IGeneralFragment;
import com.kornasdominika.appetize.model.Recipe;
import com.kornasdominika.appetize.service.rest.APIUtils;
import com.kornasdominika.appetize.service.RecipeService;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class General implements IGeneral {

    private IGeneralFragment generalFragment;

    private RecipeService recipeService;

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
                }
            }

            @Override
            public void onFailure(@NotNull Call<Recipe> call, @NotNull Throwable t) {
                Log.d("MyApp", "Error during download of recipe details");
            }
        });
    }

}
