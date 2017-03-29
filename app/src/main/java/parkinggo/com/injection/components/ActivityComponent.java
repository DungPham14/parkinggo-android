package parkinggo.com.injection.components;

import dagger.Subcomponent;
import parkinggo.com.injection.modules.ActivityModule;
import parkinggo.com.ui.authen.login.LoginActivity;

@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(LoginActivity loginActivity);
}
