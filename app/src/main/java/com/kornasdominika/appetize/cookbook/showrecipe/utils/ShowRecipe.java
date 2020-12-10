package com.kornasdominika.appetize.cookbook.showrecipe.utils;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.kornasdominika.appetize.cookbook.showrecipe.ui.IShowRecipeActivity;
import com.kornasdominika.appetize.model.Recipe;
import com.kornasdominika.appetize.service.RecipeService;
import com.kornasdominika.appetize.service.rest.APIUtils;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowRecipe implements IShowRecipe {

    private IShowRecipeActivity showRecipeActivity;

    private RecipeService recipeService;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ShowRecipe(IShowRecipeActivity showRecipeActivity) {
        this.showRecipeActivity = showRecipeActivity;
        initService();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initService() {
        recipeService = APIUtils.getRecipeService();
    }

    public void deleteRecipe(long rid, String image) {
        showRecipeActivity.showProgress();

        if (image != null) {
            deleteWithImage(rid, image);
        } else {
            deleteRecipeData(rid);
        }
    }

    private void deleteRecipeData(long rid) {
        Call<Boolean> call = recipeService.deleteRecipe(rid);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful()) {
                    showRecipeActivity.dismissProgress();
                    showRecipeActivity.showMessage("The recipe has been deleted successfully.");
                    showRecipeActivity.finishCurrentActivity();
                } else {
                    showRecipeActivity.dismissProgress();
                    showRecipeActivity.showMessage("Recipe deletion failed.");
                    showRecipeActivity.finishCurrentActivity();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.d("MyApp", "Error during deleting recipe");
                showRecipeActivity.dismissProgress();
                showRecipeActivity.showMessage("Server error during deleting recipe.");
                showRecipeActivity.finishCurrentActivity();
            }
        });
    }

    private void deleteWithImage(long rid, String image) {
        StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(image);

        storageReference.delete().addOnSuccessListener(aVoid -> deleteRecipeData(rid))
                .addOnFailureListener(e -> {
                    showRecipeActivity.dismissProgress();
                    showRecipeActivity.showMessage("Server error during deleting recipe photo.");
                    showRecipeActivity.finishCurrentActivity();
                });
    }

    @Override
    public void getRecipe(long rid) {
        Call<Recipe> call = recipeService.getRecipe(rid);
        call.enqueue(new Callback<Recipe>() {
            @Override
            public void onResponse(@NotNull Call<Recipe> call, @NotNull Response<Recipe> response) {
                if (response.isSuccessful()) {
                    Recipe recipe = response.body();
                    showRecipeActivity.setCookingTime(recipe.getCookingTime());
                }
            }

            @Override
            public void onFailure(@NotNull Call<Recipe> call, @NotNull Throwable t) {
                Log.d("MyApp", "Error during download of recipe details");
            }
        });
    }
}
