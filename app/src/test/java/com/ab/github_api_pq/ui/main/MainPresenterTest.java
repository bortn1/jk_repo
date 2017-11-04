package com.ab.github_api_pq.ui.main;

import com.ab.github_api_pq.model.GithubRepoModel;
import com.ab.github_api_pq.network.retrofit.repo.GithubRepo;
import com.ab.github_api_pq.network.retrofit.repo.OnNetworkResponse;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class MainPresenterTest {
    @Mock
    GithubRepo githubRepo;

    @Mock
    MainContract.View view;

    @Captor
    private ArgumentCaptor<OnNetworkResponse> mLoadCallbackCaptor;
    private MainPresenter mainPresenter;
    private List<GithubRepoModel> data;


    @Before
    public void setupPresenter() {
        MockitoAnnotations.initMocks(this);
        mainPresenter = new MainPresenter(githubRepo);
        mainPresenter.takeView(view);
        data = new ArrayList<>();

        when(view.isActive()).thenReturn(true);
    }

    @Test
    public void itShouldVerifyThatViewIsAttached() {
        Assert.assertNotEquals(mainPresenter.getPresenterView(), null);
    }

    @Test
    public void itShouldVerifyThatViewIsDeleted() {
        mainPresenter.deleteView();
        Assert.assertEquals(mainPresenter.getPresenterView(), null);
    }

    @Test
    public void itShouldVerifyThatDataForTheFirstPageIsShown() {
        mainPresenter.getData(TestUtils.FIRST_PAGE);

        verify(view, atLeastOnce()).showLoadingBar(true);

        verify(githubRepo, atLeastOnce()).getGithubData(eq(TestUtils.FIRST_PAGE), mLoadCallbackCaptor.capture());

        fillData();

        mLoadCallbackCaptor.getValue().success(data);

        verify(view, atLeastOnce()).showLoadingBar(false);

        verify(view, atLeastOnce()).showData(data, TestUtils.FIRST_PAGE);
    }

    @Test
    public void itShouldVerifyThatDataForTheFirstPageIsNotShownButErrorIsShown() {
        mainPresenter.getData(TestUtils.FIRST_PAGE);

        verify(view, atLeastOnce()).showLoadingBar(true);

        verify(githubRepo, atLeastOnce()).getGithubData(eq(TestUtils.FIRST_PAGE), mLoadCallbackCaptor.capture());

        fillData();

        Throwable error = new Throwable();

        mLoadCallbackCaptor.getValue().error(error);
        verify(view, atLeastOnce()).showLoadingBar(false);
        verify(view, never()).showData(data, TestUtils.FIRST_PAGE);
        verify(view, atLeastOnce()).showError(error);
    }

    private void fillData() {
        TestUtils.fillGithubRepoModel();
        data.add(TestUtils.getGithubRepoModel());
        data.add(TestUtils.getGithubRepoModel());
        data.add(TestUtils.getGithubRepoModel());
    }

}