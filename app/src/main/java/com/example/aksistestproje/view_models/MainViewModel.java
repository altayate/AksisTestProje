package com.example.aksistestproje.view_models;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.aksistestproje.api.RetrofitClient;
import com.example.aksistestproje.data.MarvelObject;
import com.example.aksistestproje.model.MarvelCharacter;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends ViewModel {
    public MutableLiveData<ArrayList<MarvelCharacter>> marvelCharactersLive;
    public ArrayList<MarvelCharacter> marvelCharacters;
    Call<MarvelObject> specificCall, call;
    MarvelCharacter marvelCharacter;
    public boolean isLoading = false;
    public boolean isCallbackComplete = false;
    private int limit = 20;
    public int offset = 0;
    public boolean isSearched = false;

    public MainViewModel() {
        marvelCharacters = new ArrayList<>();
        marvelCharactersLive = new MutableLiveData<>();
    }

    public void setCall(){
        call = new RetrofitClient().getMyApi().getBunch(limit, this.offset);
        this.offset += limit;
    }

    public void setMarvelCharacters(Call<MarvelObject> call) {
        call.clone().enqueue(new Callback<MarvelObject>() {
            @Override
            public void onResponse(Call<MarvelObject> call, Response<MarvelObject> response) {
                isCallbackComplete = false;
                if (response.isSuccessful()) {
                    for (int i = 0; i < response.body().getMarvelData().getMarvelResults().length; ++i) {
                        String imageUrl = response.body().getMarvelData().getMarvelResults()[i].getMarvelThumbnail().getPath();
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(imageUrl).insert(4, "s");
                        marvelCharacter = new MarvelCharacter();
                        marvelCharacter.setImagePath(stringBuilder + "/detail.jpg");
                        for (int j = 0; j < response.body().getMarvelData().getMarvelResults()[i].getMarvelComics().getItems().length; ++j) {
                            marvelCharacter.addToComics(response.body().getMarvelData().getMarvelResults()[i].getMarvelComics().getItems()[j].getName());
                        }
                        for (int j = 0; j < response.body().getMarvelData().getMarvelResults()[i].getMarvelSeries().getItems().length; ++j) {
                            marvelCharacter.addToSeries(response.body().getMarvelData().getMarvelResults()[i].getMarvelSeries().getItems()[j].getName());
                        }
                        for (int j = 0; j < response.body().getMarvelData().getMarvelResults()[i].getMarvelEvents().getMarvelItems().length; ++j) {
                            marvelCharacter.addToEvents(response.body().getMarvelData().getMarvelResults()[i].getMarvelEvents().getMarvelItems()[j].getName());
                        }
                        for (int j = 0; j < response.body().getMarvelData().getMarvelResults()[i].getMarvelStories().getMarvelItems().length; ++j) {
                            marvelCharacter.addToStories(response.body().getMarvelData().getMarvelResults()[i].getMarvelStories().getMarvelItems()[j].getName());
                        }
                        marvelCharacter.setId(response.body().getMarvelData().getMarvelResults()[i].getId());
                        marvelCharacter.setName(response.body().getMarvelData().getMarvelResults()[i].getName());
                        marvelCharacter.setSeriesCount(response.body().getMarvelData().getMarvelResults()[i].getMarvelSeries().getAvailable());
                        marvelCharacters.add(marvelCharacter);
                    }
                    marvelCharactersLive.postValue(marvelCharacters);
                    isLoading = false;
                }
            }

            @Override
            public void onFailure(Call<MarvelObject> call, Throwable t) {
                Log.d("retrofit", "onFailure: " + t.getMessage());
            }
        });
    }

    public void loadSearch(String search) {
        isSearched = true;
        String searchText = search;
        specificCall = new RetrofitClient().getMyApi().searchCharacters(searchText, limit, offset);
        setMarvelCharacters(specificCall);
        setCall();
    }

    public void loadFirstPage(){
        isSearched = false;
        isLoading = true;
        call = new RetrofitClient().getMyApi().getBunch(limit, 0);
        setMarvelCharacters(call);
        offset += limit;
    }

    public void loadNextPage() {
        if (isSearched)
            offset=0;
        isSearched = false;
        isLoading = true;
        setCall();
        setMarvelCharacters(call);
    }

    public ArrayList<MarvelCharacter> getMarvelCharacters() {
        return marvelCharacters;
    }

    public void setMarvelCharacters(ArrayList<MarvelCharacter> marvelCharacters) {
        this.marvelCharacters = marvelCharacters;
    }

    public MarvelCharacter getMarvelCharacter() {
        return marvelCharacter;
    }

    public void setMarvelCharacter(MarvelCharacter marvelCharacter) {
        this.marvelCharacter = marvelCharacter;
    }
}
