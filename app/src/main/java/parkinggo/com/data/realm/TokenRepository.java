/*
 * ******************************************************************************
 *  Copyright Ⓒ 2017. All rights reserved
 *  Author HoanDC. Create on 04/04/2017.
 * ******************************************************************************
 */
package parkinggo.com.data.realm;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import parkinggo.com.data.model.Token;

public class TokenRepository extends Repository {

    public TokenRepository(Realm realm) {
        super(realm);
    }

    public RealmQuery<Token> getTokenQuery() {
        return getRealmInstance().where(Token.class);
    }

    public Token getToken() {
        Token token = null;
        Realm realm = getRealmInstance();
        realm.beginTransaction();
        try {
            RealmResults<Token> tokens = getTokenQuery().findAll();
            if (tokens.size() > 0) {
                token = getTokenQuery().findAll().first();
            }
            realm.commitTransaction();
        } catch (Exception e) {
            realm.cancelTransaction();
            e.printStackTrace();
        }
        return token;
    }

    public boolean save(Token token) {
        if (token == null) {
            return false;
        }
        Realm realm = getRealmInstance();
        realm.beginTransaction();
        try {
            realm.delete(Token.class);
            realm.copyToRealm(token);
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
            realm.delete(Token.class);
            realm.commitTransaction();
            return true;
        } catch (Exception e) {
            realm.cancelTransaction();
            e.printStackTrace();
        }
        return false;
    }
}

