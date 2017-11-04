package com.ab.github_api_pq.ui.main;


import com.ab.github_api_pq.model.GithubRepoModel;

public class TestUtils {
    static final int FIRST_PAGE = 1;
    static final int LAST_PAGE = 2;

    private static GithubRepoModel githubRepoModel = new GithubRepoModel();

    static GithubRepoModel getGithubRepoModel() {
        return githubRepoModel;
    }

    static void fillGithubRepoModel() {
        githubRepoModel.setId(1);
        githubRepoModel.setName("name");
        githubRepoModel.setFullName("full_name");
        githubRepoModel.setHtmlUrl("url");
    }


    public static void cleanGithubRepoModel() {
        githubRepoModel = null;
    }
}
