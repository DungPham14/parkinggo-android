/*
 * ******************************************************************************
 *  Copyright Ⓒ 2017. All rights reserved
 *  Author HoanDC. Create on 03/04/2017.
 * ******************************************************************************
 */
package parkinggo.com.ui.splash;

import android.support.annotation.Nullable;
import android.os.Bundle;

import javax.inject.Inject;

import parkinggo.com.R;
import parkinggo.com.ui.authentication.sign_in.SignInActivity;
import parkinggo.com.ui.base.BaseActivityWithDailog;
import parkinggo.com.ui.main.MainActivity;

public class SplashActivity extends BaseActivityWithDailog implements SplashMvpView {

    @Inject
    SplashPresenter presenter;

    @Override
    protected boolean bindView() {
        return false;
    }

    @Override
    protected int addContextView() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        presenter.initialView(this);
        presenter.getConfigData();
    }

    @Override
    public void loadConfigSuccess() {
        presenter.navigateScreen();
    }

    @Override
    public void navigateSignInScreen() {
        navigateToActivity(SignInActivity.class);
        finish();
    }

    @Override
    public void navigateMainScreen() {
        navigateToActivity(MainActivity.class);
        finish();
    }
}
