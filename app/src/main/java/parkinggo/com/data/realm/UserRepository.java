/*
 * ******************************************************************************
 *  Copyright Ⓒ 2017. All rights reserved
 *  Author HoanDC. Create on 04/04/2017.
 * ******************************************************************************
 */
package parkinggo.com.data.realm;

import io.realm.Realm;
import io.realm.RealmQuery;
import parkinggo.com.data.model.User;

public class UserRepository extends Repository {

    public UserRepository(Realm realm) {
        super(realm);
    }

    private RealmQuery<User> getQueryUser() {
        return getRealmInstance().where(User.class);
    }

    public User getUser() {
        User user = null;
        Realm realm = getRealmInstance();
        realm.beginTransaction();
        try {
            user = getQueryUser().findAll().first();
            realm.commitTransaction();
        } catch (Exception e) {
            realm.cancelTransaction();
            e.printStackTrace();
        }
        return user;
    }

    public boolean save(User user) {
        if (user == null) {
            return false;
        }
        Realm realm = getRealmInstance();
        realm.beginTransaction();
        try {
            realm.delete(User.class);
            realm.copyToRealm(user);
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
            realm.delete(User.class);
            realm.commitTransaction();
            return true;
        } catch (Exception e) {
            realm.cancelTransaction();
            e.printStackTrace();
        }
        return false;
    }
}
