/*
 * ******************************************************************************
 *  Copyright Ⓒ 2017. All rights reserved
 *  Author HoanDC. Create on 29/03/2017.
 * ******************************************************************************
 */
package parkinggo.com.data;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import parkinggo.com.data.model.GlobalConf;
import parkinggo.com.data.model.LoginResponse;
import rx.Observable;

public class DataManager {

    private NetworkManager mNetworkManager;
    private DatabaseManager mDatabaseManager;
    private PreferenceManager mPreferenceManager;

    @Inject
    public DataManager(NetworkManager mNetworkManager, DatabaseManager mDatabaseManager,
                       PreferenceManager mPreferenceManager) {
        this.mNetworkManager = mNetworkManager;
        this.mDatabaseManager = mDatabaseManager;
        this.mPreferenceManager = mPreferenceManager;
    }

    public DatabaseManager getDatabaseManager() {
        return mDatabaseManager;
    }

    public NetworkManager getNetworkManager() {
        return mNetworkManager;
    }

    public PreferenceManager getPreferenceManager() {
        return mPreferenceManager;
    }

    public Observable<LoginResponse> signIn(String userName, String password) {
        RequestBody usernameBody = RequestBody.create(MediaType.parse("text/plain"), userName);
        RequestBody passwordBody = RequestBody.create(MediaType.parse("text/plain"), password);
        return getNetworkManager().signIn(usernameBody, passwordBody);
    }

    public Observable<GlobalConf> getConfig() {
        return mNetworkManager.getConfig();
    }
}
