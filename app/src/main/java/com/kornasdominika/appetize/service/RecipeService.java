package com.kornasdominika.appetize.service;

import com.kornasdominika.appetize.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RecipeService {

    @GET("recipe/{rid}")
    Call<Recipe> getRecipe(@Path("rid") long rid);

    @GET("recipes/{uid}/category/{category}")
    Call<List<Recipe>> getUserRecipeByCategory(@Path("uid") String uid, @Path("category") String category);

    @GET("recipes/{uid}/fav/{fav}")
    Call<List<Recipe>> getUserRecipeByFavorite(@Path("uid") String uid, @Path("fav") boolean fav);

    @POST("recipe")
    Call<Recipe> addRecipe(@Body Recipe recipe);

    @PUT("recipe/{rid}")
    Call<Recipe> updateRecipe(@Path("rid") long rid, @Body Recipe updatedRecipe);

    @PUT("recipe/{rid}/{fav}")
    Call<Recipe> updateFavorite(@Path("rid") long rid, @Path("fav") boolean fav);

    @DELETE("recipe/{rid}")
    Call<Boolean> deleteRecipe(@Path("rid") long rid);

}
