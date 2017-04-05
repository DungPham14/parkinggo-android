package parkinggo.com.ui.authentication.sign_in;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;

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

public class SignInActivity extends BaseActivityWithDailog implements SignInMvpView {

    @BindView(R.id.edt_user_name)
    EditText edtUserName;

    @BindView(R.id.edt_password)
    EditText edtPassword;

    @Inject
    SignInPresenter presenter;
    //Signing Options
    private GoogleSignInOptions gso;

    //google api client
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
        //Initializing google signin option
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
    }

    @OnClick({R.id.view_root, R.id.btn_sign_in, R.id.btn_skip, R.id.txt_sign_up, R.id.txt_forgot_pass,
            R.id.btn_facebook_sign_in, R.id.btn_google_sign_in})
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
            case R.id.btn_google_sign_in:
                presenter.signUpByGoogle();
                break;
        }
    }

    @Override
    public void signInByFacebook() {

    }

    @Override
    public void signInByGoogle() {

    }

    @Override
    public void navigateMainScreen() {
        Log.e("navigateMainScreen", "navigateMainScreen");
        navigateToActivity(MainActivity.class);
    }

    @Override
    public void navigateSignUp() {
        navigateToActivity(SignUpActivity.class);
    }

    @Override
    public void navigateForgotPassword() {

    }
}

