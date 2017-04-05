/*
 * ******************************************************************************
 *  Copyright Ⓒ 2017. All rights reserved
 *  Author HoanDC. Create on 05/04/2017.
 * ******************************************************************************
 */
package parkinggo.com.ui.main.fragment;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationProvider;
import android.os.Bundle;

import javax.inject.Inject;

import parkinggo.com.data.DataManager;
import parkinggo.com.ui.base.BasePresenter;
import parkinggo.com.ui.base.MvpView;
import retrofit2.Retrofit;


public class HomePresenter extends BasePresenter<HomeMvpView> implements LocationListener {

    @Inject
    public HomePresenter(Retrofit mRetrofit, DataManager mDataManager) {
        super(mRetrofit, mDataManager);
    }

    @Override
    public void initialView(HomeMvpView mvpView) {
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

    @Override
    public void onLocationChanged(Location location) {
        getMvpView().onLocationChanged(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
