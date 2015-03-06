package edu.wcu.cs.agora.FriendFinder;

import android.accounts.*;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.common.AccountPicker;
import edu.wcu.cs.agora.FriendFinder.Authentication.AccountAuthenticator;
import edu.wcu.cs.agora.FriendFinder.Authentication.GetTokenTask;

/**
 * Tyler Allen
 * Karen Dana
 *
 * 09/29/2014
 *
 * Code for functionality on the log in page.
 */
public class Login extends AccountAuthenticatorActivity implements View.OnClickListener
{
    private Button loginButton;
    private Button registerButton;
    private boolean valid;
    private String email;
    private AccountManager accountManager;


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("LOGIN", "OnCreate");
        setContentView(R.layout.login);
        SSLCert.nuke();
        valid = true;
        accountManager = AccountManager.get(this);

        //get the login and register buttons from the layout
        loginButton    = (Button) findViewById(R.id.login);
        registerButton = (Button) findViewById(R.id.register);
        //googleButton   = (Button) findViewById(R.id.google);
        //set handler for login and register buttons to be this class
        loginButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);
        //googleButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.login) {
            //request token from server
            submit();

        //} else if (view.getId() == R.id.googleButton) {
            //googleSignIn();
        } else {
            Intent register = new Intent(this, Register.class);
            //register.putExtras(getIntent().getExtras());
            //startActivity(register);
            startActivityForResult(register, Constants.REQUEST_SIGNUP);
        }
    }

    public void makeMessage(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void submit() {
        // get username and password
        this.email = ((EditText) findViewById(R.id.email)).getText().toString();
        String password = ((EditText) findViewById(R.id.pass)).getText().toString();

        getToken(Constants.ACCOUNT_TYPE, Constants.AUTHTOKEN_TYPE_FULL_ACCESS);

        // If email field is blank, ask them to input an email
        if (email.equals("")) {
            Toast.makeText(this, "Please enter your email address.", Toast.LENGTH_SHORT).show();

        // password field is blank, ask them to input their password
        } else if (password.equals("")){
            Toast.makeText(this, "Please enter your password.", Toast.LENGTH_LONG).show();

        } else {
            googleSignIn();
            // do validation here
        
            if (valid) {
                //nextActivity();
            }
        }

    }

    private void googleSignIn() {

        // get all of the current google accounts in the system
        String[] accountTypes = new String[]{"com.google"};
        // Display a list view that lets the user choose or add a Google account to log in with
        Intent intent = AccountPicker.newChooseAccountIntent(null, null, accountTypes, false, null,
                                                             null, null, null);

        startActivityForResult(intent, Constants.REQUEST_CODE_PICK_ACCOUNT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_SIGNUP) {
            addNewAccount(Constants.ACCOUNT_TYPE, Constants.AUTHTOKEN_TYPE_FULL_ACCESS);
            nextActivity();
        }

        if (requestCode == Constants.REQUEST_CODE_PICK_ACCOUNT) {
            // Receiving a result from the AccountPicker
            if (resultCode == RESULT_OK) {
                this.email = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                // With the account name acquired, go get the auth token
                getToken(Constants.ACCOUNT_TYPE, Constants.AUTHTOKEN_TYPE_FULL_ACCESS);
            } else if (resultCode == RESULT_CANCELED) {
                // The account picker dialog closed without selecting an account.
                // Notify users that they must pick an account to proceed.
                Toast.makeText(this, R.string.pick_account, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void addNewAccount(String accountType, String authTokenType) {
        final AccountManagerFuture<Bundle> future = accountManager.addAccount(accountType, authTokenType,
                null, null, this, new AccountManagerCallback<Bundle>() {
                    @Override
                    public void run(AccountManagerFuture<Bundle> future) {
                        try {
                            Bundle bundle = future.getResult();
                            makeMessage("Account was created");
                            Log.d("Add Account", "AddNewAccount Bundle is " + bundle);
                        } catch (Exception e) {
                            e.printStackTrace();
                            makeMessage(e.getMessage());
                        }
                    }
                }, null);
    }

    private void getToken(String accountType, String authTokenType) {
        //accountManager.getAuthToken()

        final AccountManagerFuture<Bundle> future =
                accountManager.getAuthTokenByFeatures(accountType, authTokenType, null, this, null,
                null, new AccountManagerCallback<Bundle>() {
                    @Override
                    public void run(AccountManagerFuture<Bundle> future) {
                        Bundle bnd = null;
                        try {
                            bnd = future.getResult();
                            final String authtoken = bnd.getString(AccountManager.KEY_AUTHTOKEN);
                            makeMessage(((authtoken != null) ? "SUCCESS!\ntoken: " + authtoken : "FAIL"));
                            Log.d("Getting Token", "GetTokenForAccount Bundle is " + bnd);

                        } catch (Exception e) {
                            e.printStackTrace();
                            makeMessage(e.getMessage());
                        }
                    }
                }
                , null);




        /**
        ConnectivityManager connectivityManager = (ConnectivityManager)
                                                  getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new GetTokenTask(this, email, Constants.SCOPE);
        } else {
            Toast.makeText(this, "Error: Unable to connect to network", Toast.LENGTH_SHORT);
        }
         */
    }

    private void nextActivity() {
        //continue to the home screen
        Log.d("Moving on", "Going to home screen");
        //Intent nextScreen = new Intent(this, Home.class);
        // Currently going to profile for testing
        Intent nextScreen = new Intent(this, Profile.class);
        //nextScreen.putExtra("user_id", user_id);
        startActivity(nextScreen);
    }
}
