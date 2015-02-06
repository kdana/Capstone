package edu.wcu.cs.agora.FriendFinder;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Tyler Allen
 * 09/29/2014
 *
 * Code for functionality on the user privacy settings page.
 */

public class Settings extends Activity
{
    private TextView privacy;

    /**
     * Currently the default onCreate.
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
    }
}