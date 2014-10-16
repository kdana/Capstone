package edu.wcu.cs.agora.FriendFinder;

import android.accounts.Account;
import android.content.*;
import android.os.Bundle;

/**
 * Created by tyler on 10/16/2014.
 */
public class SyncAdapter extends AbstractThreadedSyncAdapter
{
    private final ContentResolver contentResolver;

    /**
     * Creates an {@link android.content.AbstractThreadedSyncAdapter}.
     *
     * @param context the {@link android.content.Context} that this is running within.
     * @param autoInitialize if true then sync requests that have {@link
     * ContentResolver#SYNC_EXTRAS_INITIALIZE} set will be internally handled by {@link
     * android.content.AbstractThreadedSyncAdapter} by calling {@link
     * ContentResolver#setIsSyncable(android.accounts.Account,
     * String, int)} with 1 if it
     */
    public SyncAdapter (Context context, boolean autoInitialize)
    {
        super(context, autoInitialize);
        contentResolver = context.getContentResolver();
    }

    /**
     * Perform a sync for this account. SyncAdapter-specific parameters may be specified in extras,
     * which is guaranteed to not be null. Invocations of this method are guaranteed to be
     * serialized.
     *
     * @param account the account that should be synced
     * @param extras SyncAdapter-specific parameters
     * @param authority the authority of this sync request
     * @param provider a ContentProviderClient that points to the ContentProvider for this
     * authority
     * @param syncResult SyncAdapter-specific parameters
     */
    @Override
    public void onPerformSync (Account account, Bundle extras, String authority,
                               ContentProviderClient provider, SyncResult syncResult)
    {

    }
}
