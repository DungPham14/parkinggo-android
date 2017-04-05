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
import parkinggo.com.data.model.SlotConf;

public class SlotConfRepository extends Repository {

    public SlotConfRepository(Realm realm) {
        super(realm);
    }

    private RealmQuery<SlotConf> getQuerySlotConf() {
        return getRealmInstance().where(SlotConf.class);
    }

    public SlotConf getSlotConf() {
        SlotConf user = null;
        Realm realm = getRealmInstance();
        realm.beginTransaction();
        try {
            user = getQuerySlotConf().findAll().first();
            realm.commitTransaction();
        } catch (Exception e) {
            realm.cancelTransaction();
            e.printStackTrace();
        }
        return user;
    }

    public boolean saveSlotConf(RealmList<SlotConf> slotConfs) {
        if (slotConfs == null) {
            return false;
        }
        Realm realm = getRealmInstance();
        realm.beginTransaction();
        try {
            realm.delete(SlotConf.class);
            realm.copyToRealm(slotConfs);
            realm.commitTransaction();
            return true;
        } catch (Exception e) {
            realm.cancelTransaction();
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteSlotConf() {
        Realm realm = getRealmInstance();
        realm.beginTransaction();
        try {
            realm.delete(SlotConf.class);
            realm.commitTransaction();
            return true;
        } catch (Exception e) {
            realm.cancelTransaction();
            e.printStackTrace();
        }
        return false;
    }
}
