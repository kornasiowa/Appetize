package com.kornasdominika.appetize.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ShoppingList implements Serializable {

    private static final long serialVersionUID = 1L;

    @SerializedName("lid")
    @Expose
    private long lid;

    @SerializedName("uid")
    @Expose
    private String uid;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("itemsList")
    @Expose
    private List<Item> itemsList = new ArrayList<Item>();

    public ShoppingList() {
    }

    public ShoppingList(long lid, String uid, String name, List<Item> itemsList) {
        this.lid = lid;
        this.uid = uid;
        this.name = name;
        this.itemsList = itemsList;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public long getLid() {
        return lid;
    }

    public void setLid(long lid) {
        this.lid = lid;
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

    public List<Item> getItemsList() {
        return itemsList;
    }

    public void setItemsList(List<Item> itemsList) {
        this.itemsList = itemsList;
    }

    @Override
    public String toString() {
        return "ShoppingList{" +
                "lid=" + lid +
                ", uid='" + uid + '\'' +
                ", name='" + name + '\'' +
                ", itemsList=" + itemsList +
                '}';
    }
}
