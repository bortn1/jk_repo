package com.ab.github_api_pq.ui.main.listener;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public abstract class PaginationScrollListener extends RecyclerView.OnScrollListener {
    private int bottomVisibleElement = 3;
    private LinearLayoutManager layoutManager;

    public PaginationScrollListener(LinearLayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int visibleItemCount = layoutManager.getChildCount();
        int startLoadPosition = layoutManager.getItemCount() - bottomVisibleElement;
        int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

        if (!isLoading() && !isLastPage()) {
            if ((visibleItemCount + firstVisibleItemPosition) >= startLoadPosition
                    && firstVisibleItemPosition >= 0) {
                onListBottom();
            }
        }

    }

    protected abstract void onListBottom();

    public abstract boolean isLastPage();

    public abstract boolean isLoading();
}