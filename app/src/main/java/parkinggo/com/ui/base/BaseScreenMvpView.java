package parkinggo.com.ui.base;

import android.support.annotation.StringRes;

public interface BaseScreenMvpView extends MvpView{

    void createProgressDialog();

    void createAlertDialog();

    void showProgressDialog();

    void showAlertDialog(@StringRes int resourceId);

    void showAlertDialog(String errorMessage);

    void dismissProgressDialog();

    void showAlert(String s);

    void showAlert(@StringRes int resourceId);

    void showNoNetworkAlert();

    boolean isConnectToInternet();
}
