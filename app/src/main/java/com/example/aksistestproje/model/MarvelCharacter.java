package com.example.aksistestproje.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.aksistestproje.data.MarvelComics;
import com.example.aksistestproje.data.MarvelSeries;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

@Entity(tableName = "characters_table")
public class MarvelCharacter implements Serializable {
    private String name;
    @PrimaryKey
    private int id;
    private int seriesCount;
    private ArrayList<String> comics = new ArrayList<>();
    private ArrayList<String> series = new ArrayList<>();
    private ArrayList<String> events = new ArrayList<>();
    private ArrayList<String> stories = new ArrayList<>();
    private String imagePath;

    public MarvelCharacter() {
    }

    public MarvelCharacter(String name, int seriesCount) {
        this.name = name;
        this.seriesCount = seriesCount;
    }

    public MarvelCharacter(String name, int id, int seriesCount, ArrayList<String> comics, ArrayList<String> series, ArrayList<String> events, ArrayList<String> stories, String imagePath) {
        this.name = name;
        this.id = id;
        this.seriesCount = seriesCount;
        this.comics = comics;
        this.series = series;
        this.events = events;
        this.stories = stories;
        this.imagePath = imagePath;
    }

    public void addToComics(String s) {
        this.comics.add(s);
    }

    public void addToSeries(String s) {
        this.series.add(s);
    }

    public void addToEvents(String s) {
        this.events.add(s);
    }

    public void addToStories(String s) {
        this.stories.add(s);
    }

    public ArrayList<String> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<String> events) {
        this.events = events;
    }

    public ArrayList<String> getStories() {
        return stories;
    }

    public void setStories(ArrayList<String> stories) {
        this.stories = stories;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<String> getComics() {
        return comics;
    }

    public void setComics(ArrayList<String> comics) {
        this.comics = comics;
    }

    public ArrayList<String> getSeries() {
        return series;
    }

    public void setSeries(ArrayList<String> series) {
        this.series = series;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getSeriesCount() {
        return seriesCount;
    }

    public void setSeriesCount(int seriesCount) {
        this.seriesCount = seriesCount;
    }


}
