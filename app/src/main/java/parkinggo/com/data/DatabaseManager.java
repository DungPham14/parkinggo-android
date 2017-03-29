/*
 * ******************************************************************************
 *  Copyright Ⓒ 2017. All rights reserved
 *  Author HoanDC. Create on 29/03/2017.
 * ******************************************************************************
 */
package parkinggo.com.data;

import javax.inject.Inject;

import io.realm.Realm;

public class DatabaseManager {
    private final Realm mRealm;

    @Inject
    public DatabaseManager(Realm realm) {
        this.mRealm = realm;
    }
}
