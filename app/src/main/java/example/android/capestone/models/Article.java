package example.android.capestone.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

import paperparcel.PaperParcel;

/**
 * Created by sheri on 2/6/2018.
 */
@PaperParcel
public class Article implements Parcelable {

    public static final Creator<Article> CREATOR = PaperParcelArticle.CREATOR;
    @SerializedName("source")
    Source source;
    @SerializedName("author")
    String author;
    @SerializedName("title")
    String title;
    @SerializedName("description")
    String description;
    @SerializedName("url")
    String url;
    @SerializedName("urlToImage")
    String urlToImage;
    @SerializedName("publishedAt")
    Date publishedAt;
    String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public Date getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        PaperParcelArticle.writeToParcel(this, parcel, i);
    }
}
