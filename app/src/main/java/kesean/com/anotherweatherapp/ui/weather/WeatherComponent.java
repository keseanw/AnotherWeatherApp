package kesean.com.anotherweatherapp.ui.weather;

import dagger.Component;
import kesean.com.anotherweatherapp.data.WeatherRepositoryComponent;
import kesean.com.anotherweatherapp.ui.base.ActivityScope;
import kesean.com.anotherweatherapp.util.SchedulerModule;

/**
 * Created by Kesean on 2/14/18.
 */

@ActivityScope
@Component(modules = {WeatherPresenterModule.class, SchedulerModule.class}, dependencies = {
        WeatherRepositoryComponent.class
})
public interface WeatherComponent {

    void inject(MainActivity mainActivity);

}
