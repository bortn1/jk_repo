package com.ab.github_api_pq.utils;


import com.ab.github_api_pq.model.GithubRepoModel;

public class TestUtils {
    public static final int FIRST_PAGE = 1;
    public static final int LAST_PAGE = 2;

    private static GithubRepoModel githubRepoModel = new GithubRepoModel(1, "name");

    public static GithubRepoModel getGithubRepoModel() {
        return githubRepoModel;
    }

    public static void cleanGithubRepoModel() {
        githubRepoModel = null;
    }
}
