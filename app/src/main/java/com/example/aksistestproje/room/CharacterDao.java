package com.example.aksistestproje.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.aksistestproje.model.MarvelCharacter;

import java.util.List;

@Dao
public interface CharacterDao {
    @Insert
    void insert(MarvelCharacter marvelCharacter);

    @Update
    void update(MarvelCharacter marvelCharacter);

    @Delete
    void delete(MarvelCharacter marvelCharacter);

    @Query("DELETE FROM characters_table")
    void deleteAllCharacters();

    @Query("SELECT * FROM characters_table ORDER BY name")
    List<MarvelCharacter> getAllCharacters();

    @Query("SELECT 1 FROM characters_table WHERE id = :id")
    int getCharacterCount(int id);
}
