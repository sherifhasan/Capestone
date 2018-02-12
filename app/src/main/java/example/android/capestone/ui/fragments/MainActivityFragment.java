package example.android.capestone.ui.fragments;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import example.android.capestone.R;
import example.android.capestone.data.ArticlesProvider;
import example.android.capestone.models.Article;
import example.android.capestone.presenter.NewsPresenter;
import example.android.capestone.ui.activities.NewsDetailsActivity;
import example.android.capestone.ui.adapters.NewsAdapter;
import example.android.capestone.utility.Utility;

import static example.android.capestone.data.ArticlesContract.ArticlesTableEntry.COLUMN_AUTHOR;
import static example.android.capestone.data.ArticlesContract.ArticlesTableEntry.COLUMN_DESCRIPTION;
import static example.android.capestone.data.ArticlesContract.ArticlesTableEntry.COLUMN_TITLE;
import static example.android.capestone.data.ArticlesContract.ArticlesTableEntry.COLUMN_URL;

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

    private DatabaseReference mDatabase;
    private DatabaseReference articleCloudEndPoint;

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

        mDatabase = FirebaseDatabase.getInstance().getReference();
        articleCloudEndPoint = mDatabase.child(Utility.DATABASE_NAME);

        return rootView;
    }

    private void saveArticlesLocally() {
        getActivity().getContentResolver().delete(ArticlesProvider.Articles.articleUri, null, null);
        ContentValues[] cvs = new ContentValues[mArticles.size()];
        for (int i = 0; i < mArticles.size(); i++) {
            Article article = mArticles.get(i);
            cvs[i] = new ContentValues();

            if (article.getTitle() != null) {
                cvs[i].put(COLUMN_TITLE, article.getTitle());
            }
            if (article.getDescription() != null) {
                cvs[i].put(COLUMN_DESCRIPTION, article.getDescription());
            }
            if (article.getUrl() != null) {
                cvs[i].put(COLUMN_URL, article.getUrl());
            }
            if (article.getAuthor() != null) {
                cvs[i].put(COLUMN_AUTHOR, article.getAuthor());
            } else if (article.getSource() != null && article.getSource().getName() != null) {
                cvs[i].put(COLUMN_AUTHOR, article.getSource().getName());
            }
        }
        int numOfRows = getActivity().getContentResolver().bulkInsert(ArticlesProvider.Articles.articleUri, cvs);
        Log.d("num of rows : ", String.valueOf(numOfRows));

    }

    public void onArticleNext(List<Article> articles) {
        mArticles = articles;
        mNewsAdapter.updateAdapter(mArticles);
        for (Article article : mArticles) {
            String key = articleCloudEndPoint.push().getKey();
            article.setId(key);
            articleCloudEndPoint.child(key).setValue(article);
        }
        saveArticlesLocally();
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
