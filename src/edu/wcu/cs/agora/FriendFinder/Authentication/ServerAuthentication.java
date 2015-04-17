package edu.wcu.cs.agora.FriendFinder.Authentication;

import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import edu.wcu.cs.agora.FriendFinder.Constants;
import edu.wcu.cs.agora.FriendFinder.Login;
import edu.wcu.cs.agora.FriendFinder.Networking.MySingleton;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * Created by Karen on 11/17/2014.
 * Credit: Android Developers and udinic
 *
 */
public class ServerAuthentication {
        // Token returned from the server
        private static String token;
        // Context from which the class was accessed
        private static Context context;
        private static String email;
        private static String password;

    public static String signUp(String user, String pass, String authType, Context currContext) {
        email = user;
        password = pass;
        String name = email.split("@")[0];
        int age = 99;

        // url to request response from
        String url = Constants.URL + "/create/user/" + name + "/" + email +
                "/" + pass + "/" + age;
        context = currContext;
        return getToken(url);
    }

    public static String signIn(String user, String pass, String authType, Context currContext) {
        email = user;
        password = pass;
        String url = Constants.URL + "/login/" + email + "/" + pass;
        context = currContext;
        return getToken(url);
    }

    private static void makeMessage(String message, Context context) {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    private static String getToken(String url) {
        token = null;

        AsyncTask task = new AsyncTask<String, Void, Void>() {
            @Override
            protected Void doInBackground(String... params) {
                String url = params[0];
                final RequestFuture<String> future = RequestFuture.newFuture();
                StringRequest request = new StringRequest(url, future, future);

                // Add request to queue
                MySingleton.getInstance(context).addToRequestQueue(request);

                try {
                    token = future.get(5, TimeUnit.SECONDS);
                    Log.d("authentication", token != null ? token : "error getting token");
                    //makeMessage(token);
                } catch (InterruptedException error) {
                    token = "error";
                    error.printStackTrace();
                } catch (ExecutionException error) {
                    token = "error";
                    error.printStackTrace();
                } catch (TimeoutException error) {
                    // if the app couldn't reach the server, let the user know
                    //makeMessage("Error: Could not connect to server");
                    token = "error";
                    Log.e("Server Error", error.getMessage());
                }
                return null;
            }
        }.execute(url);

        /**
        final RequestFuture<String> future = RequestFuture.newFuture();

        //StringRequest request = new StringRequest(url, future, future);

        // Send the user information to the server
        StringRequest  request = new StringRequest
                (Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        future.onResponse(response);
                        makeMessage(response);
                        // get the auth token from the server
                        token = response;
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // if the app couldn't reach the server, let the user know
                        makeMessage("Error: Could not connect to server");
                        Log.e("Server Error", error.getMessage());
                    }
                });

        // Add request to queue
        MySingleton.getInstance(context).addToRequestQueue(request);

        try {
            token = future.get(5, TimeUnit.SECONDS);
            makeMessage(token);
        } catch (InterruptedException error) {
            makeMessage("InterruptedException Error");
            error.printStackTrace();
        } catch (ExecutionException error) {
            makeMessage("ExecutionException Error");
            error.printStackTrace();
        } catch (TimeoutException error) {
            // if the app couldn't reach the server, let the user know
            makeMessage("Error: Could not connect to server");
            token = "error";
            //Log.e("Server Error", error.getMessage());
        }
*/
        try {
            task.get(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

        makeMessage( token != null ? token.equals("error") ? "Error: Could not connect to server" : token : "Error: Something went wrong", context);
        return token;
    }
}
