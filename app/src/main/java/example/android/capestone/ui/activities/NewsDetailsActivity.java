package example.android.capestone.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import example.android.capestone.R;
import example.android.capestone.models.Article;
import example.android.capestone.ui.fragments.NewsDetailsActivityFragment;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class NewsDetailsActivity extends AppCompatActivity {
    private static final String EXTRA_ARTICLE = "article";
    boolean twoPanes;
    Article newsletter;
    @BindView(R.id.details_toolbar)
    Toolbar toolbar;
    @BindView(R.id.news_headline)
    TextView headline;

    public static void startActivity(Context context, Article article) {
        if (context == null) {
            return;
        }
        Intent i = new Intent(context, NewsDetailsActivity.class).putExtra(EXTRA_ARTICLE, article);
        i.addFlags(FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        if (getIntent() != null && getIntent().hasExtra(EXTRA_ARTICLE) && getIntent().getExtras().getParcelable(EXTRA_ARTICLE) != null) {
            newsletter = getIntent().getExtras().getParcelable(EXTRA_ARTICLE);
            assert newsletter != null;
            headline.setText(newsletter.getTitle());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            NewsDetailsActivityFragment fragment = NewsDetailsActivityFragment.newInstance(newsletter);
            getSupportFragmentManager().beginTransaction().replace(R.id.details_fragment, fragment).commit();
        }
    }
}