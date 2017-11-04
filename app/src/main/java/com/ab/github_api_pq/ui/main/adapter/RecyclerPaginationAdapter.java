package com.ab.github_api_pq.ui.main.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ab.github_api_pq.R;
import com.ab.github_api_pq.model.GithubRepoModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RecyclerPaginationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private boolean isLocal = false;

    private List<GithubRepoModel> githubRepoModels;

    private boolean isLoadingAdded = false;

    public RecyclerPaginationAdapter() {
        this.githubRepoModels = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case ITEM:
                View itemView = inflater.inflate(R.layout.item_list, parent, false);
                ButterKnife.bind(this, itemView);
                viewHolder = new DataViewHolder(itemView);
                break;
            case LOADING:
                View loaderView = inflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new LoadingViewHolder(loaderView);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        GithubRepoModel githubRepoModel = githubRepoModels.get(position);

        switch (getItemViewType(position)) {
            case ITEM:
                DataViewHolder dataViewHolder = (DataViewHolder) holder;
                dataViewHolder.textView.setText(githubRepoModel.getFullName());
                dataViewHolder.textId.setText(getDataPosition(githubRepoModel));
                break;
            case LOADING:
                break;
        }

    }

    private String getDataPosition(GithubRepoModel githubRepoModel) {
        int listValuePos = githubRepoModels.indexOf(githubRepoModel) + 1;
        return listValuePos + ".";
    }

    @Override
    public int getItemViewType(int position) {
        return (position == githubRepoModels.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }


    @Override
    public int getItemCount() {
        return githubRepoModels == null ? 0 : githubRepoModels.size();
    }


    private void add(GithubRepoModel r) {
        githubRepoModels.add(r);
        notifyItemInserted(githubRepoModels.size() - 1);
    }

    public void addAllFirstTime(List<GithubRepoModel> githubRepoModels) {
        isLocal = false;
        clear();

        for (GithubRepoModel result : githubRepoModels) {
            add(result);
        }
    }

    public void addAll(List<GithubRepoModel> githubRepoModels) {
        if (isLocal) {
            isLocal = false;
            clear();
        }

        for (GithubRepoModel result : githubRepoModels) {
            add(result);
        }
    }

    public void addAllLocal(List<GithubRepoModel> moveResults) {
        isLocal = true;
        clear();

        for (GithubRepoModel result : moveResults) {
            add(result);
        }
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new GithubRepoModel());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = githubRepoModels.size() - 1;
        GithubRepoModel result = getItem(position);

        if (result != null) {
            githubRepoModels.remove(position);
            notifyItemRemoved(position);
        }
    }


    private void remove(GithubRepoModel r) {
        int position = githubRepoModels.indexOf(r);
        if (position > -1) {
            githubRepoModels.remove(position);
            notifyItemRemoved(position);
        }
    }

    private void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    private GithubRepoModel getItem(int position) {
        return githubRepoModels.get(position);
    }


    class DataViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_view_data)
        TextView textView;
        @BindView(R.id.text_id)
        TextView textId;

        DataViewHolder(View itemView) {
            super(itemView);
            // unbind required only for fragments
            ButterKnife.bind(this, itemView);
        }
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {
        LoadingViewHolder(View itemView) {
            super(itemView);
        }
    }
}
