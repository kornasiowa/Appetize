package com.kornasdominika.appetize.service.rest;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.kornasdominika.appetize.service.RecipeService;
import com.kornasdominika.appetize.service.ShoppingListService;

public class APIUtils {

    private APIUtils() {
    }

    private static final String API_URL = "http://10.0.2.2:8080/appetize/";

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static RecipeService getRecipeService() {
        return RetrofitClient.getClient(API_URL).create(RecipeService.class);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static ShoppingListService getShoppingListService() {
        return RetrofitClient.getClient(API_URL).create(ShoppingListService.class);
    }
}


