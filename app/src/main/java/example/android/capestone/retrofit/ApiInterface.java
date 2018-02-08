package example.android.capestone.retrofit;

import example.android.capestone.models.News;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ApiInterface {

    @GET("top-headlines")
    Call<News> getTopHeadlines( @Query("country") String Country,@Query("apiKey") String apiKey);
}
