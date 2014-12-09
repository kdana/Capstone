package edu.wcu.cs.agora.FriendFinder;

import android.content.Context;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

/**
 * Created by Karen on 11/17/2014.
 * Credit: Android Developers and udinic
 *
 */
public class ServerAuthentication {
    public String signUp(final String name, final String email, final String pass, String authType) throws Exception {
        return "auth token from server here";
    }

    public String signIn(final String user, final String pass, String authType) throws Exception {
        return "auth token from server here";
    }
}
