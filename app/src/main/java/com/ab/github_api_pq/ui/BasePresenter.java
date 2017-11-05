package com.ab.github_api_pq.ui;

public interface BasePresenter<T> {
    void takeView(T view);

    void deleteView();
}
