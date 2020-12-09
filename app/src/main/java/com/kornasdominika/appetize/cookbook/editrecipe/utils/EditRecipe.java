package com.kornasdominika.appetize.cookbook.editrecipe.utils;

import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kornasdominika.appetize.cookbook.editrecipe.ui.IEditRecipeActivity;
import com.kornasdominika.appetize.model.Recipe;
import com.kornasdominika.appetize.service.RecipeService;
import com.kornasdominika.appetize.service.rest.APIUtils;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditRecipe implements IEditRecipe {

    private IEditRecipeActivity editRecipeActivity;

    private RecipeService recipeService;

    private String uid,
            currentImage;
    private boolean isFavourite;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public EditRecipe(IEditRecipeActivity editRecipeActivity) {
        this.editRecipeActivity = editRecipeActivity;
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
                    editRecipeActivity.setComponentsView(recipe);
                    editRecipeActivity.setSpinnerAtPosition(recipe);
                    editRecipeActivity.setImage(recipe);
                    editRecipeActivity.setIngredients(recipe);
                    editRecipeActivity.setSteps(recipe);
                    uid = recipe.getUid();
                    isFavourite = recipe.isFavorite();
                    currentImage = recipe.getImage();
                }
            }

            @Override
            public void onFailure(@NotNull Call<Recipe> call, @NotNull Throwable t) {
                Log.d("MyApp", "Error during download of recipe details");
            }
        });
    }

    @Override
    public void editRecipe(long rid, Recipe recipe, Uri uri, boolean isImageDeleted) {
        editRecipeActivity.showProgress();
        if(isImageDeleted){
            System.out.println("Tutaj 13");
            deleteOldImage(currentImage);
            saveRecipeData(rid, recipe, null);
        } else {
            if (uri == null) {
                System.out.println("Tutaj 1");
                saveRecipeData(rid, recipe, currentImage);
            } else {
                System.out.println("Tutaj w3");
                deleteOldImage(currentImage);
                uploadRecipeImage(rid, recipe, uri);
            }
        }

    }

    private void saveRecipeData(long rid, Recipe recipe, String imageUrl) {
        recipe.setUid(uid);
        recipe.setFavorite(isFavourite);
        recipe.setImage(imageUrl);
        Call<Recipe> call = recipeService.updateRecipe(rid, recipe);
        call.enqueue(new Callback<Recipe>() {
            @Override
            public void onResponse(Call<Recipe> call, Response<Recipe> response) {
                if (response.isSuccessful()) {
                    System.out.println("Tutaj 2");
                    editRecipeActivity.dismissProgress();
                    editRecipeActivity.showMessage("Recipe updated successfully.");
                    editRecipeActivity.finishActivity();
                } else {
                    System.out.println("Tutaj 3");
                    editRecipeActivity.dismissProgress();
                    editRecipeActivity.showMessage("Error occurs during updating the recipe.");
                    editRecipeActivity.finishActivity();
                }
            }

            @Override
            public void onFailure(Call<Recipe> call, Throwable t) {
                editRecipeActivity.showMessage("Error occurs during connection to server.");
            }
        });
    }

    private void uploadRecipeImage(long rid, Recipe recipe, Uri uri) {
        if (uri != null) {
            StorageReference fileReference = FirebaseStorage.getInstance()
                    .getReference().child("recipe_images").child(uri.getLastPathSegment());
            UploadTask uploadTask = fileReference.putFile(uri);

            uploadTask.addOnFailureListener(e -> {
                editRecipeActivity.dismissProgress();
                editRecipeActivity.showMessage("Error occurs during updating recipe image.");
            }).addOnSuccessListener(taskSnapshot -> {
                Task<Uri> downloadUrl = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                downloadUrl.addOnCompleteListener(task -> {
                    if (downloadUrl.isSuccessful()) {
                        String imageUrl = Objects.requireNonNull(downloadUrl.getResult()).toString();
                        Log.d("MyApp", imageUrl);
                        saveRecipeData(rid, recipe, imageUrl);
                    } else {
                        Log.d("MyApp", "Error while downloading from memory");
                    }
                });
            });
        }
    }

    private void deleteOldImage(String oldImage) {
        StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(oldImage);

        storageReference.delete().addOnSuccessListener(aVoid -> Log.d("MyApp", "Old image delete successfully"))
                .addOnFailureListener(e -> Log.d("MyApp", "Old image deleting failure"));
    }
}
