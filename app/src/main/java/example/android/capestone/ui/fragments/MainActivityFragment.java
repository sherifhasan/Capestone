package example.android.capestone.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import example.android.capestone.R;
import example.android.capestone.models.Article;
import example.android.capestone.presenter.NewsPresenter;
import example.android.capestone.ui.activities.NewsDetailsActivity;
import example.android.capestone.ui.adapters.NewsAdapter;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    @BindView(R.id.adView)
    AdView mAdView;
    NewsPresenter mPresenter;
    List<Article> mArticles;
    private static final String NEWS_LIST = "news_list";
    NewsAdapter mNewsAdapter;
    GridLayoutManager mLayoutManager;
    @BindView(R.id.news_list)
    RecyclerView mRecyclerView;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, rootView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);
        mNewsAdapter = new NewsAdapter(getActivity());
        int columnCount = getResources().getInteger(R.integer.spanCount);
        mLayoutManager = new GridLayoutManager(getActivity(), columnCount);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mNewsAdapter);
        mNewsAdapter.setListener(new NewsAdapter.Listener() {
            @Override
            public void onClick(Article article) {
                NewsDetailsActivity.startActivity(getActivity(), article);
            }
        });

        if (savedInstanceState != null) {
            mArticles = savedInstanceState.getParcelableArrayList(NEWS_LIST);
            mNewsAdapter.updateAdapter(mArticles);

        }

        return rootView;
    }

    public void onArticleNext(List<Article> articles) {
        mArticles = articles;
        mNewsAdapter.updateAdapter(mArticles);
    }

    public void onArticleError(Throwable throwable) {
        Snackbar.make(getView(), throwable.getMessage(), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter = new NewsPresenter(getActivity());
        mPresenter.onTakeView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onTakeView(null);
        if (!getActivity().isChangingConfigurations())
            mPresenter = null;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(NEWS_LIST, (ArrayList<Article>) mArticles);
    }
}
