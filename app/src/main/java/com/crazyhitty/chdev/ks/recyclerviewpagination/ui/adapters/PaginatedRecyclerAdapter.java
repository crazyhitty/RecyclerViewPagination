package com.crazyhitty.chdev.ks.recyclerviewpagination.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.crazyhitty.chdev.ks.recyclerviewpagination.R;
import com.crazyhitty.chdev.ks.recyclerviewpagination.models.Item;

import java.util.List;

/**
 * Author: Kartik Sharma
 * Created on: 10/30/2016 , 3:46 PM
 * Project: RecyclerViewPagination
 */

public class PaginatedRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_TEXT = 1;
    private static final int VIEW_TYPE_LOADING = 2;

    private List<Item> mItems;

    public PaginatedRecyclerAdapter(List<Item> items) {
        this.mItems = items;
    }

    public void add(Item item) {
        mItems.add(item);
        notifyItemInserted(mItems.size() - 1);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case VIEW_TYPE_TEXT:
                View defaultView = layoutInflater.inflate(R.layout.item_default, parent, false);
                viewHolder = new ItemViewHolder(defaultView);
                break;
            case VIEW_TYPE_LOADING:
                View loadingView = layoutInflater.inflate(R.layout.item_loading, parent, false);
                viewHolder = new LoadingViewHolder(loadingView);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == mItems.size()) {
            configureLoadingViewHolder((LoadingViewHolder) holder, position);
        } else {
            configureItemViewHolder((ItemViewHolder) holder, position);
        }
    }

    private void configureItemViewHolder(ItemViewHolder itemViewHolder, int position) {
        itemViewHolder.txtName.setText(mItems.get(position).getName());
    }

    private void configureLoadingViewHolder(LoadingViewHolder loadingViewHolder, int position) {
        // do nothing
    }

    @Override
    public int getItemViewType(int position) {
        if (position == mItems.size()) {
            return VIEW_TYPE_LOADING;
        }
        return VIEW_TYPE_TEXT;
    }

    @Override
    public int getItemCount() {
        return mItems.size() + 1;
    }

    private static class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView txtName;

        public ItemViewHolder(View itemView) {
            super(itemView);
            txtName = (TextView) itemView.findViewById(R.id.text_view_name);
        }
    }

    private static class LoadingViewHolder extends RecyclerView.ViewHolder {
        private ProgressBar progressBarLoading;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBarLoading = (ProgressBar) itemView.findViewById(R.id.progress_bar_loading);
        }
    }
}