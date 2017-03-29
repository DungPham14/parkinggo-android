package parkinggo.com.injection.components;

import dagger.Subcomponent;
import parkinggo.com.injection.modules.ActivityModule;
import parkinggo.com.ui.authentication.sign_in.SignInActivity;
import parkinggo.com.ui.authentication.sign_up.SignUpActivity;
import parkinggo.com.ui.main.MainActivity;

@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(SignInActivity signInActivity);

    void inject(SignUpActivity signUpActivity);

    void inject(MainActivity mainActivity);
}
