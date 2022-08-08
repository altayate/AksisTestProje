package com.example.aksistestproje.view_models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.aksistestproje.model.MarvelCharacter;
import com.example.aksistestproje.util.CharacterDatabase;

import java.util.List;

public class FavoritesViewModel extends AndroidViewModel {
    public MutableLiveData<List<MarvelCharacter>> marvelCharactersLive = new MutableLiveData<>();
    public List<MarvelCharacter> marvelCharacters;
    CharacterDatabase db;

    public FavoritesViewModel(@NonNull Application application) {
        super(application);

        db = CharacterDatabase.getDatabase(application);
        marvelCharacters = db.characterDao().getAllCharacters();
        marvelCharactersLive.postValue(marvelCharacters);
    }

    public void refreshCharacters(){
        marvelCharacters.clear();
        marvelCharacters = db.characterDao().getAllCharacters();
        marvelCharactersLive.setValue(marvelCharacters);
    }
}
