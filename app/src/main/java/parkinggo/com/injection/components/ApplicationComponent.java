package parkinggo.com.injection.components;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import javax.inject.Singleton;

import dagger.Component;
import io.realm.Realm;
import parkinggo.com.data.DataManager;
import parkinggo.com.injection.ApplicationContext;
import parkinggo.com.injection.modules.ApplicationModule;
import parkinggo.com.ui.base.BaseActivity;
import retrofit2.Retrofit;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    @ApplicationContext
    Context context();

    Application application();

    Retrofit retrofit();

    DataManager dataManager();

    Realm realm();

    Toast toast();

    void inject(BaseActivity baseActivity);
}
