/*
 * ******************************************************************************
 *  Copyright Ⓒ 2017. All rights reserved
 *  Author HoanDC. Create on 05/04/2017.
 * ******************************************************************************
 */
package parkinggo.com.ui.main.fragment;

import android.location.Location;

import parkinggo.com.ui.base.MvpView;


public interface HomeMvpView extends MvpView {

    void onLocationChanged(Location location);
}
