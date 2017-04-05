/*
 * ******************************************************************************
 *  Copyright Ⓒ 2017. All rights reserved
 *  Author HoanDC. Create on 29/03/2017.
 * ******************************************************************************
 */
package parkinggo.com;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import parkinggo.com.constants.Constants;
import parkinggo.com.injection.components.ApplicationComponent;
import parkinggo.com.injection.components.DaggerApplicationComponent;
import parkinggo.com.injection.modules.ApplicationModule;
import parkinggo.com.util.font.FontUtils;


public class ParkingGoApplication extends Application {

    private final Object lock = new Object();
    private ApplicationComponent mApplicationComponent;

    public static ParkingGoApplication get(Context context) {
        return (ParkingGoApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        FontUtils.initFont(this);
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .schemaVersion(Constants.DATABASE_VERSION)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                        .build());
    }

    public ApplicationComponent getComponent() {
        if (mApplicationComponent == null) {
            synchronized (lock) {
                if (mApplicationComponent == null) {
                    mApplicationComponent = DaggerApplicationComponent.builder()
                            .applicationModule(new ApplicationModule(this))
                            .build();
                }
            }
        }
        return mApplicationComponent;
    }
}
