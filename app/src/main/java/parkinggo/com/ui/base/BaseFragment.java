/*
 * ******************************************************************************
 *  Copyright Ⓒ 2017. All rights reserved
 *  Author HoanDC. Create on 05/04/2017.
 * ******************************************************************************
 */
package parkinggo.com.ui.base;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.annotation.UiThread;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import parkinggo.com.ParkingGoApplication;
import parkinggo.com.R;
import parkinggo.com.injection.components.ActivityComponent;
import parkinggo.com.injection.components.ActivityScopeComponent;
import parkinggo.com.injection.components.DaggerActivityScopeComponent;
import parkinggo.com.injection.modules.ActivityModule;
import parkinggo.com.util.Utils;

public abstract class BaseFragment extends Fragment implements MvpView {
    protected BaseActivity mActivity;
    protected Unbinder viewUnbind;
    @Inject
    Toast mToast;
    private ActivityComponent activityComponent;
    private ActivityScopeComponent activityScopeComponent;

    private ActivityScopeComponent getActivityScopeComponent() {
        if (activityScopeComponent == null) {
            activityScopeComponent = DaggerActivityScopeComponent.builder()
                    .applicationComponent(ParkingGoApplication.get(getActivity()).getComponent())
                    .build();
        }
        return activityScopeComponent;
    }

    public final ActivityComponent getActivityComponent() {
        if (activityComponent == null) {
            activityComponent = getActivityScopeComponent()
                    .activityComponent(new ActivityModule(getActivity()));
        }
        return activityComponent;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (BaseActivity) activity;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(addContextView(), container, false);
    }

    protected abstract boolean bindView();

    protected abstract int addContextView();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (bindView()) {
            viewUnbind = ButterKnife.bind(this, view);
        }
        ParkingGoApplication.get(getActivity()).getComponent().inject(this);
    }

    @Override
    public void showNoNetworkAlert() {
        showMessage(R.string.error_not_connect_to_internet);
    }

    @MainThread
    @UiThread
    protected void showMessage(@StringRes int strRes) {
        showMessage(getString(strRes));
    }

    @MainThread
    @UiThread
    protected void showMessage(String message) {
        Toast toast = mToast;
        if (toast != null) {
            toast.setText(message);
        }
        Context context = getContext();
        if (context != null) {
            toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        }
        if (toast != null) {
            toast.show();
        }
    }

    @Override
    public boolean isConnectToInternet() {
        return Utils.isConnectivityAvailable(getActivity());
    }

    @Override
    public void onDestroyView() {
        if (viewUnbind != null) {
            viewUnbind.unbind();
        }
        super.onDestroyView();
    }

    public void onBackPressed() {
        if (getActivity().getSupportFragmentManager().getBackStackEntryCount() <= 0) {
            getActivity().finish();
        } else {
            getActivity().getSupportFragmentManager().popBackStack();
        }
    }
}
