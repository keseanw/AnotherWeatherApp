package kesean.com.anotherweatherapp.data;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import kesean.com.anotherweatherapp.data.repository.Remote;
import kesean.com.anotherweatherapp.data.repository.WeatherDataSource;
import kesean.com.anotherweatherapp.data.repository.remote.WeatherRemoteDataSource;

/**
 * Created by Kesean on 2/13/18.
 */

@Module
public class WeatherRepositoryModule {

    /*
    * Remote data source for API calls
    * */

    @Provides
    @Remote
    @Singleton
    public WeatherDataSource provideRemoteDataSource(WeatherRemoteDataSource weatherRemoteDataSource) {
        return weatherRemoteDataSource;
    }
}
