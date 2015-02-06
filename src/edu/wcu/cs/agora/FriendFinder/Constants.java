package edu.wcu.cs.agora.FriendFinder;

import com.google.android.gms.maps.model.LatLng;

import edu.wcu.cs.agora.FriendFinder.Authentication.ServerAuthentication;

public class Constants {
    public static final LatLng CULLOWHEE = new LatLng(35.3113624, -83.1819903);
    public static final int MAP_ZOOM = 13;
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
    public static final String ACCOUNT_TYPE = "com.friendfinder.www";
    public static final String ACCOUNT_NAME = "FriendFinder";
    public static final String AUTHTOKEN_TYPE_READ_ONLY = "Read only";
    public static final String AUTHTOKEN_TYPE_FULL_ACCESS = "Full access";

    public static final ServerAuthentication server = new ServerAuthentication();
}