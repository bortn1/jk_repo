package com.ab.github_api_pq.di.modules;

import android.app.Application;

import com.ab.github_api_pq.network.retrofit.repo.GithubRepo;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class NetworkModule {

    @Singleton
    @Provides
    GithubRepo provideGithubRepo(Application context) {
        return new GithubRepo(context);
    }
}
