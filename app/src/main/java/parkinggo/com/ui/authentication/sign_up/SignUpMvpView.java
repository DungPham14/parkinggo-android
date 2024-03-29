/*
 * ******************************************************************************
 *  Copyright Ⓒ 2017. All rights reserved
 *  Author HoanDC. Create on 29/03/2017.
 * ******************************************************************************
 */
package parkinggo.com.ui.authentication.sign_up;

import android.support.annotation.StringRes;

import io.realm.RealmResults;
import parkinggo.com.data.model.UserConf;
import parkinggo.com.ui.base.BaseScreenMvpView;


public interface SignUpMvpView extends BaseScreenMvpView {

    void showUserType(RealmResults<UserConf> userConfs);

    void changeButtonText(@StringRes int strId);

    void navigateSignUpDriver();

    void navigateSignRenter();
}
