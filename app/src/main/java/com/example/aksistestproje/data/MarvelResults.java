package com.example.aksistestproje.data;

import com.google.gson.annotations.SerializedName;

public class MarvelResults {
    private int id;
    private String name;

    @SerializedName("thumbnail")
    private MarvelThumbnail marvelThumbnail;

    @SerializedName("series")
    private MarvelSeries marvelSeries;

    @SerializedName("comics")
    private MarvelComics marvelComics;

    @SerializedName("events")
    private MarvelEvents marvelEvents;

    @SerializedName("stories")
    private MarvelStories marvelStories;

    public MarvelResults(int id, String name, MarvelThumbnail marvelThumbnail, MarvelSeries marvelSeries, MarvelComics marvelComics, MarvelEvents marvelEvents, MarvelStories marvelStories) {
        this.id = id;
        this.name = name;
        this.marvelThumbnail = marvelThumbnail;
        this.marvelSeries = marvelSeries;
        this.marvelComics = marvelComics;
        this.marvelEvents = marvelEvents;
        this.marvelStories = marvelStories;
    }

    public MarvelEvents getMarvelEvents() {
        return marvelEvents;
    }

    public void setMarvelEvents(MarvelEvents marvelEvents) {
        this.marvelEvents = marvelEvents;
    }

    public MarvelStories getMarvelStories() {
        return marvelStories;
    }

    public void setMarvelStories(MarvelStories marvelStories) {
        this.marvelStories = marvelStories;
    }

    public MarvelComics getMarvelComics() {
        return marvelComics;
    }

    public void setMarvelComics(MarvelComics marvelComics) {
        this.marvelComics = marvelComics;
    }

    public MarvelThumbnail getMarvelThumbnail() {
        return marvelThumbnail;
    }

    public void setMarvelThumbnail(MarvelThumbnail marvelThumbnail) {
        this.marvelThumbnail = marvelThumbnail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MarvelSeries getMarvelSeries() {
        return marvelSeries;
    }

    public void setMarvelSeries(MarvelSeries marvelSeries) {
        this.marvelSeries = marvelSeries;
    }
}
