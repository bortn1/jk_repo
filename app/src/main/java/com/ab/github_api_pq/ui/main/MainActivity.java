package com.ab.github_api_pq.ui.main;

import com.ab.github_api_pq.model.GithubRepoModel;

import java.util.List;

import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity implements MainContract.View {
    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void showLoadingBar(boolean show) {

    }

    @Override
    public void showData(List<GithubRepoModel> data, int page) {

    }

    @Override
    public void showError(Throwable throwable) {

    }

    @Override
    public void startBottomLoading(boolean show) {

    }

    @Override
    public void lastPage(boolean show) {

    }
}
