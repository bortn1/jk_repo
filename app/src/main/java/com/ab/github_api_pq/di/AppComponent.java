package com.ab.github_api_pq.di;

import android.app.Application;

import com.ab.github_api_pq.BaseApplication;
import com.ab.github_api_pq.di.modules.ActivityModule;
import com.ab.github_api_pq.di.modules.ApplicationModule;
import com.ab.github_api_pq.di.modules.NetworkModule;
import com.ab.github_api_pq.network.retrofit.repo.GithubRepo;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {NetworkModule.class,
        ApplicationModule.class,
        ActivityModule.class,
        AndroidSupportInjectionModule.class})

public interface AppComponent extends AndroidInjector<DaggerApplication> {

    void inject(BaseApplication application);

    GithubRepo getGithubRepo();

    @Override
    void inject(DaggerApplication instance);

    // Gives us syntactic sugar. we can then do DaggerAppComponent.builder().application(this).build().inject(this);
    // never having to instantiate any modules or say which module we are passing the application to.
    // Application will just be provided into our app graph now.
    @Component.Builder
    interface Builder {

        @BindsInstance
        AppComponent.Builder application(Application application);

        AppComponent build();
    }
}
