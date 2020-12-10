package com.kornasdominika.appetize.cookbook.home.maincourses.utils;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kornasdominika.appetize.cookbook.home.maincourses.ui.IMainCoursesFragment;
import com.kornasdominika.appetize.model.Recipe;
import com.kornasdominika.appetize.service.rest.APIUtils;
import com.kornasdominika.appetize.service.RecipeService;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainCourses implements IMainCourses{

    private IMainCoursesFragment mainCoursesFragment;

    private RecipeService recipeService;

    private List<Recipe> recipesList;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public MainCourses(IMainCoursesFragment mainCoursesFragment) {
        this.mainCoursesFragment = mainCoursesFragment;
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
        } else
            return null;
    }

    @Override
    public void getAllMainCoursesList(String category) {
        recipesList = new ArrayList<>();
        Call<List<Recipe>> call = recipeService.getUserRecipeByCategory(getUserId(), category);
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(@NotNull Call<List<Recipe>> call, @NotNull Response<List<Recipe>> response) {
                if (response.isSuccessful()) {
                    recipesList = response.body();
                    Collections.sort(recipesList);
                    mainCoursesFragment.setRecipeList(recipesList);
                    mainCoursesFragment.setListAdapter(recipesList);
                    if(recipesList.isEmpty()){
                        mainCoursesFragment.checkIfRecipesExists(false, "No recipes found in this category");
                    } else {
                        mainCoursesFragment.checkIfRecipesExists(true, null);
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<Recipe>> call, @NotNull Throwable t) {
                mainCoursesFragment.checkIfRecipesExists(false, "Error during download of meals receipts");
            }
        });
    }

}
