package com.example.aksistestproje.activities;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.aksistestproje.R;
import com.example.aksistestproje.adapter.MyExpandableListAdapter;
import com.example.aksistestproje.model.MarvelCharacter;
import com.example.aksistestproje.room.CharacterRepo;
import com.example.aksistestproje.util.CharacterDatabase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CharacterDetails extends AppCompatActivity implements View.OnClickListener {

    TextView nameTextView;
    ImageView backButton;
    ImageView characterImageView;
    FloatingActionButton likeButton, dislikeButton;
    CharacterRepo characterRepo;
    public MarvelCharacter character;
    List<String> groupList, childList;
    Map<String, List<String>> myCollection;
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_details);
        CharacterDatabase db = CharacterDatabase.getDatabase(this.getApplicationContext());

        Bundle bundle = getIntent().getExtras();
        character = (MarvelCharacter) bundle.getSerializable("character");
        int isLiked = db.characterDao().getCharacterCount(character.getId());
        Log.d("room", "onCreate: " + isLiked);

        characterRepo = new CharacterRepo(this.getApplication());

        createGroupList();
        createCollection();

        characterImageView = findViewById(R.id.characterDetails_imageView);
        likeButton = findViewById(R.id.like_floatingActionButton);
        dislikeButton = findViewById(R.id.dislike_floatingActionButton);
        backButton = findViewById(R.id.backButton_imageView);
        expandableListView = findViewById(R.id.expandableListView);
        expandableListAdapter = new MyExpandableListAdapter(this, groupList, myCollection);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int lastExpandedPosition = -1;

            @Override
            public void onGroupExpand(int i) {
                if (lastExpandedPosition != -1 && i != lastExpandedPosition) {
                    expandableListView.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = i;
            }
        });

        likeButton.setOnClickListener(this);
        dislikeButton.setOnClickListener(this);
        backButton.setOnClickListener(this);

        if (isLiked == 1) {
            likeButton.setVisibility(View.VISIBLE);
            dislikeButton.setVisibility(View.GONE);
        } else {
            dislikeButton.setVisibility(View.VISIBLE);
            likeButton.setVisibility(View.GONE);
        }

        nameTextView = findViewById(R.id.details_name_textView);
        nameTextView.setText(character.getName());
        Glide.with(this)
                .asBitmap()
                .load(character.getImagePath())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        characterImageView.setImageBitmap(resource);
                        characterImageView.buildDrawingCache();
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });
    }

    private void createCollection() {
        myCollection = new HashMap<>();
        for (String group : groupList) {
            switch (group) {
                case "Events":
                    loadChild(character.getEvents());
                    break;
                case "Series":
                    loadChild(character.getSeries());
                    break;
                case "Comics":
                    loadChild(character.getComics());
                    break;
                case "Stories":
                    loadChild(character.getStories());
                    break;
            }
            myCollection.put(group, childList);
        }
    }

    private void loadChild(List<String> s) {
        childList = new ArrayList<>();
        childList.addAll(s);
    }

    private void createGroupList() {
        groupList = new ArrayList<>();
        if (!character.getEvents().isEmpty()) {
            groupList.add("Events");
        }
        if (!character.getSeries().isEmpty()) {
            groupList.add("Series");
        }
        if (!character.getComics().isEmpty()) {
            groupList.add("Comics");
        }
        if (!character.getStories().isEmpty()) {
            groupList.add("Stories");
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        expandableListView.setIndicatorBounds(expandableListView.getRight() - 120, expandableListView.getWidth());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.like_floatingActionButton:
                likeButton.setVisibility(View.GONE);
                dislikeButton.setVisibility(View.VISIBLE);
                characterRepo.delete(character);
                Toast.makeText(this, "Character Removed From Likes!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.dislike_floatingActionButton:
                dislikeButton.setVisibility(View.GONE);
                likeButton.setVisibility(View.VISIBLE);
                characterRepo.insert(character);
                Toast.makeText(this, "Character Liked!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.backButton_imageView:
                onBackPressed();
                break;
        }
    }
}