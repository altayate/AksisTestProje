package com.example.aksistestproje.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aksistestproje.R;
import com.example.aksistestproje.adapter.MainAdapter;
import com.example.aksistestproje.adapter.PaginationScrollListener;
import com.example.aksistestproje.api.RetrofitClient;
import com.example.aksistestproje.data.MarvelObject;
import com.example.aksistestproje.model.MarvelCharacter;
import com.example.aksistestproje.room.CharacterRepo;
import com.example.aksistestproje.view_models.MainViewModel;

import java.util.ArrayList;

import retrofit2.Call;

public class MainActivity extends AppCompatActivity {

    static GridLayoutManager gridLayoutManager;
    RecyclerView recyclerView;
    ImageButton gridOn, gridOff, search;
    ArrayList<MarvelCharacter> marvelCharacters;
    Call<MarvelObject> specificCall;
    Call<MarvelObject> call;
    ImageView imageView, goToFavorites, clearText;
    MarvelCharacter marvelCharacter;
    CardView mainCardView;
    EditText editText;
    CharacterRepo characterRepo;
    MainAdapter adapter;
    private ProgressBar spinner;
    private boolean isLoading = false;
    private int limit = 20;
    private int offset = 20;
    private boolean isSearched = false;
    MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main);

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        imageView = findViewById(R.id.character_imageView);

        gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        characterRepo = new CharacterRepo(this.getApplication());
        adapter = new MainAdapter(this, getApplication());
        call = new RetrofitClient().getMyApi().getBunch(limit, adapter.getItemCount());

        editText = findViewById(R.id.editTextTextPersonName);
        mainCardView = findViewById(R.id.mainCardView);
        gridOn = findViewById(R.id.gridOn_viewButton);
        gridOff = findViewById(R.id.gridOff_viewButton);
        search = findViewById(R.id.search_imageButton);
        recyclerView = findViewById(R.id.recyclerViewMain);

        mainViewModel.setMarvelCharacters(call);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(gridLayoutManager);
        spinner = findViewById(R.id.progressBar1);
        goToFavorites = findViewById(R.id.goToFavorites_imageView);
        clearText = findViewById(R.id.clearText_imageView);




        recyclerView.addOnScrollListener(new PaginationScrollListener(gridLayoutManager) {
            @Override
            protected void loadMoreItems() {
                if (!mainViewModel.isLoading && !mainViewModel.isSearched) {
                    spinner.setVisibility(View.VISIBLE);
                    isLoading = true;
                    mainViewModel.loadNextPage();
                } else if (!mainViewModel.isLoading && mainViewModel.isSearched) {
                    spinner.setVisibility(View.VISIBLE);
                    isLoading = true;
                    mainViewModel.loadSearch(editText.getText().toString());
                }
                else if (gridLayoutManager.getItemCount()%limit!=0){
                    spinner.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });



        gridOn.setOnClickListener(e -> {
            gridLayoutManager.setSpanCount(1);
            gridOn.setVisibility(View.GONE);
            gridOff.setVisibility(View.VISIBLE);
        });
        gridOff.setOnClickListener(e -> {
            gridLayoutManager.setSpanCount(2);
            gridOff.setVisibility(View.GONE);
            gridOn.setVisibility(View.VISIBLE);
        });
        search.setOnClickListener(e -> {
            if (!editText.getText().toString().trim().equals("")) {
                spinner.setVisibility(View.VISIBLE);
                mainViewModel.marvelCharacters.clear();
                adapter.clearData();
                mainViewModel.offset = 0;
                call.cancel();
                mainViewModel.loadSearch(editText.getText().toString());
            }
        });
        goToFavorites.setOnClickListener(e -> {
            Intent intent = new Intent(this, FavoritesActivity.class);
            startActivity(intent);
        });
        clearText.setOnClickListener(e -> {
            if (mainViewModel.isSearched) {
                spinner.setVisibility(View.VISIBLE);
                mainViewModel.marvelCharacters.clear();
                adapter.clearData();
                mainViewModel.offset = 0;
                mainViewModel.loadNextPage();
                mainViewModel.isSearched = false;
            }
            editText.setText("");
        });

        mainViewModel.marvelCharactersLive.observe(this, marvelCharacters -> {
            adapter.addAll(marvelCharacters);
            adapter.notifyDataSetChanged();
            recyclerView.invalidate();
            spinner.setVisibility(View.INVISIBLE);
        });

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        recyclerView.invalidate();
    }

    /*
    public void setMarvelCharacters(Call call) {
        call.clone().enqueue(new Callback<MarvelObject>() {
            @Override
            public void onResponse(Call<MarvelObject> call, Response<MarvelObject> response) {
                if (response.isSuccessful()) {
                    for (int i = 0; i < response.body().getMarvelData().getMarvelResults().length; ++i) {
                        spinner.setVisibility(View.INVISIBLE);
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
                    adapter.addAll(marvelCharacters);
                    adapter.notifyItemRangeInserted(offset,limit-1);
                    isLoading = false;
                }
                if (response.body() == null) {
                    spinner.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<MarvelObject> call, Throwable t) {
                Log.d("retrofit", "onFailure: " + t.getMessage());
            }
        });

    }

     */

}