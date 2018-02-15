package kesean.com.anotherweatherapp.ui.weather;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Kesean on 2/14/18.
 */

@Module
public class WeatherPresenterModule {
    private WeatherContract.View view;

    public WeatherPresenterModule(WeatherContract.View view) {
        this.view = view;
    }

    @Provides
    public WeatherContract.View provideView() {
        return view;
    }
}
