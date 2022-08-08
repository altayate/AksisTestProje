package com.example.aksistestproje.activities;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.aksistestproje.R;
import com.example.aksistestproje.adapter.FavoritesAdapter;
import com.example.aksistestproje.model.MarvelCharacter;
import com.example.aksistestproje.view_models.FavoritesViewModel;

import java.util.ArrayList;
import java.util.List;

public class FavoritesActivity extends AppCompatActivity {
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    ImageView backButton;
    FavoritesViewModel favoritesViewModel;
    private List<MarvelCharacter> marvelCharacters = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        recyclerView = findViewById(R.id.favorites_recyclerView);
        backButton = findViewById(R.id.backButton_imageView1);
        FavoritesAdapter adapter = new FavoritesAdapter(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        swipeRefreshLayout = findViewById(R.id.refreshLayout);

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);

        backButton.setOnClickListener(e -> onBackPressed());

        favoritesViewModel = new ViewModelProvider(this).get(FavoritesViewModel.class);

        favoritesViewModel.marvelCharactersLive.observe(this, new Observer<List<MarvelCharacter>>() {
            @Override
            public void onChanged(List<MarvelCharacter> marvelCharacters) {
                adapter.addAll(marvelCharacters);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(() -> {
            favoritesViewModel.refreshCharacters();
            adapter.addAll(favoritesViewModel.marvelCharacters);
            recyclerView.setAdapter(adapter);
            recyclerView.invalidate();
            swipeRefreshLayout.setRefreshing(false);
        });

        recyclerView.invalidate();
    }
}