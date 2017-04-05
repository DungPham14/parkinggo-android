/*
 * ******************************************************************************
 *  Copyright Ⓒ 2017. All rights reserved
 *  Author HoanDC. Create on 03/04/2017.
 * ******************************************************************************
 */
package parkinggo.com.data.realm;


import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmQuery;
import parkinggo.com.data.model.ParkingTypeConf;

public class ParkingTypeConfRepository extends Repository {

    public ParkingTypeConfRepository(Realm realm) {
        super(realm);
    }

    private RealmQuery<ParkingTypeConf> getQueryParkingTypeConf() {
        return getRealmInstance().where(ParkingTypeConf.class);
    }

    public ParkingTypeConf getParkingTypeConf() {
        ParkingTypeConf user = null;
        Realm realm = getRealmInstance();
        realm.beginTransaction();
        try {
            user = getQueryParkingTypeConf().findAll().first();
            realm.commitTransaction();
        } catch (Exception e) {
            realm.cancelTransaction();
            e.printStackTrace();
        }
        return user;
    }

    public boolean saveParkingTypeConf(RealmList<ParkingTypeConf> parkingTypeConfs) {
        if (parkingTypeConfs == null) {
            return false;
        }
        Realm realm = getRealmInstance();
        realm.beginTransaction();
        try {
            realm.delete(ParkingTypeConf.class);
            realm.copyToRealm(parkingTypeConfs);
            realm.commitTransaction();
            return true;
        } catch (Exception e) {
            realm.cancelTransaction();
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteParkingTypeConf() {
        Realm realm = getRealmInstance();
        realm.beginTransaction();
        try {
            realm.delete(ParkingTypeConf.class);
            realm.commitTransaction();
            return true;
        } catch (Exception e) {
            realm.cancelTransaction();
            e.printStackTrace();
        }
        return false;
    }
}
