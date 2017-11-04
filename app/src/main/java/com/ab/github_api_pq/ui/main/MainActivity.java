package com.ab.github_api_pq.ui.main;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.design.widget.Snackbar;
import android.support.test.espresso.IdlingResource;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.ab.github_api_pq.R;
import com.ab.github_api_pq.model.GithubRepoModel;
import com.ab.github_api_pq.ui.main.adapter.RecyclerPaginationAdapter;
import com.ab.github_api_pq.ui.main.listener.PaginationScrollListener;
import com.ab.github_api_pq.utils.EspressoIdlingResource;

import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;
import io.realm.Realm;
import io.realm.RealmQuery;

public class MainActivity extends DaggerAppCompatActivity implements MainContract.View {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @Inject
    MainPresenter mainPresenter;

    private int startPage = 1;
    private boolean isLoadingShow = false;
    private boolean isLastPageShow;
    private boolean isActivityDestroyed = false;
    private RecyclerPaginationAdapter recyclerPaginationAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // unbind required only for fragments
        ButterKnife.bind(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void onListBottom() {
                //list behaviour thread separation and perfomance improvements
                recyclerView.post(() -> mainPresenter.getDataOnBottomList(!isLastPageShow));
            }

            @Override
            public boolean isLastPage() {
                return isLastPageShow;
            }

            @Override
            public boolean isLoading() {
                return isLoadingShow;
            }
        });
        recyclerPaginationAdapter = new RecyclerPaginationAdapter();
        recyclerView.setAdapter(recyclerPaginationAdapter);
        swipeRefreshLayout.setOnRefreshListener(() -> mainPresenter.getData(startPage));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mainPresenter.takeView(this);
        mainPresenter.getData(startPage);
    }

    @Override
    protected void onDestroy() {
        isActivityDestroyed = true;
        mainPresenter.deleteView();
        super.onDestroy();
    }

    @Override
    public boolean isActive() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return isDestroyed();
        } else return isActivityDestroyed;
    }

    @Override
    public void showLoadingBar(boolean show) {
        swipeRefreshLayout.setRefreshing(show);
    }

    @Override
    public void showData(List<GithubRepoModel> githubRepoModels, int currentPage) {
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
        if (currentPage > startPage) {
            recyclerPaginationAdapter.addAll(githubRepoModels);
        } else {
            recyclerPaginationAdapter.addAllFirstTime(githubRepoModels);
        }
    }

    @Override
    public void handleErrorBehaviour(Throwable throwable) {
        if (getLocalGithubRepoModels() != null &&
                getLocalGithubRepoModels().size() > 0) {
            mainPresenter.isLocal(true);
            showLocalData(getLocalGithubRepoModels());
        } else {
            mainPresenter.setPage(0);
            String errorMessage = getString(R.string.error_text) + throwable.getLocalizedMessage();
            Snackbar.make(recyclerView, errorMessage,
                    Snackbar.LENGTH_INDEFINITE).setAction(R.string.retry_snackbar, view ->
                    mainPresenter.getData(startPage)).setActionTextColor(Color.YELLOW).show();
        }

    }

    private void showLocalData(List<GithubRepoModel> localGithubRepoModels) {
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
        Snackbar snackbar = Snackbar.make(recyclerView, R.string.local_data_show_text, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction(R.string.snackbar_dismiss, view -> snackbar.dismiss()).setActionTextColor(Color.YELLOW);
        snackbar.show();
        recyclerPaginationAdapter.addAllLocal(localGithubRepoModels);
    }

    @Override
    public void startBottomLoading(boolean show) {
        this.isLoadingShow = show;

        if (isLoadingShow) {
            recyclerPaginationAdapter.addLoadingFooter();
        } else {
            recyclerPaginationAdapter.removeLoadingFooter();
        }
    }

    @Override
    public void lastPage(boolean show) {
        isLastPageShow = show;
    }

    @Nullable
    private List<GithubRepoModel> getLocalGithubRepoModels() {
        if (Realm.getDefaultInstance() != null) {
            return RealmQuery.createQuery(Realm.getDefaultInstance(),
                    GithubRepoModel.class).findAll();
        } else return null;
    }

    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    IdlingResource getCountingIdlingResource() {
        return EspressoIdlingResource.getIdlingResource();
    }
}
