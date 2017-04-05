/*
 * ******************************************************************************
 *  Copyright Ⓒ 2017. All rights reserved
 *  Author HoanDC. Create on 03/04/2017.
 * ******************************************************************************
 */
package parkinggo.com.ui.splash;

import parkinggo.com.ui.base.BaseScreenMvpView;

public interface SplashMvpView extends BaseScreenMvpView {

    void loadConfigSuccess();

    void navigateSignInScreen();

    void navigateMainScreen();
}
