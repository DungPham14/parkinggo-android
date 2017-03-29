package parkinggo.com.injection.components;

import dagger.Component;
import parkinggo.com.injection.PerActivity;
import parkinggo.com.injection.modules.ActivityModule;

@PerActivity
@Component(dependencies = ApplicationComponent.class)
public interface ActivityScopeComponent {

    ActivityComponent activityComponent(ActivityModule module);
}
