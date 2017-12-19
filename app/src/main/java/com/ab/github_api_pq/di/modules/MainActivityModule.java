package com.ab.github_api_pq.di.modules;


import com.ab.github_api_pq.ui.main.MainContract;
import com.ab.github_api_pq.ui.main.MainPresenter;

import dagger.Binds;
import dagger.Module;
// specific activity module
@Module
public abstract class MainActivityModule {
    @Binds
    abstract MainContract.Presenter dataPresenter(MainPresenter presenter);
}
