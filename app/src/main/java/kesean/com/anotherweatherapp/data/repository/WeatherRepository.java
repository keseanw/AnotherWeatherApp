package kesean.com.anotherweatherapp.data.repository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import kesean.com.anotherweatherapp.data.model.Weather;
import kesean.com.anotherweatherapp.data.repository.remote.WeatherRemoteDataSource;

/**
 * Created by Kesean on 2/13/18.
 */

public class WeatherRepository implements WeatherDataSource {
    private WeatherDataSource remoteDataSource;
    List<Weather> caches;

    @Inject
    public WeatherRepository(@Remote WeatherDataSource remoteDataSource){
        this.remoteDataSource = remoteDataSource;
        caches = new ArrayList<>();
    }

    @Override
    public Flowable<List<Weather>> loadWeather(String cityName) {
        return remoteDataSource.loadWeather(cityName)
                .flatMap(Flowable::fromIterable)
                .doOnNext(weather -> {
                    caches.add(weather);
                    //revisit
                    //localDataSource.addSearch(search);
                }).toList().toFlowable();
    }
}
