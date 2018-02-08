package example.android.capestone.ui.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import example.android.capestone.R;
import example.android.capestone.models.Article;

/**
 * Created by sheri on 11/10/2017.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {
    Listener mListener;
    private Context context;
    private List<Article> newsList;

    public NewsAdapter(Context context) {
        this.context = context;
    }

    public void updateAdapter(List<Article> newsList) {
        this.newsList = newsList;
        notifyDataSetChanged();
    }

    public void setListener(Listener mListener) {
        this.mListener = mListener;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.news_raw_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.setContent(newsList != null ? newsList.get(position) : null);
        final Article article = newsList.get(position);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onClick(article);
                }
            }
        });
    }

    @Override
    public int getItemCount() {

        return newsList == null ? 0 : newsList.size();
    }

    public static interface Listener {
        void onClick(Article article);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.article_title)
        TextView titleText = null;
        @BindView(R.id.news_card)
        CardView cardView = null;
        @BindView(R.id.news_published_at)
        TextView articleDate = null;
        @BindView(R.id.news_image)
        ImageView imageView = null;
        private Article newsLetter;

        MyViewHolder(View convertView) {
            super(convertView);
            ButterKnife.bind(this, convertView);
        }

        void setContent(Article news) {
            newsLetter = news;
            if (newsLetter.getTitle() != null && !TextUtils.isEmpty(newsLetter.getTitle())) {
                titleText.setText(newsLetter.getTitle());
            }
            if (newsLetter.getPublishedAt() != null && !TextUtils.isEmpty(newsLetter.getPublishedAt().toString()))
                articleDate.setText(newsLetter.getPublishedAt().toString());
            if (newsLetter.getUrlToImage() != null && !TextUtils.isEmpty(newsLetter.getUrlToImage())) {
                Picasso.with(context).load(newsLetter.getUrlToImage()).into(imageView);
            }
        }
    }
}