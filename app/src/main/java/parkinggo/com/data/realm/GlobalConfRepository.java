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
import parkinggo.com.data.model.GlobalConf;

public class GlobalConfRepository extends Repository {

    public GlobalConfRepository(Realm realm) {
        super(realm);
    }

    private RealmQuery<GlobalConf> getQueryGlobalConf() {
        return getRealmInstance().where(GlobalConf.class);
    }

    public GlobalConf getGlobalConf() {
        GlobalConf user = null;
        Realm realm = getRealmInstance();
        realm.beginTransaction();
        try {
            user = getQueryGlobalConf().findAll().first();
            realm.commitTransaction();
        } catch (Exception e) {
            realm.cancelTransaction();
            e.printStackTrace();
        }
        return user;
    }

    public boolean saveGlobalConf(RealmList<GlobalConf> globalConfs) {
        if (globalConfs == null) {
            return false;
        }
        Realm realm = getRealmInstance();
        realm.beginTransaction();
        try {
            realm.delete(GlobalConf.class);
            realm.copyToRealm(globalConfs);
            realm.commitTransaction();
            return true;
        } catch (Exception e) {
            realm.cancelTransaction();
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteGlobalConf() {
        Realm realm = getRealmInstance();
        realm.beginTransaction();
        try {
            realm.delete(GlobalConf.class);
            realm.commitTransaction();
            return true;
        } catch (Exception e) {
            realm.cancelTransaction();
            e.printStackTrace();
        }
        return false;
    }
}

