package edu.wcu.cs.agora.FriendFinder;

import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import edu.wcu.cs.agora.FriendFinder.Authentication.ServerAuthentication;
import edu.wcu.cs.agora.FriendFinder.Networking.MySingleton;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by tyler on 9/26/14.
 */

public class Register extends Activity implements View.OnClickListener {

    private Button signUpButton;
    private boolean valid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        SSLCert.nuke();
        valid = true;

        //Get the button from the layout
        signUpButton = (Button) findViewById(R.id.sign_up);
        //Set this class to be the handler for the button
        signUpButton.setOnClickListener(this);
    }

    public void makeMessage(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void onClick(View view) {
        String email       = ((EditText) findViewById(R.id.email)).getText().toString().trim();
        String pass        = ((EditText) findViewById(R.id.pass)).getText().toString().trim();
        String confirmPass = ((EditText) findViewById(R.id.repeat_pass)).getText().toString().trim();

        if (view.getId() == R.id.sign_up) {
            // make sure the required fields are not empty
            if (TextUtils.isEmpty(email)) {
                makeMessage("Please enter your email address.");
            } else if (TextUtils.isEmpty(pass)) {
                makeMessage("Please enter your password.");
            } else if (TextUtils.isEmpty(confirmPass)) {
                makeMessage("Please confirm your password.");
            } else if (!pass.equals(confirmPass)) {
                makeMessage("Passwords do not match.");
            } else {
                // if all the information is entered, create the account
                createAccount(email, pass);
            }
        }
    }

    public void createAccount(String email, String pass) {
        // make sure the chosen email is not currently in use
        boolean valid = isFree(email);

        if (valid) {
            String token = ServerAuthentication.signUp(email, pass,
                                                       Constants.AUTHTOKEN_TYPE_FULL_ACCESS, this);
            if (token != null) {
                //finish here
                Intent intent = new Intent();
                intent.putExtra(AccountManager.KEY_ACCOUNT_NAME, email);
                intent.putExtra(AccountManager.KEY_ACCOUNT_TYPE, Constants.ACCOUNT_TYPE);
                intent.putExtra(AccountManager.KEY_AUTHTOKEN, token);
                intent.putExtra(Constants.ARG_USER_PASS, pass);
                setResult(RESULT_OK, intent);
                finish();
            } else {
                makeMessage("Error processing request");
            }
        } else {
            makeMessage("Email address currently in use");
        }
    }

    private boolean isFree(String email) {
        // email is free unless proven otherwise
        valid = true;
        // link to retrieve the user with the given email
        String url = Constants.URL + "/user/email/" + email;

        // send a request for a JSON object representation of a user
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // if the user exists, the email is taken
                        if (response != null) {
                            valid = false;
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        makeMessage("Error: Could not connect to server");
                        Log.e("Server Error", error.getMessage());
                        valid = false;
                    }
                });

        // Add request to queue
        MySingleton.getInstance(this).addToRequestQueue(jsObjRequest);
        return valid;
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        super.onBackPressed();
    }
}