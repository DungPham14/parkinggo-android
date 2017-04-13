/*
 * ******************************************************************************
 *  Copyright Ⓒ 2017. All rights reserved
 *  Author HoanDC. Create on 05/04/2017.
 * ******************************************************************************
 */
package parkinggo.com.ui.main.fragment;

import android.location.Location;

import io.realm.RealmList;
import parkinggo.com.data.model.Parking;
import parkinggo.com.ui.base.BaseScreenMvpView;
import parkinggo.com.ui.base.MvpView;


public interface HomeMvpView extends BaseScreenMvpView {

    void onLocationChanged(Location location);

    void makeMarker(RealmList<Parking> parkings);
}
