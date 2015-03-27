package edu.wcu.cs.agora.FriendFinder;

import android.accounts.*;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.common.AccountPicker;
import edu.wcu.cs.agora.FriendFinder.Authentication.AccountAuthenticator;
import edu.wcu.cs.agora.FriendFinder.Authentication.GetTokenTask;
import edu.wcu.cs.agora.FriendFinder.Authentication.ServerAuthentication;

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
    private Button googleButton;
    private TextView registerButton;
    private String email;
    private AccountManager accountManager;


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("LOGIN", "OnCreate");
        // set the current layout to be the login screen
        setContentView(R.layout.login);
        SSLCert.nuke();
        // get the account manager for this application
        accountManager = AccountManager.get(this);

        //get the login and register buttons from the layout
        loginButton    = (Button) findViewById(R.id.login);
        registerButton = (TextView) findViewById(R.id.register);
        googleButton   = (Button) findViewById(R.id.google);

        //set handler for login and register buttons to be this class
        loginButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);
        googleButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.login) {
            //request token from server
            login();

        } else if (view.getId() == R.id.register){
            Intent register = new Intent(this, Register.class);
            //register.putExtras(getIntent().getExtras());
            //startActivity(register);
            startActivityForResult(register, Constants.REQUEST_SIGNUP);
        } else if (view.getId() == R.id.google) {
            googleSignIn();
        }
    }

    public void makeMessage(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void login() {
        // get username and password from the screen
        this.email = ((EditText) findViewById(R.id.email)).getText().toString().trim();
        String password = ((EditText) findViewById(R.id.pass)).getText().toString().trim();

        // If email field is blank, ask them to input an email
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter your email address.", Toast.LENGTH_SHORT).show();

        // If password field is blank, ask them to input their password
        } else if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please enter your password.", Toast.LENGTH_LONG).show();

        } else {
            String token = ServerAuthentication.signIn(email, password,
                    Constants.ACCOUNT_TYPE, this);

            if (token != null) {
                Intent intent = new Intent();
                intent.putExtra(AccountManager.KEY_ACCOUNT_NAME, email);
                intent.putExtra(AccountManager.KEY_ACCOUNT_TYPE, Constants.ACCOUNT_TYPE);
                intent.putExtra(AccountManager.KEY_AUTHTOKEN, token);
                intent.putExtra(Constants.ARG_USER_PASS, password);

                finishLogin(intent);
            } else {
                makeMessage("Invalid Credentials");
            }

        }
    }

    public void finishLogin(Intent intent) {
        String name = intent.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
        String pass     = intent.getStringExtra(Constants.ARG_USER_PASS);
        Account account = new Account(name, intent.getStringExtra(AccountManager.KEY_ACCOUNT_TYPE));

        if (getIntent().getBooleanExtra(Constants.ARG_ADDING_NEW_ACCOUNT, false)) {
            String token = intent.getStringExtra(AccountManager.KEY_AUTHTOKEN);
            String tokenType = Constants.ACCOUNT_TYPE;
            // Add account on device
            accountManager.addAccountExplicitly(account, pass, null);
            // Give token to account manager
            accountManager.setAuthToken(account, tokenType, token);
        } else {
            accountManager.setPassword(account, pass);
        }

        if (intent.getExtras() != null) {
            setAccountAuthenticatorResult(intent.getExtras());
            setResult(RESULT_OK, intent);
            finish();
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
        if (requestCode == Constants.REQUEST_SIGNUP && resultCode == RESULT_OK) {
            finishLogin(data);
        }

        if (requestCode == Constants.REQUEST_CODE_PICK_ACCOUNT) {
            // Receiving a result from the AccountPicker
            if (resultCode == RESULT_OK) {
                this.email = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                // With the account name acquired, go get the auth token
                getGoogleToken();
                makeMessage("Got the google token!");
            } else if (resultCode == RESULT_CANCELED) {
                // The account picker dialog closed without selecting an account.
                // Notify users that they must pick an account to proceed.
                Toast.makeText(this, R.string.pick_account, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getGoogleToken() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                                                  getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new GetTokenTask(this, email, Constants.SCOPE);
        } else {
            Toast.makeText(this, "Error: Unable to connect to network", Toast.LENGTH_SHORT);
        }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.createEventMenuButton:
                intent = new Intent(this, EventsPage.class);
                startActivity(intent);
                return true;
            case R.id.searchMenuButton:
                //intent = new Intent(this, Search.class);
                //startActivity(intent);
                return true;
            case R.id.viewProfileMenuButton:
                intent = new Intent(this, Profile.class);
                startActivity(intent);
                return true;
            case R.id.settingsMenuButton:
                intent = new Intent(this, Settings.class);
                startActivity(intent);
                return true;
            case R.id.logOutMenuButton:
                //TODO: invalidate token and remove from authenticator
                return true;
            case R.id.mapMenuButton:
                intent = new Intent(this, Map.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
