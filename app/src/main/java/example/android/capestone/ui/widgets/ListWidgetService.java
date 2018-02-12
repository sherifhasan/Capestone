package example.android.capestone.ui.widgets;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import example.android.capestone.R;
import example.android.capestone.models.Article;
import example.android.capestone.utility.Utility;

/**
 * Created by sheri on 2/12/2018.
 */
public class ListWidgetService extends RemoteViewsService {
    private List<Article> articles = new ArrayList();
    private ListAdapter articlesAdapter;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        getArticles();
        articlesAdapter = new ListAdapter(getApplicationContext(), articles);
        return articlesAdapter;

    }

    private void getArticles() {
        DatabaseReference scoresRef = FirebaseDatabase.getInstance().getReference(Utility.DATABASE_NAME);
        scoresRef.orderByValue().limitToLast(4).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    Article article = singleSnapshot.getValue(Article.class);
                    articles.add(article);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public class ListAdapter implements RemoteViewsService.RemoteViewsFactory {

        private List<Article> articles = new ArrayList();
        private final Context context;
        private RemoteViews views;

        public ListAdapter(Context context, List<Article> articles) {
            this.context = context;
            this.articles = articles;
            views = new RemoteViews(context.getPackageName(), R.layout.list_item_widget);
        }

        @Override
        public void onCreate() {
        }


        @Override
        public void onDataSetChanged() {
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {

            return articles == null ? 0 : articles.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            Article article = articles.get(position);
            if (article.getTitle() != null) {
                views.setTextViewText(R.id.widget_news_title, article.getTitle());
            }
            if (article.getDescription() != null) {
                views.setTextViewText(R.id.widget_published, article.getDescription());
            }
            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 0;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}