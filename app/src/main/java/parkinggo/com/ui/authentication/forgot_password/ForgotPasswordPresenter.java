/*
 * ******************************************************************************
 *  Copyright Ⓒ 2017. All rights reserved
 *  Author HoanDC. Create on 05/04/2017.
 * ******************************************************************************
 */
package parkinggo.com.ui.authentication.forgot_password;

import javax.inject.Inject;

import parkinggo.com.data.DataManager;
import parkinggo.com.ui.base.BasePresenter;
import retrofit2.Retrofit;


public class ForgotPasswordPresenter extends BasePresenter<ForgotPasswordMvpView> {

    @Inject
    public ForgotPasswordPresenter(Retrofit mRetrofit, DataManager mDataManager) {
        super(mRetrofit, mDataManager);
    }

    @Override
    public void initialView(ForgotPasswordMvpView mvpView) {
        super.initialView(mvpView);
    }

    @Override
    public void destroyView() {
        super.destroyView();
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
