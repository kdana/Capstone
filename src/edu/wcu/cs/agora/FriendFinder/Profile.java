package edu.wcu.cs.agora.FriendFinder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);



        //get id of the user that was selected and add it to the url
        user_id = savedInstanceState.getInt("user_id");
        String url = Constants.URL + "/user/" + user_id;

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
        Button schedule = (Button) findViewById(R.id.schedule);
        Button profileButton = (Button) findViewById(R.id.profileButton);
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
            String[] friends = getFriends();
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

        // Set this class to be the handler for button presses
        schedule.setOnClickListener(this);
        profileButton.setOnClickListener(this);

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
                // pull up Google Calendar here
                break;
            default:
                makeMessage("Stop that!");
        }
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
}
