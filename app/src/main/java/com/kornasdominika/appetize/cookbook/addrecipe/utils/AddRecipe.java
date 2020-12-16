package com.kornasdominika.appetize.cookbook.addrecipe.utils;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kornasdominika.appetize.cookbook.addrecipe.ui.IAddRecipeActivity;
import com.kornasdominika.appetize.model.Recipe;
import com.kornasdominika.appetize.service.rest.APIUtils;
import com.kornasdominika.appetize.service.RecipeService;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddRecipe implements IAddRecipe {

    private IAddRecipeActivity addRecipeActivity;
    private Context context;

    private RecipeService recipeService;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public AddRecipe(IAddRecipeActivity addRecipeActivity, Context context) {
        this.addRecipeActivity = addRecipeActivity;
        this.context = context;
        initService();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initService() {
        recipeService = APIUtils.getRecipeService();
    }

    @Override
    public String getUserId() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            return user.getUid();
        }
        return null;
    }

    @Override
    public void addRecipe(Recipe recipe, Uri uri) {
        addRecipeActivity.showProgress();
        if(uri == null){
            saveRecipeData(recipe, null);
        } else {
            uploadRecipeImage(recipe, uri);
        }
    }

    private void saveRecipeData(Recipe recipe, String imageUrl) {
        recipe.setImage(imageUrl);
        Call<Recipe> call = recipeService.addRecipe(recipe);
        call.enqueue(new Callback<Recipe>() {
            @Override
            public void onResponse(Call<Recipe> call, Response<Recipe> response) {
                if (response.isSuccessful()) {
                    addRecipeActivity.dismissProgress();
                    addRecipeActivity.showMessage("Recipe added successfully.");
                    addRecipeActivity.finishActivity();
                } else {
                    addRecipeActivity.dismissProgress();
                    addRecipeActivity.showMessage("Error occurs during addition new recipe.");
                    addRecipeActivity.finishActivity();
                }
            }

            @Override
            public void onFailure(Call<Recipe> call, Throwable t) {
                addRecipeActivity.showMessage("Error occurs during connection to server.");
            }
        });
    }

    private void uploadRecipeImage(Recipe recipe, Uri uri) {
        if (uri != null) {
            StorageReference fileReference = FirebaseStorage.getInstance()
                    .getReference().child("recipe_images").child("image" + uri.getLastPathSegment());
            UploadTask uploadTask = fileReference.putFile(uri);

            uploadTask.addOnFailureListener(e -> {
                addRecipeActivity.dismissProgress();
                addRecipeActivity.showMessage("Error occurs during addition recipe image.");
            }).addOnSuccessListener(taskSnapshot -> {
                Task<Uri> downloadUrl = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                downloadUrl.addOnCompleteListener(task -> {
                    if (downloadUrl.isSuccessful()) {
                        String imageUrl = Objects.requireNonNull(downloadUrl.getResult()).toString();
                        saveRecipeData(recipe, imageUrl);
                    } else {
                        Log.d("MyApp", "Error while downloading from memory");
                    }
                });

            });
        }
    }

}
