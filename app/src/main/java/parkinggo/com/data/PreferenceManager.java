/*
 * ******************************************************************************
 *  Copyright Ⓒ 2017. All rights reserved
 *  Author HoanDC. Create on 04/04/2017.
 * ******************************************************************************
 */
package parkinggo.com.data;

import javax.inject.Inject;
import javax.inject.Singleton;

import parkinggo.com.data.prefs.GlobalPref;

@Singleton
public class PreferenceManager {

    private final GlobalPref mGlobalPref;

    @Inject
    public PreferenceManager(GlobalPref mGlobalPref) {
        this.mGlobalPref = mGlobalPref;
    }

    public boolean isSkipSignIn(){
        return mGlobalPref.isSkipSignIn();
    }

    public void saveSkipSignIn(boolean isSkip){
        mGlobalPref.skipSignIn(isSkip);
    }
}
