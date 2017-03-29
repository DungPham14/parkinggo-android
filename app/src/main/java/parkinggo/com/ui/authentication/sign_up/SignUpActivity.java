package parkinggo.com.ui.authentication.sign_up;

import android.os.Bundle;

import javax.inject.Inject;

import parkinggo.com.R;
import parkinggo.com.ui.base.BaseMVPDialogActivity;

public class SignUpActivity extends BaseMVPDialogActivity implements SignUpMvpView {

    @Inject
    SignUpPresenter presenter;

    @Override
    protected boolean bindView() {
        return true;
    }

    @Override
    protected int addContextView() {
        return R.layout.activity_sign_up;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        presenter.initialView(this);
    }


}
