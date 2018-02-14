package example.android.capestone.data.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;


/**
 * Created by sheri on 2/14/2018.
 */

public class AuthenticatorService extends Service {
    private Authenticator authenticator;

    @Override
    public void onCreate() {
        authenticator = new Authenticator(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return authenticator.getIBinder();
    }
}
