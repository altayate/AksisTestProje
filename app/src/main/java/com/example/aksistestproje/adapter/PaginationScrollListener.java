package com.example.aksistestproje.adapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public abstract class PaginationScrollListener extends RecyclerView.OnScrollListener {
    private GridLayoutManager layoutManager;

    public PaginationScrollListener(GridLayoutManager gridLayoutManager){
        this.layoutManager = gridLayoutManager;
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        Log.d("TAG", "onScrolled: ");

        int visibleItemCount = layoutManager.getChildCount();
        int maxItemCount = layoutManager.getItemCount();
        int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

        Log.d("onScrolled", "visibleItemCount: " + visibleItemCount + " maxItemCount: " + maxItemCount + " firstVisible: " + firstVisibleItemPosition);

        if (visibleItemCount+firstVisibleItemPosition == maxItemCount){
            loadMoreItems();
        }
    }

    protected abstract void loadMoreItems();
    public abstract boolean isLoading();
}
