package edu.wcu.cs.agora.FriendFinder;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import edu.wcu.cs.agora.FriendFinder.Networking.MySingleton;

/**
 * Tyler Allen
 * Karen Dana
 *
 * 09/29/2014
 *
 * Code for functionality on the log in page.
 */
public class Login extends android.accounts.AccountAuthenticatorActivity implements View.OnClickListener
{
    private String authority;
    private Button loginButton;
    private Button registerButton;
    private AccountManager accountManager;
    private String authTokenType;
    private final int REQ_SIGNUP = 1;
    private SharedPreferences settings;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        Log.d("LOGIN", "OnCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        SSLCert.nuke();

        // Initialize account manager and token type
        accountManager = AccountManager.get(getBaseContext());
        //String accountName = getIntent().getStringExtra(Constants.ARG_ACCOUNT_NAME);
        //String authTokenType = getIntent().getStringExtra(Constants.ARG_AUTH_TYPE);
        //if (authTokenType == null) {
        //    authTokenType = Constants.AUTHTOKEN_TYPE_FULL_ACCESS;
        //}

        //get the login and register buttons from the layout
        loginButton    = (Button) findViewById(R.id.login);
        registerButton = (Button) findViewById(R.id.register);
        //set handler for login and register buttons
        loginButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);

        // Get the Shared Preferences
        settings = getSharedPreferences(Constants.PREFERENCES_NAME, MODE_WORLD_READABLE);

        //authority = getResources().getString(R.string.authority);
        //Intent intent = new Intent(this, SyncService.class);

        //startService(intent);
        //startService(new Intent(this, GenericAccountService.class));


        //ContentResolver.setSyncAutomatically(GenericAccountService.getAccount(), authority, true);
        //final AtomicReference<Account> account = new AtomicReference<>(CreateSyncAccount(this));

//        final Bundle extras = new Bundle();
        //Runnable r = new Runnable()
        //{
            /**
             * Starts executing the active part of the class' code. This method is called when a
             * thread is
             * started that has been created with a class which implements {@code Runnable}.
             */
        /**    @Override
            public void run ()
            {
                ContentResolver.requestSync(account.get(), authority, extras);
                Log.d("LOGIN", "Sync Requested");
            }
        };
        Handler handler = new Handler();
        handler.postDelayed(r, 5000);
*/
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
            //request token from server
            submit();

        } else {
            Intent register = new Intent(this, Register.class);
            //register.putExtras(getIntent().getExtras());
            startActivity(register);
            //startActivityForResult(register, REQ_SIGNUP);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        //If user has entered credentials on register page
        if (requestCode == REQ_SIGNUP && resultCode == RESULT_OK) {
            finishLogin(data);
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void makeMessage(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void submit() {
        // get username and password
        final String email = ((EditText) findViewById(R.id.email)).getText().toString();
        final String password = ((EditText) findViewById(R.id.pass)).getText().toString();

        if (email.equals("")) {
            Toast missing = Toast.makeText(this, "Please enter your email address.",
                                           Toast.LENGTH_SHORT);
            missing.show();
        } else if (password.equals("")){
            Toast missing = Toast.makeText(this, "Please enter your password.", Toast.LENGTH_LONG);
            missing.show();
        } else {

            // url to request response from
            String url = Constants.URL + "/user/email/" + email;
            // Request a string response from the provided URL.
            StringRequest request = new StringRequest(Request.Method.GET, url,
                    new Response.Listener() {
                        @Override
                        public void onResponse(Object obj) {
                            makeMessage("Response: " + (String) obj);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    makeMessage(error.getMessage());
                }
            });
            // Add request to queue
            MySingleton.getInstance(this).addToRequestQueue(request);

            //continue to the home screen
            Log.d("Moving on", "Going to home screen");
            Intent nextScreen = new Intent(this, Home.class);
            startActivity(nextScreen);



        /**    new AsyncTask<Void, Void, Intent>() {
                @Override
                protected Intent doInBackground(Void... params) {
                    // get authentication token from the server
                    Log.d("Authentication", "Beginning Authentication");
                    Intent res = null;
                    try {
                        String authtoken = Constants.server.signIn(email, password, "login");
                        res = new Intent();
                        res.putExtra(AccountManager.KEY_ACCOUNT_NAME, email);
                        res.putExtra(AccountManager.KEY_ACCOUNT_TYPE, Constants.ARG_ACCOUNT_TYPE);
                        res.putExtra(AccountManager.KEY_AUTHTOKEN, authtoken);
                        res.putExtra(Constants.ARG_USER_PASS, password);
                    } catch (Exception e) {
                        Log.e("Error", "Could not sign in to server " + e.getMessage());
                        e.printStackTrace();
                    }
                    return res;
                }


                //send to server
//            NetworkHandler handler = new NetworkHandler();
//            handler.send(this, "user=" + username + "&pass=" + password);

                @Override
                protected void onPostExecute(Intent intent) {
                    finishLogin(intent);
                }
            }.execute();
         */
        }

    }


    private void finishLogin(Intent intent) {
        String accountName = intent.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
        String accountPassword = intent.getStringExtra(Constants.ARG_USER_PASS);
        final Account account = new Account(accountName, intent.getStringExtra(AccountManager.KEY_ACCOUNT_TYPE));
        if (getIntent().getBooleanExtra(Constants.ARG_ADDING_NEW_ACCOUNT, false)) {
            String authtoken = intent.getStringExtra(AccountManager.KEY_AUTHTOKEN);
            String authtokenType = "login";
            // Creating the account on the device and setting the auth token we got
            // (Not setting the auth token will cause another call to the server to authenticate the user)
            accountManager.addAccountExplicitly(account, accountPassword, null);
            accountManager.setAuthToken(account, authtokenType, authtoken);
        } else {
            accountManager.setPassword(account, accountPassword);
        }
        setAccountAuthenticatorResult(intent.getExtras());
        setResult(RESULT_OK, intent);
        finish();
    }
}
