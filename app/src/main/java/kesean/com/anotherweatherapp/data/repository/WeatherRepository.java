package kesean.com.anotherweatherapp.data.repository;


import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import kesean.com.anotherweatherapp.data.Config;
import kesean.com.anotherweatherapp.data.model.Weather;


/**
 * Created by Kesean on 2/13/18.
 */

public class WeatherRepository implements WeatherDataSource {
    private WeatherDataSource remoteDataSource;
    private SharedPreferences preferences;

    List<Weather> caches;

    @Inject
    public WeatherRepository(SharedPreferences preferences, @Remote WeatherDataSource remoteDataSource){
        this.preferences = preferences;
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

    @Override
    public void setWeatherCityName(String cityName) {
        preferences.edit()
                .putString(Config.WEATHER_CITY_NAME, cityName)
                .apply();
    }

    @Override
    public String getWeatherCityName() {
        return preferences.getString(Config.WEATHER_CITY_NAME, "none");
    }
}
