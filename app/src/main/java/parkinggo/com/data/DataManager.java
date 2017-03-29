/*
 * ******************************************************************************
 *  Copyright Ⓒ 2017. All rights reserved
 *  Author HoanDC. Create on 29/03/2017.
 * ******************************************************************************
 */
package parkinggo.com.data;

import javax.inject.Inject;

public class DataManager {

    private NetworkManager mNetworkManager;
    private DatabaseManager mDatabaseManager;

    @Inject
    public DataManager(NetworkManager mNetworkManager, DatabaseManager mDatabaseManager) {
        this.mNetworkManager = mNetworkManager;
        this.mDatabaseManager = mDatabaseManager;
    }

    public DatabaseManager getDatabaseManager() {
        return mDatabaseManager;
    }

    public NetworkManager getNetworkManager() {
        return mNetworkManager;
    }

}
