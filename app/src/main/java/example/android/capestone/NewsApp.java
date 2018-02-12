package example.android.capestone;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by sheri on 2/9/2018.
 */

public class NewsApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
