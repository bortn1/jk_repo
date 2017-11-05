package com.ab.github_api_pq.model;

import android.support.annotation.VisibleForTesting;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class GithubRepoModel extends RealmObject {
    @PrimaryKey
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("html_url")
    @Expose
    private String htmlUrl;


    public GithubRepoModel() {
    }

    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    public GithubRepoModel(Integer id, String name) {
        this.id = id;
        this.fullName = name;
    }

    public Integer getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

}
