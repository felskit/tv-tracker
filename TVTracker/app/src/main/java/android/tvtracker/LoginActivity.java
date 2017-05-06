package android.tvtracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.tvtracker.controllers.LoginController;
import android.tvtracker.interfaces.ILoginActivity;
import android.tvtracker.models.UserId;
import android.view.View;
import android.widget.RelativeLayout;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity implements ILoginActivity {
    private CallbackManager mCallbackManager;
    private RelativeLayout mOverlay;
    private Intent mHomeIntent;
    private Boolean processed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        setContentView(R.layout.activity_login);

        mOverlay = (RelativeLayout) findViewById(R.id.overlay);
        mCallbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email"));
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                processToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                error.printStackTrace();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        AccessToken token = AccessToken.getCurrentAccessToken();
        if (token != null) {
            processToken(token);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void processToken(AccessToken token) {
        if (!processed) {
            processed = true;
        } else {
            return;
        }

        final ILoginActivity loginActivity = this;
        mOverlay.setVisibility(View.VISIBLE);
        GraphRequest request = GraphRequest.newMeRequest(token, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    final String facebookId = object.getString("id");
                    mHomeIntent = new Intent(LoginActivity.this, MainActivity.class);
                    mHomeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
                    mHomeIntent.putExtra("fbUserId", facebookId);
                    mHomeIntent.putExtra("userName", object.getString("name"));
                    mHomeIntent.putExtra("email", object.getString("email"));
                    UserId userId = new UserId(facebookId, null);
                    LoginController loginController = new LoginController(loginActivity);
                    loginController.start();
                    loginController.login(userId);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email");
        request.setParameters(parameters);
        request.executeAsync();
    }

    @Override
    public void redirect(int userId) {
        mHomeIntent.putExtra("userId", userId);
        setResult(RESULT_OK, mHomeIntent);
        finish();
    }
}
