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
import parkinggo.com.data.model.CurrencyConf;

public class CurrencyConfRepository extends Repository {

    public CurrencyConfRepository(Realm realm) {
        super(realm);
    }

    private RealmQuery<CurrencyConf> getQueryCurrencyConfig() {
        return getRealmInstance().where(CurrencyConf.class);
    }

    public CurrencyConf getCurrencyConf() {
        CurrencyConf user = null;
        Realm realm = getRealmInstance();
        realm.beginTransaction();
        try {
            user = getQueryCurrencyConfig().findAll().first();
            realm.commitTransaction();
        } catch (Exception e) {
            realm.cancelTransaction();
            e.printStackTrace();
        }
        return user;
    }

    public boolean saveCurrencyConf(RealmList<CurrencyConf> currencyConfs) {
        if (currencyConfs == null) {
            return false;
        }
        Realm realm = getRealmInstance();
        realm.beginTransaction();
        try {
            realm.delete(CurrencyConf.class);
            realm.copyToRealm(currencyConfs);
            realm.commitTransaction();
            return true;
        } catch (Exception e) {
            realm.cancelTransaction();
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteCurrencyConf() {
        Realm realm = getRealmInstance();
        realm.beginTransaction();
        try {
            realm.delete(CurrencyConf.class);
            realm.commitTransaction();
            return true;
        } catch (Exception e) {
            realm.cancelTransaction();
            e.printStackTrace();
        }
        return false;
    }
}
