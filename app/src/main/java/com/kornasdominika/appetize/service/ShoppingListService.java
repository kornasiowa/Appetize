package com.kornasdominika.appetize.service;

import com.kornasdominika.appetize.model.Item;
import com.kornasdominika.appetize.model.ShoppingList;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ShoppingListService {

    @POST("shoppingList")
    Call<ShoppingList> addList(@Body ShoppingList shoppingList);

    @GET("shoppingList/{uid}")
    Call<List<ShoppingList>> getUsersShoppingLists(@Path("uid") String uid);

    @GET("shoppingList/{uid}/{name}")
    Call<Boolean> findShoppingListByName(@Path("uid") String uid, @Path("name") String name);

    @GET("shoppingList/details/{lid}")
    Call<ShoppingList> getShoppingListById(@Path("lid") long lid);

    @DELETE("shoppingList/{lid}")
    Call<Boolean> deleteShoppingList(@Path("lid") long lid);

    @PUT("shoppingList/items/{lid}")
    Call<ShoppingList> updateShoppingList(@Path("lid") long lid, @Body List<Item> itemList);

    @PUT("shoppingList/recipeItems/{name}")
    Call<Boolean> updateShoppingList(@Path("name") String name, @Body List<Item> itemList);
}
