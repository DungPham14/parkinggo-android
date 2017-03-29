package parkinggo.com.injection.modules;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import parkinggo.com.data.networking.NetworkService;
import parkinggo.com.injection.ApplicationContext;
import retrofit2.Retrofit;

@Module
public class ApplicationModule {
    private final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideApplicationContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    Toast provideToast(@ApplicationContext Context context) {
        return Toast.makeText(context, "", Toast.LENGTH_LONG);
    }

    @Provides
    @Singleton
    NetworkService networkService(Retrofit retrofit){
        return retrofit.create(NetworkService.class);
    }

    @Provides
    @Singleton
    Retrofit provideRetrofitInstance() {
        return NetworkService.Creator.newRetrofitInstance();
    }

    @Provides
    @Singleton
    Realm provideRealm() {
        return Realm.getDefaultInstance();
    }

}
