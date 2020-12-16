package com.kornasdominika.appetize.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Item implements Serializable {

    @SerializedName("bought")
    @Expose
    private boolean bought;

    @SerializedName("itemName")
    @Expose
    private String itemName;

    public Item() {
    }

    public Item(boolean bought, String itemName) {
        this.bought = bought;
        this.itemName = itemName;
    }

    public boolean isBought() {
        return bought;
    }

    public void setBought(boolean bought) {
        this.bought = bought;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    @Override
    public String toString() {
        return "Item{" +
                "bought=" + bought +
                ", itemName='" + itemName + '\'' +
                '}';
    }
}

