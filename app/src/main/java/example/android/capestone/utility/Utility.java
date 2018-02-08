package example.android.capestone.utility;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by sheri on 2/6/2018.
 */

public class Utility {
    public final static String API_KEY = "52fdf019a14a41ca89f28af20df01134";
    public final static String COUNTRY = "us";


    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        return cm.getActiveNetworkInfo() != null;
    }
}
