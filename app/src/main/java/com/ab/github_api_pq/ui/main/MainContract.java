package com.ab.github_api_pq.ui.main;


import com.ab.github_api_pq.model.GithubRepoModel;
import com.ab.github_api_pq.ui.BasePresenter;

import java.util.List;

public interface MainContract {
    interface View {
        boolean isActive();

        void showLoadingBar(boolean show);

        void showData(List<GithubRepoModel> data, int page);
    }

    interface Presenter extends BasePresenter<View> {

        void getData(int page);
    }
}
