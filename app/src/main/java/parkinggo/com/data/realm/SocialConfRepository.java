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
import parkinggo.com.data.model.SocialConf;

public class SocialConfRepository extends Repository {

    public SocialConfRepository(Realm realm) {
        super(realm);
    }

    private RealmQuery<SocialConf> getQuerySocialConf() {
        return getRealmInstance().where(SocialConf.class);
    }

    public SocialConf getSocialConf() {
        SocialConf user = null;
        Realm realm = getRealmInstance();
        realm.beginTransaction();
        try {
            user = getQuerySocialConf().findAll().first();
            realm.commitTransaction();
        } catch (Exception e) {
            realm.cancelTransaction();
            e.printStackTrace();
        }
        return user;
    }

    public boolean saveSocialConf(RealmList<SocialConf> socialConfs) {
        if (socialConfs == null) {
            return false;
        }
        Realm realm = getRealmInstance();
        realm.beginTransaction();
        try {
            realm.delete(SocialConf.class);
            realm.copyToRealm(socialConfs);
            realm.commitTransaction();
            return true;
        } catch (Exception e) {
            realm.cancelTransaction();
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteSocialConf() {
        Realm realm = getRealmInstance();
        realm.beginTransaction();
        try {
            realm.delete(SocialConf.class);
            realm.commitTransaction();
            return true;
        } catch (Exception e) {
            realm.cancelTransaction();
            e.printStackTrace();
        }
        return false;
    }

}
