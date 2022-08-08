package com.example.aksistestproje.adapter;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.aksistestproje.activities.CharacterDetails;
import com.example.aksistestproje.R;
import com.example.aksistestproje.model.MarvelCharacter;
import com.example.aksistestproje.util.CharacterDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder> {

    Context context;
    ArrayList<MarvelCharacter> data;
    Application application;

    public MainAdapter(Context ct, Application application) {
        context = ct;
        this.application = application;
        this.data = new ArrayList<>();
    }

    public ArrayList<MarvelCharacter> getData() {
        return data;
    }

    public void setData(ArrayList<MarvelCharacter> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);

        return new MyViewHolder(view);
    }

    public void add(MarvelCharacter marvelCharacter) {
        if (!data.contains(marvelCharacter)) {
            data.add(marvelCharacter);
            notifyItemInserted(data.size() - 1);
            Log.d("TAG", "add: " + marvelCharacter.getName());
        }
    }

    public void addAll(List<MarvelCharacter> marvelCharacter) {
        data.clear();
        data.addAll(marvelCharacter);
    }

    public void clearData() {
        int size = data.size();
        data.clear();
        notifyItemRangeRemoved(0, size);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CharacterDatabase db = CharacterDatabase.getDatabase(context);
        holder.characterName.setText(data.get(position).getName());
        holder.characterSeriesCount.setText("Series Count: " + data.get(position).getSeriesCount());

        int isLiked = db.characterDao().getCharacterCount(data.get(position).getId());
        Log.d("room", "onCreate: " + isLiked);

        if (isLiked == 1) {
            holder.emptyHeart.setVisibility(View.GONE);
            holder.filledHeart.setVisibility(View.VISIBLE);
        } else {
            holder.filledHeart.setVisibility(View.GONE);
            holder.emptyHeart.setVisibility(View.VISIBLE);
        }

        holder.cardView.setOnClickListener(e -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("character", data.get(position));
            Intent intent = new Intent(context, CharacterDetails.class);
            intent.putExtras(bundle);
            context.startActivity(intent);
        });


        Log.d("image", "onBindViewHolder: " + data.get(position).getImagePath());


        Glide.with(context)
                .asBitmap()
                .load(data.get(position).getImagePath())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        holder.characterImage.setImageBitmap(resource);
                        holder.characterImage.buildDrawingCache();
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });

    }


    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView characterImage, filledHeart, emptyHeart;
        TextView characterName, characterSeriesCount;
        CardView cardView;
        ImageButton gridOn, gridOff;
        ConstraintLayout constraintLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            characterImage = itemView.findViewById(R.id.character_imageView);
            filledHeart = itemView.findViewById(R.id.filledHeart_imageView);
            emptyHeart = itemView.findViewById(R.id.emptyHeart_imageView);
            characterName = itemView.findViewById(R.id.character_name_textView);
            characterSeriesCount = itemView.findViewById(R.id.character_series_count_textView);
            cardView = itemView.findViewById(R.id.mainCardView);
            gridOn = itemView.findViewById(R.id.gridOn_viewButton);
            gridOff = itemView.findViewById(R.id.gridOff_viewButton);
            constraintLayout = itemView.findViewById(R.id.main_constraintLayout);
        }
    }
}
