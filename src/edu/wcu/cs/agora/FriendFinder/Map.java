package edu.wcu.cs.agora.FriendFinder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * Tyler Allen
 * Karen Dana
 * 09/29/2014
 *
 * Code for functionality on the log in page.
 */
public class Map extends Activity {

    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);

        // Get the map fragment
        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(Constants.CULLOWHEE, Constants.MAP_ZOOM));
        map.addMarker(new MarkerOptions().title("Cullowhee")
                                         .snippet("Home of Western Carolina University")
                                         .position(Constants.CULLOWHEE));
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