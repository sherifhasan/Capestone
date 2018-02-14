package example.android.capestone.data.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by sheri on 2/14/2018.
 */

public class ArticleSyncService extends Service {
    private static ArticlesSyncAdapter syncAdapter = null;
    private static final Object syncAdapterLock = new Object();

    @Override
    public void onCreate() {
        super.onCreate();

        synchronized (syncAdapterLock) {
            if (syncAdapter == null) {
                syncAdapter = new ArticlesSyncAdapter(getApplicationContext(), true);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {

        return syncAdapter.getSyncAdapterBinder();
    }
}
