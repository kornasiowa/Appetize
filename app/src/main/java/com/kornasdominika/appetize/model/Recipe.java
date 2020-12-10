package com.kornasdominika.appetize.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Recipe implements Serializable, Comparable<Recipe> {

    private static final long serialVersionUID = 1L;

    @SerializedName("rid")
    @Expose
    private long rid;

    @SerializedName("uid")
    @Expose
    private String uid;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("category")
    @Expose
    private String category;

    @SerializedName("calories")
    @Expose
    private int calories;

    @SerializedName("portions")
    @Expose
    private int portions;

    @SerializedName("cookingTime")
    @Expose
    private int cookingTime;

    @SerializedName("favorite")
    @Expose
    private boolean favorite;

    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("ingredientList")
    @Expose
    private List<Ingredient> ingredientList = new ArrayList<Ingredient>();

    @SerializedName("stepList")
    @Expose
    private List<Step> stepList = new ArrayList<Step>();

    public Recipe() {
    }

    public Recipe(long rid, String uid, String name, String category, int calories, int portions, int cookingTime, boolean favorite, String image, List<Ingredient> ingredientList, List<Step> stepList) {
        this.rid = rid;
        this.uid = uid;
        this.name = name;
        this.category = category;
        this.calories = calories;
        this.portions = portions;
        this.cookingTime = cookingTime;
        this.favorite = favorite;
        this.image = image;
        this.ingredientList = ingredientList;
        this.stepList = stepList;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public long getRid() {
        return rid;
    }

    public void setRid(long rid) {
        this.rid = rid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getPortions() {
        return portions;
    }

    public void setPortions(int portions) {
        this.portions = portions;
    }

    public int getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(int cookingTime) {
        this.cookingTime = cookingTime;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Ingredient> getIngredientList() {
        return ingredientList;
    }

    public void setIngredientList(List<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }

    public List<Step> getStepList() {
        return stepList;
    }

    public void setStepList(List<Step> stepList) {
        this.stepList = stepList;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "rid=" + rid +
                ", uid=" + uid +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", calories=" + calories +
                ", portions=" + portions +
                ", cookingTime=" + cookingTime +
                ", favorite=" + favorite +
                ", ingredientList=" + ingredientList +
                ", stepList=" + stepList +
                '}';
    }

    @Override
    public int compareTo(Recipe recipe) {
        if (getName() == null || recipe.getName() == null) {
            return 0;
        }
        return getName().compareTo(recipe.getName());
    }

}
