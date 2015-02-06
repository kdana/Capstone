package edu.wcu.cs.agora.FriendFinder;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

/**
 * Created by karen on 10/20/14.
 */
public class Home extends TabActivity implements TabHost.OnTabChangeListener {
    private TabHost tabHost;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        // get tab host from the layout and set this class as the handler for it
        tabHost = getTabHost();
        tabHost.setOnTabChangedListener(this);

        TabHost.TabSpec spec;
        Intent intent;

        // Tab 1 Setup
        intent = new Intent(this, Events.class);
        spec = tabHost.newTabSpec("First").setIndicator("").setContent(intent);
        // adds intent to tab
        tabHost.addTab(spec);

        // Tab 2 Setup
        intent = new Intent(this, Events.class);
        spec = tabHost.newTabSpec("Second").setIndicator("").setContent(intent);
        tabHost.addTab(spec);

        // Set Tab 1 as default tab
        tabHost.getTabWidget().setCurrentTab(0);
    }

    @Override
    public void onTabChanged(String tabId) {

    }
}
