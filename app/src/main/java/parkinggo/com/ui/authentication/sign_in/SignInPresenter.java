/*
 * ******************************************************************************
 *  Copyright Ⓒ 2017. All rights reserved
 *  Author HoanDC. Create on 29/03/2017.
 * ******************************************************************************
 */
package parkinggo.com.ui.authentication.sign_in;

import android.util.Log;

import javax.inject.Inject;

import parkinggo.com.data.DataManager;
import parkinggo.com.data.model.Token;
import parkinggo.com.data.model.User;
import parkinggo.com.ui.base.BasePresenter;
import parkinggo.com.util.StringHelper;
import retrofit2.Retrofit;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class SignInPresenter extends BasePresenter<SignInMvpView> {

    @Inject
    public SignInPresenter(Retrofit mRetrofit, DataManager mDataManager) {
        super(mRetrofit, mDataManager);
    }

    @Override
    public void initialView(SignInMvpView mvpView) {
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

    public void signIn(User user) {
        if (!isConnectToInternet()) {
            notifyNoNetwork();
            return;
        }
        mDataManager.signIn(user.getUserName(), user.getPassword())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(() -> {
                    getMvpView().showProgressDialog();
                }).doOnCompleted(() -> {
            getMvpView().dismissProgressDialog();
        }).subscribe(response -> {
            if (response.getData() != null) {
                Log.e("subscribe", "token="+response.getData().getToken());
                if (!StringHelper.isEmpty(response.getData().getToken())) {
                    mDataManager.getDatabaseManager().saveToken(new Token(response.getData().getToken()));
                }
                if (response.getData().getUser() != null) {
                    mDataManager.getDatabaseManager().saveUser(response.getData().getUser());
                }
            }
            getMvpView().navigateMainScreen();
        }, throwable -> {
            throwable.printStackTrace();
            getMvpView().dismissProgressDialog();
        });

    }

    public void skip() {
        mDataManager.getPreferenceManager().saveSkipSignIn(true);
        getMvpView().navigateMainScreen();
    }

    public void signUp() {
        getMvpView().navigateSignUp();
    }

    public void forgotPass() {
        getMvpView().navigateForgotPassword();
    }

    public void signUpByFaceBook() {

    }

    public void signUpByGoogle() {

    }

}
