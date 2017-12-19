package com.ab.github_api_pq.di.modules;


import com.ab.github_api_pq.ui.main.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

// should contain all activities of application
@Module
public abstract class ActivityModule {
    @ContributesAndroidInjector(modules = MainActivityModule.class)
    abstract MainActivity mainActivity();
}
