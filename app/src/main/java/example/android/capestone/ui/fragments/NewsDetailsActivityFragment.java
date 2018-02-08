package example.android.capestone.ui.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import example.android.capestone.R;
import example.android.capestone.models.Article;

/**
 * A placeholder fragment containing a simple view.
 */
public class NewsDetailsActivityFragment extends Fragment {
    private static final String EXTRA_ARTICLE = "article";
    private static final String SAVED_ARTICLE = "saved";
    Article article;
    @BindView(R.id.article_author)
    TextView author;
    @BindView(R.id.article_title_details)
    TextView title;
    @BindView(R.id.news_description)
    TextView description;
    @BindView(R.id.news_published_at_details)
    TextView publishedAt;
    @BindView(R.id.news_url)
    TextView newsUrl;
    @BindView(R.id.news_image_details)
    ImageView newsLogo;


    public static NewsDetailsActivityFragment newInstance(Article article) {
        Bundle args = new Bundle();
        args.putParcelable(EXTRA_ARTICLE, article);
        NewsDetailsActivityFragment fragment = new NewsDetailsActivityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public NewsDetailsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news_details, container, false);
        ButterKnife.bind(this, rootView);
        if (savedInstanceState == null) {
            if (getArguments() != null && getArguments().containsKey(EXTRA_ARTICLE) && getArguments().getParcelable(EXTRA_ARTICLE) != null) {
                article = getArguments().getParcelable(EXTRA_ARTICLE);
                assert article != null;
                initViews(article);
            }

        } else {
            article = savedInstanceState.getParcelable(SAVED_ARTICLE);
            initViews(article);
        }
        return rootView;
    }

    private void initViews(final Article article) {
        if (article.getAuthor() != null && !TextUtils.isEmpty(article.getAuthor())) {
            String authorString = getString(R.string.Author) + article.getAuthor();
            author.setText(authorString);
        } else if (article.getSource() != null && !TextUtils.isEmpty(article.getSource().getName())) {
            String sourceString = getString(R.string.source) + article.getSource().getName();
            author.setText(sourceString);
        }
        if (article.getTitle() != null && !TextUtils.isEmpty(article.getTitle())) {
            String titleString = getString(R.string.Title) + article.getTitle();
            title.setText(titleString);
        }
        if (article.getDescription() != null && !TextUtils.isEmpty(article.getDescription())) {
            String descString = getString(R.string.desc) + article.getDescription();
            description.setText(descString);
        }
        if (article.getUrl() != null && !TextUtils.isEmpty(article.getUrl())) {
            String newsLink = "<font color='#ff4081'>" + article.getUrl() + "</font>";
            String newsUrlString = getString(R.string.news_url) + newsLink;
            newsUrl.setText(Html.fromHtml(newsUrlString));
            newsUrl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openUrl(article.getUrl());
                }
            });
        }
        if (article.getPublishedAt() != null && !TextUtils.isEmpty(article.getPublishedAt().toString())) {
            String publishedString = getString(R.string.published) + article.getPublishedAt().toString();
            publishedAt.setText(publishedString);
        }
        if (article.getUrlToImage() != null && !TextUtils.isEmpty(article.getUrlToImage())) {
            Picasso.with(getActivity()).load(article.getUrlToImage()).into(newsLogo);
        }
    }

    private void openUrl(String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(SAVED_ARTICLE, article);
    }
}