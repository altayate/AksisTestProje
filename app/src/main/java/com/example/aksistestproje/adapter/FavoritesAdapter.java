package com.example.aksistestproje.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
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

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder> {
    Context context;
    List<MarvelCharacter> data = new ArrayList<>();

    public FavoritesAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public FavoritesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);

        return new FavoritesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesViewHolder holder, int position) {
        CharacterDatabase db = CharacterDatabase.getDatabase(context);


        int isLiked = db.characterDao().getCharacterCount(data.get(position).getId());

        if (isLiked == 1) {
            holder.characterName.setText(data.get(position).getName());
            holder.characterSeriesCount.setText("Series Count: " + data.get(position).getSeriesCount());
            holder.cardView.setOnClickListener(e -> {
                Bundle bundle = new Bundle();
                bundle.putSerializable("character", data.get(position));
                Intent intent = new Intent(context, CharacterDetails.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
            });

            if (isLiked == 1) {
                holder.emptyHeart.setVisibility(View.GONE);
                holder.filledHeart.setVisibility(View.VISIBLE);
            } else {
                holder.filledHeart.setVisibility(View.GONE);
                holder.emptyHeart.setVisibility(View.VISIBLE);
            }

            Glide.with(context)
                    .asBitmap()
                    .load(data.get(position).getImagePath())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .placeholder(R.color.black)
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

            Log.d("TAG", "onBindViewHolder: " + data.get(position).getId());
        } else {
            data.remove(position);
        }
    }


    public void addAll(List<MarvelCharacter> marvelCharacter) {
        data.clear();
        data.addAll(marvelCharacter);
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class FavoritesViewHolder extends RecyclerView.ViewHolder {
        TextView characterName, characterSeriesCount;
        ImageView characterImage, filledHeart, emptyHeart;
        CardView cardView;

        public FavoritesViewHolder(@NonNull View itemView) {
            super(itemView);

            characterName = itemView.findViewById(R.id.character_name_textView);
            characterSeriesCount = itemView.findViewById(R.id.character_series_count_textView);
            characterImage = itemView.findViewById(R.id.character_imageView);
            cardView = itemView.findViewById(R.id.mainCardView);
            filledHeart = itemView.findViewById(R.id.filledHeart_imageView);
            emptyHeart = itemView.findViewById(R.id.emptyHeart_imageView);
        }
    }
}
