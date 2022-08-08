package com.example.aksistestproje.util;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.aksistestproje.model.MarvelCharacter;
import com.example.aksistestproje.room.CharacterDao;
import com.example.aksistestproje.room.Converters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {MarvelCharacter.class}, version = 21)
@TypeConverters({Converters.class})
public abstract class CharacterDatabase extends RoomDatabase {

    public static final int NUMBER_OF_THREADS = 4;

    private static volatile CharacterDatabase INSTANCE;
    public static final ExecutorService databaseWriteExecutor
            = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public abstract CharacterDao characterDao();

    public static CharacterDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CharacterDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CharacterDatabase.class, "contact_database").addCallback(sRoomDatabaseCallback).fallbackToDestructiveMigration().allowMainThreadQueries().build();
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback() {
                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);

                    databaseWriteExecutor.execute(() -> {
                        CharacterDao characterDao = INSTANCE.characterDao();
                    });
                }
            };


}
