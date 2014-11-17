package edu.wcu.cs.agora.FriendFinder;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.*;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.google.android.gms.identity.intents.AddressConstants;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Tyler Allen
 * Karen Dana
 *
 * 09/29/2014
 *
 * Code for functionality on the log in page.
 */
public class Login extends Activity implements View.OnClickListener
{
    private String authority;
    private Button loginButton;
    private Button registerButton;

    /**
     * Called when the activity is first created. Boilerplate code.
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        Log.d("LOGIN", "OnCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        //set handler for login and register buttons
        loginButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);

        authority = getResources().getString(R.string.authority);
        Intent intent = new Intent(this, SyncService.class);

        startService(intent);
        startService(new Intent(this, GenericAccountService.class));


        ContentResolver.setSyncAutomatically(GenericAccountService.getAccount(), authority, true);
        final AtomicReference<Account> account = new AtomicReference<>(CreateSyncAccount(this));

        final Bundle extras = new Bundle();
        Runnable r = new Runnable()
        {
            /**
             * Starts executing the active part of the class' code. This method is called when a
             * thread is
             * started that has been created with a class which implements {@code Runnable}.
             */
            @Override
            public void run ()
            {
                ContentResolver.requestSync(account.get(), authority, extras);
                Log.d("LOGIN", "Sync Requested");
            }
        };
        Handler handler = new Handler();
        handler.postDelayed(r, 5000);
    }

    /**
     * Create a new dummy account for the sync adapter
     *
     * @param context The application context
     */
    public static Account CreateSyncAccount(Context context) {
        // Create the account type and default account
        Account newAccount = new Account("temp", "edu.wcu");
        // Get an instance of the Android account manager
        AccountManager accountManager =
                (AccountManager) context.getSystemService(
                        ACCOUNT_SERVICE);
        /*
         * Add the account and account type, no password or user data
         * If successful, return the Account object, otherwise report an error.
         */
        if (accountManager.addAccountExplicitly(newAccount, null, null)) {
            Log.d("LOGIN", "Successfully created account.");
            /*
             * If you don't set android:syncable="true" in
             * in your <provider> element in the manifest,
             * then call context.setIsSyncable(account, authority, 1)
             * here.
             */
        } else {
            Log.d("LOGIN", "Error creating account");
            /*
             * The account exists or some other error occurred. Log this, report it,
             * or handle it internally.
             */
        }

        return newAccount;
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.login) {
            //get username and password
            String username = String.valueOf(((EditText) findViewById(R.id.email)).getText());
            String password = String.valueOf(((EditText) findViewById(R.id.pass)).getText());

            //send to server


        } else {
            Intent intent = new Intent(this, Register.class);
            startActivity(intent);
        }
    }
    public void submit() {
        final String username = ((EditText) findViewById(R.id.email)).getText().toString();
        final String password = ((EditText) findViewById(R.id.pass)).getText().toString();
        new AsyncTask<Void, Void, Intent>() {
            @Override
            protected Intent doInBackground(Void... params) {
                String mAuthTokenType = "login";
                String authtoken = sServerAuthenticate.userSignIn(username, password, mAuthTokenType);
                final Intent res = new Intent();
                res.putExtra(AccountManager.KEY_ACCOUNT_NAME, username);
                res.putExtra(AccountManager.KEY_ACCOUNT_TYPE, ACCOUNT_TYPE);
                res.putExtra(AccountManager.KEY_AUTHTOKEN, authtoken);
                res.putExtra(PARAM_USER_PASS, password);
                return res;
            }

            @Override
            protected void onPostExecute(Intent intent) {
                finishLogin(intent);
            }
        }.execute();

    }

    private void finishLogin(Intent intent) {
        String accountName = intent.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
        String accountPassword = intent.getStringExtra(PARAM_USER_PASS);
        final Account account = new Account(accountName, intent.getStringExtra(AccountManager.KEY_ACCOUNT_TYPE));
        if (getIntent().getBooleanExtra(ARG_IS_ADDING_NEW_ACCOUNT, false)) {
            String authtoken = intent.getStringExtra(AccountManager.KEY_AUTHTOKEN);
            String authtokenType = mAuthTokenType;
            // Creating the account on the device and setting the auth token we got
            // (Not setting the auth token will cause another call to the server to authenticate the user)
            mAccountManager.addAccountExplicitly(account, accountPassword, null);
            mAccountManager.setAuthToken(account, authtokenType, authtoken);
        } else {
            mAccountManager.setPassword(account, accountPassword);
        }
        setAccountAuthenticatorResult(intent.getExtras());
        setResult(RESULT_OK, intent);
        finish();
    }
}
