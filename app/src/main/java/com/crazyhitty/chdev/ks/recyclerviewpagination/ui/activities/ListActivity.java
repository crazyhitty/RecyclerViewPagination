package com.crazyhitty.chdev.ks.recyclerviewpagination.ui.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.crazyhitty.chdev.ks.recyclerviewpagination.R;
import com.crazyhitty.chdev.ks.recyclerviewpagination.models.Item;
import com.crazyhitty.chdev.ks.recyclerviewpagination.ui.adapters.PaginatedRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private RecyclerView mRecyclerViewItems;

    private PaginatedRecyclerAdapter mPaginatedRecyclerAdapter;

    private int mPageCount = 0;
    private boolean mIsLoadingNewItems = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        bindViews();
        init();
    }

    private void bindViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mRecyclerViewItems = (RecyclerView) findViewById(R.id.recycler_view_items);
    }

    private void init() {
        // set the toolbar
        setSupportActionBar(mToolbar);

        // create adapter for recycler view and set it
        mPaginatedRecyclerAdapter = new PaginatedRecyclerAdapter(getInitialDummyData());
        mRecyclerViewItems.setAdapter(mPaginatedRecyclerAdapter);

        // provide load more functionality
        mRecyclerViewItems.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                // check if loading view (last item on our list) is visible
                if (linearLayoutManager.findLastVisibleItemPosition() == mPaginatedRecyclerAdapter.getItemCount() - 1) {
                    loadMoreItems();
                }
            }
        });
    }

    private List<Item> getInitialDummyData() {
        List<Item> items = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            items.add(new Item("Kajal_" + i));
        }
        return items;
    }

    private void loadMoreItems() {
        // check if the data was already loading
        if (!mIsLoadingNewItems) {
            mPageCount++;
            mIsLoadingNewItems = true;
            Toast.makeText(getApplicationContext(), "Loading more items: pageCount: " + mPageCount, Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    int index = mPaginatedRecyclerAdapter.getItemCount() - 1;
                    for (int i = index; i < index + 10; i++) {
                        mPaginatedRecyclerAdapter.add(new Item("Kajal_" + i));
                    }
                    mIsLoadingNewItems = false;
                    Toast.makeText(getApplicationContext(), "Loading complete!", Toast.LENGTH_SHORT).show();
                }
            }, 3000);
        }
    }
}
