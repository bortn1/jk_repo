package com.ab.github_api_pq.ui.main;


import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.ab.github_api_pq.model.GithubRepoModel;
import com.ab.github_api_pq.network.retrofit.repo.GithubRepo;
import com.ab.github_api_pq.network.retrofit.repo.OnNetworkResponse;

import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Inject;

public class MainPresenter implements MainContract.Presenter {
    private final GithubRepo githubRepo;
    @Nullable
    private MainContract.View presenterView;
    private int page = 1;
    private int startPage = 1;
    private boolean isLocal = false;

    @Inject
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
            if (currentPage > startPage) {
                presenterView.startBottomLoading(true);
            } else {
                page = startPage;
                presenterView.showLoadingBar(true);
            }
        }

        githubRepo.getGithubData(currentPage, new OnNetworkResponse<List<GithubRepoModel>>() {
            @Override
            public void success(@NonNull List<GithubRepoModel> githubRepoModels) {
                isLocal = false;

                if (currentPage > startPage) {
                    presenterView.startBottomLoading(false);
                } else {
                    presenterView.showLoadingBar(false);
                }


                if (githubRepoModels.size() > 0) {
                    presenterView.lastPage(false);
                    presenterView.showData(githubRepoModels, currentPage);
                } else {
                    presenterView.lastPage(true);
                }
            }

            @Override
            public void error(@Nullable Throwable throwable) {
                presenterView.showLoadingBar(false);
                presenterView.handleErrorBehaviour(throwable);
            }
        });
    }

    @Override
    public void getDataOnBottomList(boolean bottom) {
        if (bottom && !isLocal) {
            page++;
            getData(page);
        }
    }

    @Override
    public void isLocal(boolean isLocal) {
        this.isLocal = isLocal;
    }

    @Override
    public void setPage(int page) {
        this.page = page;
    }


    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    @Nullable
    MainContract.View getPresenterView() {
        return presenterView;
    }
}
