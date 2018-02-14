package kesean.com.anotherweatherapp.data;

import javax.inject.Singleton;

import dagger.Component;
import kesean.com.anotherweatherapp.AppModule;
import kesean.com.anotherweatherapp.data.repository.WeatherRepository;

/**
 * Created by Kesean on 2/13/18.
 */

@Singleton
@Component(modules = { WeatherRepositoryModule.class, AppModule.class, ApiServiceModule.class})
public interface WeatherRepositoryComponent {
    WeatherRepository provideWeatherRepository();
}
