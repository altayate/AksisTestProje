package com.example.aksistestproje.room;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.aksistestproje.model.MarvelCharacter;
import com.example.aksistestproje.util.CharacterDatabase;

import java.util.List;

public class CharacterRepo implements LifecycleOwner {
    private CharacterDao characterDao;
    private List<MarvelCharacter> characters;
    private LiveData<Integer> count;
    String name;

    public CharacterRepo(Application application) {
        CharacterDatabase db = CharacterDatabase.getDatabase(application);
        characterDao = db.characterDao();
        CharacterDatabase.databaseWriteExecutor.execute(() -> {
            characters = characterDao.getAllCharacters();
        });
    }

    public List<MarvelCharacter> getAllCharacters() {
        return characters;
    }

    public void insert(MarvelCharacter character) {
        try {
            CharacterDatabase.databaseWriteExecutor.execute(() -> {
                characterDao.insert(character);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteAllCharacters() {
        CharacterDatabase.databaseWriteExecutor.execute(() -> {
            characterDao.deleteAllCharacters();
        });
    }

    public void update(MarvelCharacter character) {
        CharacterDatabase.databaseWriteExecutor.execute(() -> {
            characterDao.update(character);
        });
    }

    public void delete(MarvelCharacter marvelCharacter){
        CharacterDatabase.databaseWriteExecutor.execute(()->{
            characterDao.delete(marvelCharacter);
        });
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return null;
    }
}



