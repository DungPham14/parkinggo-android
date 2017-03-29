package parkinggo.com.ui.authentication.sign_in;

import android.support.annotation.Nullable;

import android.os.Bundle;

import javax.inject.Inject;

import parkinggo.com.R;
import parkinggo.com.ui.base.BaseMVPDialogActivity;

public class SignInActivity extends BaseMVPDialogActivity implements SignInMvpView {

    @Inject
    SignInPresenter presenter;

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
    }
}

