package edu.wcu.cs.agora.FriendFinder;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import edu.wcu.cs.agora.FriendFinder.Networking.MySingleton;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by karen on 2/6/15.
 */
public class Profile extends Activity implements View.OnClickListener {

    private String[] friends;
    private int user_id;
    private Button schedule;
    private Button profileButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        schedule = (Button) findViewById(R.id.schedule);
        profileButton = (Button) findViewById(R.id.profileButton);
        schedule.setOnClickListener(this);
        profileButton.setOnClickListener(this);

        String url = Constants.URL + "/user/" + 1;

        //get id of the user that was selected and add it to the url
        /**
        user_id = savedInstanceState.getInt("user_id", -1);
        if (user_id != -1) {
            url += user_id;
        } else {
            makeMessage("User is not in system");
        }
        */

        // Request the user's data from the server
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {
                        parseJSON((JSONObject) response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        makeMessage("Error: Could not connect to server");
                        Log.e("Server Error", error.getMessage());
                    }
                });
        // Add request to queue
        MySingleton.getInstance(this).addToRequestQueue(jsObjRequest);
    }

    public void makeMessage(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toast.show();
    }

    private void parseJSON(JSONObject response) {
        // Retrieve the text views, buttons, and image view from the layout
        TextView name = (TextView) findViewById(R.id.name);
        TextView status = (TextView) findViewById(R.id.status);
        TextView likes = (TextView) findViewById(R.id.likes);
        TextView dislikes = (TextView) findViewById(R.id.dislikes);
        TextView events = (TextView) findViewById(R.id.events);
        ImageView picture = (ImageView) findViewById(R.id.picture);

        //unset user id
        int id = -1;

        String type = "";

        // Set the information for the given user
        try {
            name.setText(response.getString("name"));
            //TODO: set status to value from calendar here
            likes.setText("");
            dislikes.setText("");
            events.setText("");
            // get the id of the profile owner
            // get the list of people in the user's circle
            //String[] friends = getFriends();
            id = response.getInt("id");


            if (id == user_id) {
                type = "owner";
            } else {
                type = "user";
            }

        } catch (JSONException e) {
            makeMessage("Could not display user data");
            e.printStackTrace();
        }

        String text = "";
        switch (type) {
            case "owner":
                text = "Edit";
                break;
            case "friend":
                text = "Locate";
                break;
            case "user":
                text = "Add to Group";
                break;
            default:
                makeMessage("Stop that!");
        }
        profileButton.setText(text);
    }

    @Override
    public void onClick(View v) {
        Button button = (Button) v;
        String text = button.getText().toString();

        switch (text) {
            case "Edit":
                // change activity layout here
                break;
            case "Locate":
                makeMessage("Locate feature coming soon!");
                break;
            case "Add to Group":
                makeMessage("Group feature coming soon!");
                break;
            case "Schedule":
                //makeMessage("Schedule feature coming soon!");
                calendar();
                break;
            default:
                makeMessage("Stop that!");
        }
    }

    private void calendar() {
        // Run query
        Cursor cur = null;
        ContentResolver cr = getContentResolver();
        Uri uri = CalendarContract.Calendars.CONTENT_URI;
        String selection = "((" + CalendarContract.Calendars.ACCOUNT_NAME + " = ?) AND ("
                + CalendarContract.Calendars.ACCOUNT_TYPE + " = ?) AND ("
                + CalendarContract.Calendars.OWNER_ACCOUNT + " = ?))";
        String[] selectionArgs = new String[] {"sampleuser@gmail.com", "com.google",
                "sampleuser@gmail.com"};
        // Submit the query and get a Cursor object back.
        cur = cr.query(uri, Constants.EVENT_PROJECTION, selection, selectionArgs, null);
    }

    public String[] getFriends() {
        // url to request response from
        String url = Constants.URL;
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
                makeMessage("Error: Could not connect to server");
                Log.e("Server Error", error.getMessage());
            }
        });
        // Add request to queue
        MySingleton.getInstance(this).addToRequestQueue(request);
        return friends;
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
