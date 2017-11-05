package com.ab.github_api_pq.network.retrofit.repo;

import android.content.Context;

import com.ab.github_api_pq.model.GithubRepoModel;
import com.ab.github_api_pq.utils.TestUtils;
import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.observers.TestSubscriber;

import static com.ab.github_api_pq.utils.TestUtils.FIRST_PAGE;

/**
 * Created by andrewbortnichuk on 04/11/2017.
 */
public class GithubRepoTest {
    @Mock
    Context context;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void itShouldNotifyForSuccessAndNoErrorsForMockServer() {

        List<GithubRepoModel> result = new ArrayList();

        result.add(TestUtils.getGithubRepoModel());

        MockWebServer mockService = new MockWebServer();
        mockService.enqueue(new MockResponse().setBody(new Gson().toJson(result)));

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(mockService.url("dfdf/"))
                .build();

        TestSubscriber<List<GithubRepoModel>> subscriber = new TestSubscriber<>();
        GithubRepo githubRepo = new GithubRepo(context);
        githubRepo.setRetrofit(retrofit);
        githubRepo.getObservable(FIRST_PAGE).subscribe(subscriber);

        subscriber.assertNoErrors();
        subscriber.assertCompleted();
    }

    @Test
    public void itShouldNotifyForSuccessAndNoErrorsForServiceServer() {

        List<GithubRepoModel> result = new ArrayList();
        result.add(TestUtils.getGithubRepoModel());
        result.add(TestUtils.getGithubRepoModel());

        MockWebServer mockWebServer = new MockWebServer();
        mockWebServer.enqueue(new MockResponse().setBody(new Gson().toJson(result)));

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(mockWebServer.url("https://api.github.com/"))
                .build();

        TestSubscriber<List<GithubRepoModel>> subscriber = new TestSubscriber<>();
        GithubRepo githubRepo = new GithubRepo(context);
        githubRepo.setRetrofit(retrofit);
        githubRepo.getObservable(FIRST_PAGE).subscribe(subscriber);

        subscriber.assertNoErrors();
        subscriber.assertCompleted();
    }

}
