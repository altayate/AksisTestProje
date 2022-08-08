package com.example.aksistestproje.api;

import com.example.aksistestproje.data.MarvelObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiService {
    public static final String ts = "92a9cb104b8a48fcbd22c7ddc7b2b676";
    public static final String marvelPublicKey = "4ba5568113ac68893b317d5d91fb5c85";
    public static final String marvelPrivateKey = "e54bda2927c8b8e09f4042b92c7f3590711616b5";
    public static final String marvelHash = "92a9cb104b8a48fcbd22c7ddc7b2b676";

    String baseURL = "https://gateway.marvel.com/v1/public/";
    String charactersURL = "https://gateway.marvel.com/v1/public/characters?ts=1&apikey=4ba5568113ac68893b317d5d91fb5c85&hash=92a9cb104b8a48fcbd22c7ddc7b2b676";

    @GET("characters?ts=1&apikey=4ba5568113ac68893b317d5d91fb5c85&hash=92a9cb104b8a48fcbd22c7ddc7b2b676")
    Call<MarvelObject> searchCharacters( @Query("nameStartsWith") String s, @Query("limit") int limit, @Query("offset") int offset);

    @GET("characters?ts=1&apikey=4ba5568113ac68893b317d5d91fb5c85&hash=92a9cb104b8a48fcbd22c7ddc7b2b676")
    Call<MarvelObject> getBunch(@Query("limit") int limit, @Query("offset") int offset);
}
