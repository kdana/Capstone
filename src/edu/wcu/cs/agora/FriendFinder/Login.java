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
    private Button loginButton;
    private Button registerButton;
    private boolean valid;

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
        valid = true;

        //get the login and register buttons from the layout
        loginButton    = (Button) findViewById(R.id.login);
        registerButton = (Button) findViewById(R.id.register);
        //set handler for login and register buttons to be this class
        loginButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);
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

    public void makeMessage(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void submit() {
        // get username and password
        final String email = ((EditText) findViewById(R.id.email)).getText().toString();
        final String password = ((EditText) findViewById(R.id.pass)).getText().toString();

        // If email field is blank, ask them to input an email
        if (email.equals("")) {
            Toast missing = Toast.makeText(this, "Please enter your email address.",
                                           Toast.LENGTH_SHORT);
            missing.show();
        // password field is blank, ask them to input their password
        } else if (password.equals("")){
            Toast missing = Toast.makeText(this, "Please enter your password.", Toast.LENGTH_LONG);
            missing.show();
        } else {

            // do validation here
        
            if (valid) {
                //continue to the home screen
                Log.d("Moving on", "Going to home screen");
                //Intent nextScreen = new Intent(this, Home.class);
                // Currently going to profile for testing
                Intent nextScreen = new Intent(this, Profile.class);
                //nextScreen.putExtra("user_id", user_id);
                startActivity(nextScreen);
            }
        }

    }
}
