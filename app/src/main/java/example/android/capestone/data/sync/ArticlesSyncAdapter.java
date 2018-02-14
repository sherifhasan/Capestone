package example.android.capestone.data.sync;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentValues;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import example.android.capestone.data.database.ArticlesProvider;
import example.android.capestone.models.Article;
import example.android.capestone.models.News;
import example.android.capestone.network.retrofit.ApiClient;
import example.android.capestone.network.retrofit.ApiInterface;
import example.android.capestone.utility.Utility;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static example.android.capestone.data.database.ArticlesContract.ArticlesTableEntry.COLUMN_AUTHOR;
import static example.android.capestone.data.database.ArticlesContract.ArticlesTableEntry.COLUMN_DESCRIPTION;
import static example.android.capestone.data.database.ArticlesContract.ArticlesTableEntry.COLUMN_TITLE;
import static example.android.capestone.data.database.ArticlesContract.ArticlesTableEntry.COLUMN_URL;

/**
 * Created by sheri on 2/14/2018.
 */

public class ArticlesSyncAdapter extends AbstractThreadedSyncAdapter {

    public ArticlesSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority,
                              ContentProviderClient contentProviderClient, SyncResult syncResult) {
        try {
            insertArticles(contentProviderClient);
        } catch (RemoteException | IOException e) {
            syncResult.hasHardError();
        }
    }

    private void insertArticles(final ContentProviderClient contentProviderClient) throws RemoteException, IOException {
        contentProviderClient.delete(ArticlesProvider.Articles.articleUri, null, null);
        ApiInterface apiService = ApiClient.getClient();
        Call<News> call = apiService.getTopHeadlines(Utility.COUNTRY, Utility.API_KEY);
        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {

                if (response.body() != null) {
                    Log.d("Articles size: ", (response.body().getArticles().size() + toString()));
                    List<Article> articleList = response.body().getArticles();
                    ContentValues[] cvs = new ContentValues[articleList.size()];
                    for (int i = 0; i < articleList.size(); i++) {
                        Article article = articleList.get(i);
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
                    int numOfRows = 0;
                    try {
                        numOfRows = contentProviderClient.bulkInsert(ArticlesProvider.Articles.articleUri, cvs);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    Log.d("num of rows : ", String.valueOf(numOfRows));
                } else {
                    Log.d("Get Articles : ", "Response is null");
                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                Log.d("onFailure :", t.toString());
            }
        });
    }
}