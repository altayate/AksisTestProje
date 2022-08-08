package com.example.aksistestproje.data;

import com.google.gson.annotations.SerializedName;

public class MarvelStories {
    private int available;

    @SerializedName("items")
    private MarvelItems[] marvelItems;

    public MarvelStories(int available, MarvelItems[] marvelItems) {
        this.available = available;
        this.marvelItems = marvelItems;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public MarvelItems[] getMarvelItems() {
        return marvelItems;
    }

    public void setMarvelItems(MarvelItems[] marvelItems) {
        this.marvelItems = marvelItems;
    }
}
