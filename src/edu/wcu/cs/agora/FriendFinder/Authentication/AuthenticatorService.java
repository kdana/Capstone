package edu.wcu.cs.agora.FriendFinder.Authentication;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by Karen on 11/17/2014.
 *
 * http://udinic.wordpress.com/2013/04/24/write-your-own-android-authenticator/
 */
public class AuthenticatorService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        AccountAuthenticator authenticator = new AccountAuthenticator(this);
        return authenticator.getIBinder();
    }
}
