/*
 * ******************************************************************************
 *  Copyright Ⓒ 2017. All rights reserved
 *  Author HoanDC. Create on 03/04/2017.
 * ******************************************************************************
 */
package parkinggo.com.data.realm;

import io.realm.Realm;

public abstract class Repository {

    private final Realm realm;

    public Repository(Realm realm) {
        this.realm = realm;
    }

    protected Realm getRealmInstance() {
        return realm;
    }
}
