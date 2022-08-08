package com.example.aksistestproje.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MarvelSeries {
    private int available;

    @SerializedName("items")
    private MarvelItems[] marvelItems;

    public MarvelSeries(int available, MarvelItems[] marvelItems) {
        this.available = available;
        this.marvelItems = marvelItems;
    }


    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public MarvelItems[] getItems() {
        return marvelItems;
    }

    public void setItems(MarvelItems[] items) {
        this.marvelItems = items;
    }
}
