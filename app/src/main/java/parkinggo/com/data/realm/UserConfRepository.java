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
import parkinggo.com.data.model.UserConf;

public class UserConfRepository extends Repository {

    public UserConfRepository(Realm realm) {
        super(realm);
    }

    private RealmQuery<UserConf> getQueryUserConf() {
        return getRealmInstance().where(UserConf.class);
    }

    public UserConf getUserConf() {
        UserConf user = null;
        Realm realm = getRealmInstance();
        realm.beginTransaction();
        try {
            user = getQueryUserConf().findAll().first();
            realm.commitTransaction();
        } catch (Exception e) {
            realm.cancelTransaction();
            e.printStackTrace();
        }
        return user;
    }

    public boolean saveUserConf(RealmList<UserConf> userConfs) {
        if (userConfs == null) {
            return false;
        }
        Realm realm = getRealmInstance();
        realm.beginTransaction();
        try {
            realm.delete(UserConf.class);
            realm.copyToRealm(userConfs);
            realm.commitTransaction();
            return true;
        } catch (Exception e) {
            realm.cancelTransaction();
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteUserConf() {
        Realm realm = getRealmInstance();
        realm.beginTransaction();
        try {
            realm.delete(UserConf.class);
            realm.commitTransaction();
            return true;
        } catch (Exception e) {
            realm.cancelTransaction();
            e.printStackTrace();
        }
        return false;
    }
}
