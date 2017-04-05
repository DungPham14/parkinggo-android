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
import parkinggo.com.data.model.CarTypeConf;

public class CarTypeConfRepository extends Repository {

    public CarTypeConfRepository(Realm realm) {
        super(realm);
    }

    private RealmQuery<CarTypeConf> getQueryCarTypeConfig() {
        return getRealmInstance().where(CarTypeConf.class);
    }

    public CarTypeConf getCarTypeConfig() {
        CarTypeConf user = null;
        Realm realm = getRealmInstance();
        realm.beginTransaction();
        try {
            user = getQueryCarTypeConfig().findAll().first();
            realm.commitTransaction();
        } catch (Exception e) {
            realm.cancelTransaction();
            e.printStackTrace();
        }
        return user;
    }

    public boolean saveCarTypeConfig(RealmList<CarTypeConf> carTypeConfs) {
        if (carTypeConfs == null) {
            return false;
        }
        Realm realm = getRealmInstance();
        realm.beginTransaction();
        try {
            realm.delete(CarTypeConf.class);
            realm.copyToRealm(carTypeConfs);
            realm.commitTransaction();
            return true;
        } catch (Exception e) {
            realm.cancelTransaction();
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteCarTypeConfig() {
        Realm realm = getRealmInstance();
        realm.beginTransaction();
        try {
            realm.delete(CarTypeConf.class);
            realm.commitTransaction();
            return true;
        } catch (Exception e) {
            realm.cancelTransaction();
            e.printStackTrace();
        }
        return false;
    }

}
