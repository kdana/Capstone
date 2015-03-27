package edu.wcu.cs.agora.FriendFinder;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

/**
 * Tyler Allen
 * 09/29/2014
 *
 * Code for functioning specific event instance.
 */
public class EventsPage extends Activity implements View.OnClickListener {
    private Button mapButton;

    /**
     * Currently the default fragment onCreate.
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_page);
        mapButton = (Button) findViewById(R.id.mapButton);

        mapButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == mapButton.getId()) {
            Intent intent = new Intent(this, Map.class);
            //TODO: get and set LatLng coordinates and pass them to the map
            startActivity(intent);
        }
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