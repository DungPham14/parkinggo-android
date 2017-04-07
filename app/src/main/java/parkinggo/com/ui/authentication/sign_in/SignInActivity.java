package parkinggo.com.ui.authentication.sign_in;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import parkinggo.com.R;
import parkinggo.com.data.model.User;
import parkinggo.com.ui.authentication.sign_up.SignUpActivity;
import parkinggo.com.ui.base.BaseActivityWithDailog;
import parkinggo.com.ui.main.MainActivity;
import parkinggo.com.util.StringHelper;
import parkinggo.com.util.Utils;

public class SignInActivity extends BaseActivityWithDailog implements SignInMvpView, GoogleApiClient.OnConnectionFailedListener {

    private final int RC_SIGN_IN = 100;
    @BindView(R.id.edt_user_name)
    EditText edtUserName;

    @BindView(R.id.edt_password)
    EditText edtPassword;

    @BindView(R.id.btn_facebook_sign_in)
    LoginButton loginButton;

    @Inject
    SignInPresenter presenter;
    // Face book
    CallbackManager callbackManager;
    //Signing Options
    private GoogleSignInOptions mGoogleSignInOptions;
    // Google api client
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected boolean bindView() {
        return true;
    }

    @Override
    protected int addContextView() {
        return R.layout.activity_sign_in;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        presenter.initialView(this);
        initial();
    }

    private void initial() {
        initGoogle();
        initFacebook();
    }

    private void initGoogle() {
        mGoogleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestId()
                .requestProfile()
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, mGoogleSignInOptions)
                .build();
    }

    private void initFacebook() {
        callbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions(Arrays.asList("public_profile, email, user_birthday, user_friends"));
        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                showProgressDialog();
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                dismissProgressDialog();
                                // Application code
                                Log.e("onCompleted", "object=" + object.toString());
                                try {
                                    String id = object.getString("id");
                                    String email = object.getString("email");
                                    String firstName = object.getString("first_name");
                                    String lastName = object.getString("last_name");
                                    String avatar = "http://graph.facebook.com/" + id + "/picture?type=large";
//                                    UserModel userModel = new UserModel(id, firstName, lastName, email, "", avatar);
//                                    loginSocial(userModel, id, "facebook");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, email, first_name, last_name, name, gender, birthday");
                request.setParameters(parameters);
                request.executeAsync();

            }

            @Override
            public void onCancel() {
                dismissProgressDialog();
            }

            @Override
            public void onError(FacebookException exception) {
                exception.printStackTrace();
                dismissProgressDialog();
            }
        });
    }

    @OnClick({R.id.view_root, R.id.btn_sign_in, R.id.btn_skip, R.id.txt_sign_up, R.id.txt_forgot_pass,
            R.id.btn_facebook_sign_in, R.id.btn_google})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.view_root:
                Utils.hideKeyboardMachine(this);
                break;
            case R.id.btn_sign_in:
                String userName = edtUserName.getText().toString();
                String password = edtPassword.getText().toString();
                if (StringHelper.isEmpty(userName)) {
                    edtUserName.requestFocus();
                    edtUserName.setError(getString(R.string.error_invalid_user_name));
                    return;
                }
                if (StringHelper.isEmpty(password)) {
                    edtPassword.requestFocus();
                    edtPassword.setError(getString(R.string.error_incorrect_password));
                    return;
                }
                User user = new User();
                user.setUserName(userName);
                user.setPassword(password);
                presenter.signIn(user);
                break;
            case R.id.btn_skip:
                presenter.skip();
                break;
            case R.id.txt_sign_up:
                presenter.signUp();
                break;
            case R.id.txt_forgot_pass:
                presenter.forgotPass();
                break;
            case R.id.btn_facebook_sign_in:
                presenter.signUpByFaceBook();
                break;
            case R.id.btn_google:
                presenter.signUpByGoogle();
                break;
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    public void signInByFacebook() {
        LoginManager.getInstance().logOut();
        loginButton.performClick();
    }

    @Override
    public void signInByGoogle() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void navigateMainScreen() {
        navigateToActivity(MainActivity.class);
    }

    @Override
    public void navigateSignUp() {
        navigateToActivity(SignUpActivity.class);
    }

    @Override
    public void navigateForgotPassword() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            Log.e("handleSignInResult", "Id=" + acct.getId());
            Log.e("handleSignInResult", "email=" + acct.getEmail());
            Log.e("handleSignInResult", "avatar=" + acct.getPhotoUrl());
            Log.e("handleSignInResult", "username=" + acct.getAccount().name);
            Log.e("handleSignInResult", "getDisplayName=" + acct.getDisplayName());

        } else {
            // Signed out, show unauthenticated UI.
        }
    }

}

