/*
 * ******************************************************************************
 *  Copyright Ⓒ 2017. All rights reserved
 *  Author HoanDC. Create on 05/04/2017.
 * ******************************************************************************
 */
package parkinggo.com.ui.base;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;

import parkinggo.com.R;
import parkinggo.com.util.Utils;

public abstract class BaseFragmentWithDialog extends BaseFragment implements BaseScreenMvpView {
    protected MaterialDialog progressDialog, alertDialog;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        createProgressDialog();
        createAlertDialog();
    }

    @Override
    public void createProgressDialog() {
        progressDialog = new MaterialDialog.Builder(getContext())
                .content(R.string.waiting_message)
                .progress(true, 0)
                .build();
    }

    @Override
    public void showProgressDialog() {
        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    @Override
    public void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void createAlertDialog() {
        alertDialog = new MaterialDialog.Builder(getContext())
                .title(R.string.title_dialog)
                .positiveText(R.string.action_ok)
                .build();
    }

    @Override
    public void showAlertDialog(String errorMessage) {
        alertDialog.setContent(errorMessage);
        alertDialog.show();
    }

    @Override
    public void showAlertDialog(@StringRes int resourceId) {
        alertDialog.setContent(resourceId);
        alertDialog.show();
    }

    @Override
    public void showAlert(String message) {
        showMessage(message);
    }

    @Override
    public void showAlert(@StringRes int resourceId) {
        showMessage(resourceId);
    }

    @Override
    public void showNoNetworkAlert() {
        showAlertDialog(getString(R.string.error_not_connect_to_internet));
    }

    @Override
    public boolean isConnectToInternet() {
        return Utils.isConnectivityAvailable(getActivity());
    }

    @Override
    public void onDestroyView() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
        super.onDestroyView();
    }
}
