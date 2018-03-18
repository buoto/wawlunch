package kantoniak.com.wawlunch;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.w3c.dom.Text;

public class AccountActivity extends Activity {

    private static final String TAG = AccountActivity.class.getSimpleName();

    private View loggedInView;
    private View notLoggedInView;
    private TextView loggedInMessageTextView;

    private final CallbackManager callbackManager = CallbackManager.Factory.create();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        super.setTitle("Konto");

        loggedInView = findViewById(R.id.facebook_logged_in);
        notLoggedInView = findViewById(R.id.facebook_not_logged_in);
        loggedInMessageTextView = findViewById(R.id.logged_in_message);

        LoginManager.getInstance().registerCallback(callbackManager,
            new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    updateScreens();
                }

                @Override
                public void onCancel() {
                    updateScreens();
                }

                @Override
                public void onError(FacebookException error) {
                    updateScreens();
                }
            });

        updateScreens();
    }

    private void updateScreens() {
        if (isLoggedIn()) {
            loggedInView.setVisibility(View.VISIBLE);
            notLoggedInView.setVisibility(View.GONE);
            loggedInMessageTextView.setText("Facebook ID: " + Profile.getCurrentProfile().getId());
        } else {
            loggedInView.setVisibility(View.GONE);
            notLoggedInView.setVisibility(View.VISIBLE);
        }
    }

    private boolean isLoggedIn() {
        AccessToken token = AccessToken.getCurrentAccessToken();
        if (token == null) {
            return false;
        }
        Log.i(TAG, "Facebook Access Token: " + token.getToken());
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

}
