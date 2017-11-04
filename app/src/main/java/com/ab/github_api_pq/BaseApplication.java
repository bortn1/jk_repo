package com.ab.github_api_pq;


import com.ab.github_api_pq.di.AppComponent;
import com.ab.github_api_pq.di.DaggerAppComponent;
import com.ab.github_api_pq.network.retrofit.repo.GithubRepo;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;


public class BaseApplication extends DaggerApplication {

    //doing it once in application file to have and share instance to our UI

    @Inject
    GithubRepo githubRepo;

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        AppComponent appComponent = DaggerAppComponent.builder().application(this).build();
        appComponent.inject(this);
        return appComponent;
    }
}
