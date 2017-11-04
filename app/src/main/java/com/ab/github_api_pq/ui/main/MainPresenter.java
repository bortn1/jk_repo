package com.ab.github_api_pq.ui.main;


import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.ab.github_api_pq.model.GithubRepoModel;
import com.ab.github_api_pq.network.retrofit.repo.GithubRepo;
import com.ab.github_api_pq.network.retrofit.repo.OnNetworkResponse;

import java.util.List;

import javax.annotation.Nullable;

public class MainPresenter implements MainContract.Presenter {
    private final GithubRepo githubRepo;
    @Nullable
    private MainContract.View presenterView;
    private int page = 1;
    private int startPage = 1;

    public MainPresenter(GithubRepo githubRepo) {
        this.githubRepo = githubRepo;
    }

    @Override
    public void takeView(MainContract.View view) {
        presenterView = view;
    }

    @Override
    public void deleteView() {
        presenterView = null;
    }

    @Override
    public void getData(int currentPage) {
        if (presenterView != null) {
            presenterView.showLoadingBar(true);
        }
        githubRepo.getGithubData(currentPage, new OnNetworkResponse<List<GithubRepoModel>>() {
            @Override
            public void success(@NonNull List<GithubRepoModel> githubRepoModels) {
                presenterView.showLoadingBar(false);
                if (githubRepoModels.size() > 0) {
                    presenterView.showData(githubRepoModels, currentPage);
                }
            }

            @Override
            public void error(@Nullable Throwable throwable) {
                presenterView.showLoadingBar(false);
                presenterView.showError(throwable);
            }
        });
    }


    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    @Nullable
    MainContract.View getPresenterView() {
        return presenterView;
    }
}
