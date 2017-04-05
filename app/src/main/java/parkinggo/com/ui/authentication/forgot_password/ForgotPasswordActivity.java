/*
 * ******************************************************************************
 *  Copyright Ⓒ 2017. All rights reserved
 *  Author HoanDC. Create on 05/04/2017.
 * ******************************************************************************
 */
package parkinggo.com.ui.authentication.forgot_password;

import android.os.Bundle;

import javax.inject.Inject;

import parkinggo.com.R;
import parkinggo.com.ui.base.BaseActivityWithDailog;

public class ForgotPasswordActivity extends BaseActivityWithDailog implements ForgotPasswordMvpView {

    @Inject
    ForgotPasswordPresenter presenter;

    @Override
    protected boolean bindView() {
        return true;
    }

    @Override
    protected int addContextView() {
        return R.layout.activity_forgot_password;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        presenter.initialView(this);
    }
}
