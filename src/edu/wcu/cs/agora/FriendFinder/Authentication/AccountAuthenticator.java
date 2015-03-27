package edu.wcu.cs.agora.FriendFinder.Authentication;

import android.accounts.*;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import edu.wcu.cs.agora.FriendFinder.Constants;
import edu.wcu.cs.agora.FriendFinder.Login;

/**
 * Created by Karen on 11/17/2014.
 *
 * http://udinic.wordpress.com/2013/04/24/write-your-own-android-authenticator/
 */
public class AccountAuthenticator extends AbstractAccountAuthenticator {

    private Context context;

    public AccountAuthenticator(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public Bundle editProperties(AccountAuthenticatorResponse accountAuthenticatorResponse, String s) {
        return null;
    }

    @Override
    public Bundle addAccount(AccountAuthenticatorResponse response, String accountType, String authTokenType, String[] requiredFeatures, Bundle options) throws NetworkErrorException {
        final Intent intent = new Intent(context, Login.class);
        intent.putExtra(Constants.ARG_ACCOUNT_TYPE, accountType);
        intent.putExtra(Constants.ARG_AUTH_TYPE, authTokenType);
        intent.putExtra(Constants.ARG_ADDING_NEW_ACCOUNT, true);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
        final Bundle bundle = new Bundle();
        bundle.putParcelable(AccountManager.KEY_INTENT, intent);
        return bundle;
    }

    @Override
    public Bundle confirmCredentials(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, Bundle bundle) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle getAuthToken(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws NetworkErrorException {

        // Extract the username and password from the Account Manager, and ask
        // the server for an appropriate AuthToken.
        final AccountManager manager = AccountManager.get(context);

        String authToken = manager.peekAuthToken(account, authTokenType);

        // Lets give another try to authenticate the user
        if (TextUtils.isEmpty(authToken)) {
            final String password = manager.getPassword(account);
            if (password != null) {
                authToken = Constants.server.signIn(account.name, password, authTokenType, context);

                if (authToken == null) {
                    Log.e("Error", "Error occurred while trying to sign in ");
                }
            }
        }

        // If we get an authToken - we return it
        if (!TextUtils.isEmpty(authToken)) {
            final Bundle result = new Bundle();
            result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name);
            result.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type);
            result.putString(AccountManager.KEY_AUTHTOKEN, authToken);
            return result;
        }

        /* If we get here, then we couldn't access the user's password - so we
           need to re-prompt them for their credentials. We do that by creating
           an intent to display our AuthenticatorActivity. */
        final Intent intent = new Intent(context, Login.class);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
        intent.putExtra(Constants.ARG_ACCOUNT_TYPE, account.type);
        intent.putExtra(Constants.ARG_AUTH_TYPE, authTokenType);
        final Bundle bundle = new Bundle();
        bundle.putParcelable(AccountManager.KEY_INTENT, intent);
        return bundle;
    }

    @Override
    public String getAuthTokenLabel(String s) {
        return null;
    }

    @Override
    public Bundle updateCredentials(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, String s, Bundle bundle) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle hasFeatures(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, String[] strings) throws NetworkErrorException {
        return null;
    }
}
