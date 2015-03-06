package edu.wcu.cs.agora.FriendFinder.Authentication;


import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;

import java.io.IOException;

/**
 * Created by Karen on 3/6/2015.
 */
public class GetTokenTask  extends AsyncTask{
    private Activity activity;
    private String email;
    private String scope;

    public GetTokenTask(Activity activity, String email, String scope) {
        this.activity = activity;
        this.email     = email;
        this.scope    = scope;
    }

    /**
     * Runs when execute() is called on the instance
     *
     * @param params
     * @return
     */
    @Override
    protected Object doInBackground(Object[] params) {
        try {
            String token = getToken();
            if (token != null) {
                //we have a token!
            }
        } catch (IOException e) {
            Log.e("Token Retrieval", "Error trying to get token");
        }
        return null;
    }

    private String getToken() throws IOException {
        try {
            return GoogleAuthUtil.getToken(activity, email, scope);
        } catch (UserRecoverableAuthException uraE) {
            Log.e("Token Retrieval", "User Recoverable Auth Exception: " + uraE.getMessage());
            Toast.makeText(activity, "Recoverable Exception", Toast.LENGTH_SHORT);
        } catch (GoogleAuthException gaE) {
            Log.e("Token Retrieval", "Google Auth Exception: " + gaE.getMessage());
            Toast.makeText(activity, "Please update Google Play", Toast.LENGTH_SHORT);
        }
        return null;
    }
}
