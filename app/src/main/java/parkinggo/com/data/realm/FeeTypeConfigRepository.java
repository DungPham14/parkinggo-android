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
import parkinggo.com.data.model.FeeTypeConf;

public class FeeTypeConfigRepository extends Repository {

    public FeeTypeConfigRepository(Realm realm) {
        super(realm);
    }

    private RealmQuery<FeeTypeConf> getQueryFreeTypeConfig() {
        return getRealmInstance().where(FeeTypeConf.class);
    }

    public FeeTypeConf getFreeTypeConfig() {
        FeeTypeConf user = null;
        Realm realm = getRealmInstance();
        realm.beginTransaction();
        try {
            user = getQueryFreeTypeConfig().findAll().first();
            realm.commitTransaction();
        } catch (Exception e) {
            realm.cancelTransaction();
            e.printStackTrace();
        }
        return user;
    }

    public boolean saveFreeTypeConfig(RealmList<FeeTypeConf> feeTypeConfs) {
        if (feeTypeConfs == null) {
            return false;
        }
        Realm realm = getRealmInstance();
        realm.beginTransaction();
        try {
            realm.delete(FeeTypeConf.class);
            realm.copyToRealm(feeTypeConfs);
            realm.commitTransaction();
            return true;
        } catch (Exception e) {
            realm.cancelTransaction();
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteFreeTypeConfig() {
        Realm realm = getRealmInstance();
        realm.beginTransaction();
        try {
            realm.delete(FeeTypeConf.class);
            realm.commitTransaction();
            return true;
        } catch (Exception e) {
            realm.cancelTransaction();
            e.printStackTrace();
        }
        return false;
    }
}
