package example.android.capestone.presenter;

import android.content.Context;
import android.util.Log;

import java.util.List;

import example.android.capestone.models.Article;
import example.android.capestone.models.News;
import example.android.capestone.network.retrofit.ApiClient;
import example.android.capestone.network.retrofit.ApiInterface;
import example.android.capestone.ui.fragments.MainActivityFragment;
import example.android.capestone.utility.Utility;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static example.android.capestone.utility.Utility.isNetworkConnected;

;

/**
 * Created by sheri on 11/14/2017.
 */

public class NewsPresenter {


    private List<Article> articleList;
    private Throwable mError;
    private MainActivityFragment mView;
    private Context mContext;

    public NewsPresenter(Context context) {
        this.mContext = context;
        getNewsFromApi(context);
    }

    private void getNewsFromApi(Context context) {
        if (isNetworkConnected(context)) {
            ApiInterface apiService = ApiClient.getClient();
            Call<News> call = apiService.getTopHeadlines(Utility.COUNTRY, Utility.API_KEY);
            call.enqueue(new Callback<News>() {
                @Override
                public void onResponse(Call<News> call, Response<News> response) {

                    if (response.body() != null) {
                        Log.d("Articles size: ", (response.body().getArticles().size() + toString()));
                        articleList = response.body().getArticles();
                        publish();
                    } else {
                        Log.d("Get Articles : ", "Response is null");
                    }
                }

                @Override
                public void onFailure(Call<News> call, Throwable t) {
                    Log.d("onFailure :", t.toString());
                    mError = t;
                    publish();
                }
            });
        }
    }

    public void onTakeView(MainActivityFragment view) {
        this.mView = view;
        publish();
    }

    private void publish() {
        if (mView != null) {
            if (articleList != null)
                mView.onArticleNext(articleList);
            else if (mError != null)
                mView.onArticleError(mError);
        }
    }
}