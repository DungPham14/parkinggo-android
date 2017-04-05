/*
 * ******************************************************************************
 *  Copyright Ⓒ 2017. All rights reserved
 *  Author HoanDC. Create on 03/04/2017.
 * ******************************************************************************
 */
package parkinggo.com.ui.splash;

import javax.inject.Inject;

import parkinggo.com.data.DataManager;
import parkinggo.com.data.model.Token;
import parkinggo.com.ui.base.BasePresenter;
import parkinggo.com.util.StringHelper;
import retrofit2.Retrofit;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class SplashPresenter extends BasePresenter<SplashMvpView> {

    @Inject
    public SplashPresenter(Retrofit mRetrofit, DataManager mDataManager) {
        super(mRetrofit, mDataManager);
    }

    @Override
    public void initialView(SplashMvpView mvpView) {
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


    public void getConfigData() {
        if (!isConnectToInternet()) {
            notifyNoNetwork();
            return;
        }
        mDataManager.getConfig()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(() -> {
                    getMvpView().showProgressDialog();
                }).doOnCompleted(() -> {
            getMvpView().dismissProgressDialog();
        }).subscribe(response -> {
            mDataManager.getDatabaseManager().saveConfig(response);
            getMvpView().loadConfigSuccess();
        }, throwable -> {
            throwable.printStackTrace();
            getMvpView().dismissProgressDialog();
        });
    }

    public void navigateScreen() {
        Token token = mDataManager.getDatabaseManager().getToken();
        if (token != null) {
            if (StringHelper.isEmpty(token.getToken())) {
                getMvpView().navigateSignInScreen();
            } else {
                getMvpView().navigateMainScreen();
            }
        } else {
            boolean isSkipSignIn = mDataManager.getPreferenceManager().isSkipSignIn();
            if (isSkipSignIn) {
                getMvpView().navigateMainScreen();
            } else {
                getMvpView().navigateSignInScreen();
            }
        }
    }
}
