package edu.wcu.cs.agora.FriendFinder;

import android.provider.CalendarContract;
import com.google.android.gms.maps.model.LatLng;

import edu.wcu.cs.agora.FriendFinder.Authentication.ServerAuthentication;

public class Constants {
    public static final int    REQUEST_CODE_PICK_ACCOUNT = 1000;
    public static final int    REQUEST_SIGNUP = 1;
    //public static final String SCOPE = "audience:server:client_id:908000618400-gm89ngk9va6106mn462lgmr3js36b6m4.apps.googleusercontent.com";
    public static final String SCOPE = "908000618400-vj5mj9anl3cskdgjb64qvapl1rll21kf.apps.googleusercontent.com";
    public static final LatLng CULLOWHEE = new LatLng(35.3113624, -83.1819903);
    public static final int    MAP_ZOOM = 13;
    public static final String PREFERENCES_NAME = "Preferences";
    public static final String ARG_USER_PASS = "password";
    public static final String ARG_ADDING_NEW_ACCOUNT = "new account";
    public static final String ARG_AUTH_TYPE = "authorization";
    public static final String ARG_ACCOUNT_TYPE = "account type";
    public static final String ARG_ACCOUNT_EMAIL = "email";
    public static final String KEY_ERROR_MESSAGE = "error message";
    public static final String ARG_SETTINGS = "settings";
    public static final String ARG_USERNAME = "username";
    public static final String URL = "https://kartemis.no-ip.org";
    public static final String ACCOUNT_TYPE = "edu.tomokumo.wcu";
    public static final String ACCOUNT_NAME = "FriendFinder";
    public static final String AUTHTOKEN_TYPE_READ_ONLY = "Read only";
    public static final String AUTHTOKEN_TYPE_FULL_ACCESS = "Full access";
    // Projection array. Creating indices for this array instead of doing
// dynamic lookups improves performance.
    public static final String[] EVENT_PROJECTION = new String[] {
            CalendarContract.Calendars._ID,                           // 0
            CalendarContract.Calendars.ACCOUNT_NAME,                  // 1
            CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,         // 2
            CalendarContract.Calendars.OWNER_ACCOUNT                  // 3
    };

    // The indices for the projection array above.
    private static final int PROJECTION_ID_INDEX = 0;
    private static final int PROJECTION_ACCOUNT_NAME_INDEX = 1;
    private static final int PROJECTION_DISPLAY_NAME_INDEX = 2;
    private static final int PROJECTION_OWNER_ACCOUNT_INDEX = 3;

    public static final ServerAuthentication server = new ServerAuthentication();
}