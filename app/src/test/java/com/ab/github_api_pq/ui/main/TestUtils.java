package com.ab.github_api_pq.ui.main;


import com.ab.github_api_pq.model.GithubRepoModel;

public class TestUtils {
    public static final int FIRST_PAGE = 1;

    private static GithubRepoModel githubRepoModel = new GithubRepoModel();

    public static GithubRepoModel getGithubRepoModel() {
        return githubRepoModel;
    }

    public static void fillGithubRepoModel() {
        githubRepoModel.setId(1);
        githubRepoModel.setName("name");
        githubRepoModel.setFullName("full_name");
        githubRepoModel.setHtmlUrl("url");
    }


    public static void cleanGithubRepoModel() {
        githubRepoModel = null;
    }
}
