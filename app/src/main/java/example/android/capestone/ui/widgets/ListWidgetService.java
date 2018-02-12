package example.android.capestone.ui.widgets;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import example.android.capestone.R;
import example.android.capestone.data.ArticlesProvider;

import static example.android.capestone.data.ArticlesContract.ArticlesTableEntry.COLUMN_DESCRIPTION;
import static example.android.capestone.data.ArticlesContract.ArticlesTableEntry.COLUMN_TITLE;

/**
 * Created by sheri on 2/12/2018.
 */
public class ListWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListAdapter(getApplicationContext());
    }


    public class ListAdapter implements RemoteViewsService.RemoteViewsFactory {

        private final Context mContext;

        private Cursor mCursor;

        public ListAdapter(Context context) {
            this.mContext = context;
            Uri queryUri = ArticlesProvider.Articles.articleUri;
            mCursor = mContext.getContentResolver().query(queryUri, null, null, null, null);
        }

        @Override
        public void onCreate() {
        }


        @Override
        public void onDataSetChanged() {
            Uri queryUri = ArticlesProvider.Articles.articleUri;
            if (mCursor != null) {
                mCursor.close();
            }
            mCursor = mContext.getContentResolver().query(queryUri, null, null, null, null);
        }

        @Override
        public void onDestroy() {
            mCursor.close();
        }

        @Override
        public int getCount() {
            return mCursor == null ? 0 : mCursor.getCount();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.list_item_widget);

            if (mCursor.moveToPosition(position)) {
                String articleTitle = mCursor.getString(mCursor.getColumnIndex(COLUMN_TITLE));
                String articleDescription = mCursor.getString(mCursor.getColumnIndex(COLUMN_DESCRIPTION));
                if (articleTitle != null) {
                    views.setTextViewText(R.id.widget_news_title, articleTitle);
                }
                if (articleDescription != null) {
                    views.setTextViewText(R.id.widget_desc, articleDescription);
                }
            }

            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }
}