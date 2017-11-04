package com.ab.github_api_pq.network.retrofit.repo;

import android.content.Context;

import com.ab.github_api_pq.model.GithubRepoModel;
import com.ab.github_api_pq.network.retrofit.RetrofitFactory;
import com.ab.github_api_pq.network.retrofit.api.GithubRepoApi;

import java.util.List;

import javax.inject.Singleton;

import io.realm.Realm;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@Singleton
public class GithubRepo {
    private final Context context;
    private Realm realm;

    public GithubRepo(Context context) {
        this.context = context;
    }

    public void getGithubData(int page, OnNetworkResponse<List<GithubRepoModel>> onNetworkResponse) {
        realm = Realm.getDefaultInstance();
        RetrofitFactory.newRetrofit(context)
                .create(GithubRepoApi.class)
                .getRepoModel(page)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(githubRepoModels -> {
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
}
