package edu.wcu.cs.agora.FriendFinder;

import android.app.Activity;
import android.content.Intent;
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
 * Created by tyler on 9/26/14.
 */

public class Register extends Activity implements View.OnClickListener {

    Button signUpButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        SSLCert.nuke();

        //Get the button from the layout
        signUpButton = (Button) findViewById(R.id.sign_up);
        //Set this class to be the handler for the button
        signUpButton.setOnClickListener(this);
    }

    public void makeMessage(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    public void onClick(View v) {
        //get the input from the edit texts in the layout
        String email       = ((EditText) findViewById(R.id.email)).getText().toString();
        String pass        = ((EditText) findViewById(R.id.pass)).getText().toString();
        String confirmPass = ((EditText) findViewById(R.id.repeat_pass)).getText().toString();

        //make sure the passwords are the same
        if (pass.equals(confirmPass)) {
            //send data to server to create user
            //Log.d("Java", "Splitting email by @ sign");
            String name = email.split("@")[0];
            int age = 99;

            // url to request response from
            String url = Constants.URL + "/create/user/" + name + "/" + email +
                                                     "/" + pass + "/" + age;
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
            //Log.d("Connection", "Creating new user");
            MySingleton.getInstance(this).addToRequestQueue(request);

            //continue to the home screen
            Log.d("Moving on", "Going to home screen");
            Intent nextScreen = new Intent(this, Home.class);
            startActivity(nextScreen);

        } else {
            Toast mismatch = Toast.makeText(this, "Password does not match", Toast.LENGTH_SHORT);
            mismatch.show();
        }
        //finish here
    }
}