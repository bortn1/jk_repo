package com.ab.github_api_pq.network.retrofit.repo;

import android.content.Context;
import android.support.annotation.VisibleForTesting;

import com.ab.github_api_pq.model.GithubRepoModel;
import com.ab.github_api_pq.network.retrofit.RetrofitFactory;
import com.ab.github_api_pq.network.retrofit.api.GithubRepoApi;
import com.ab.github_api_pq.utils.EspressoIdlingResource;

import java.util.List;

import javax.inject.Singleton;

import io.realm.Realm;
import retrofit2.Retrofit;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@Singleton
public class GithubRepo {
    private final Context context;
    private Realm realm;
    private Retrofit retrofit;

    public GithubRepo(Context context) {
        this.context = context;
    }

    public Retrofit getRetrofit() {
        if (retrofit == null) {
            return RetrofitFactory.newRetrofit(context);
        } else return retrofit;
    }

    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    public void setRetrofit(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    public void getGithubData(int page, OnNetworkResponse<List<GithubRepoModel>> onNetworkResponse) {
        realm = Realm.getDefaultInstance();
        EspressoIdlingResource.increment(); // App is busy until further notice

        getObservable(page)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnTerminate(() -> {
                    if (!EspressoIdlingResource.getIdlingResource().isIdleNow()) {
                        EspressoIdlingResource.decrement(); // Set app as idle.
                    }
                }).subscribe(githubRepoModels -> {
                    realm.executeTransaction(realmParam -> {
                        for (GithubRepoModel githubRepoModel : githubRepoModels) {
                            realmParam.copyToRealmOrUpdate(githubRepoModel);
                        }
                    });
                    realm.close();
                    onNetworkResponse.success(githubRepoModels);

                },
                onNetworkResponse::error);
    }

    Observable<List<GithubRepoModel>> getObservable(int page) {
        return getRetrofit().create(GithubRepoApi.class)
                .getRepoModel(page);
    }
}
