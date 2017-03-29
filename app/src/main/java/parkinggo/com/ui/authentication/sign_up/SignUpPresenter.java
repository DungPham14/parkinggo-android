/*
 * ******************************************************************************
 *  Copyright Ⓒ 2017. All rights reserved
 *  Author HoanDC. Create on 29/03/2017.
 * ******************************************************************************
 */
package parkinggo.com.ui.authentication.sign_up;

import javax.inject.Inject;

import parkinggo.com.data.DataManager;
import parkinggo.com.ui.base.BasePresenter;
import retrofit2.Retrofit;


public class SignUpPresenter extends BasePresenter<SignUpMvpView> {

    @Inject
    public SignUpPresenter(Retrofit mRetrofit, DataManager mDataManager) {
        super(mRetrofit, mDataManager);
    }

    @Override
    public void initialView(SignUpMvpView mvpView) {
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
