package com.kornasdominika.appetize.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Ingredient implements Serializable {

    @SerializedName("amount")
    @Expose
    private String amount;

    @SerializedName("ingredientName")
    @Expose
    private String ingredientName;

    public Ingredient() {
    }

    public Ingredient(String amount, String ingredientName) {
        this.amount = amount;
        this.ingredientName = ingredientName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String name) {
        this.ingredientName = ingredientName;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "amount='" + amount + '\'' +
                ", name='" + ingredientName + '\'' +
                '}';
    }
}

