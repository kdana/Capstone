package edu.wcu.cs.agora.FriendFinder;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by tyler on 10/16/2014.
 *
 * Credit: Android Framework SyncService.java example.
 *
 * Reference: https://developer.android.com/training/sync-adapters/creating-authenticator.html
 * to continue
 */
public class SyncService extends Service
{
    private static final Object      syncAdapterLock = new Object();
    private static       SyncAdapter syncAdapter     = null;

    /**
     * Thread-safe constructor, creates static {@link SyncAdapter} instance.
     */
    @Override
    public void onCreate ()
    {
        super.onCreate();
        synchronized (syncAdapterLock)
        {
            if (syncAdapter == null)
            {
                syncAdapter = new SyncAdapter(getApplicationContext(), true);
            }
        }
    }

    @Override
    /**
     * Logging-only destructor.
     */
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * Return Binder handle for IPC communication with {@link SyncAdapter}.
     *
     * <p>New sync requests will be sent directly to the SyncAdapter using this channel.
     *
     * @param intent Calling intent
     * @return Binder handle for {@link SyncAdapter}
     */
    @Override
    public IBinder onBind(Intent intent) {
        return syncAdapter.getSyncAdapterBinder();
    }
}
