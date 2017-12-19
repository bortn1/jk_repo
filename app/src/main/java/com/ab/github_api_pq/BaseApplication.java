package com.ab.github_api_pq;


import com.ab.github_api_pq.di.AppComponent;
import com.ab.github_api_pq.di.DaggerAppComponent;
import com.ab.github_api_pq.network.retrofit.repo.GithubRepo;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import io.realm.Realm;
import io.realm.RealmConfiguration;


public class BaseApplication extends DaggerApplication {

    //doing it once in application file to have and share instance to our UI

    @Inject
    GithubRepo githubRepo;

    @Override
    public void onCreate() {
        super.onCreate();
        RealmConfiguration config = new RealmConfiguration.Builder().name("mvp.realm").build();
        Realm.setDefaultConfiguration(config);
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        Realm.init(this);
        AppComponent appComponent = DaggerAppComponent.builder().application(this).build();
        appComponent.inject(this);
        return appComponent;
    }
}
