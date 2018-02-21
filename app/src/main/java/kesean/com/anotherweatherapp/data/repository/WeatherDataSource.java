package kesean.com.anotherweatherapp.data.repository;

import android.app.Application;
import android.content.Context;

import java.util.List;

import io.reactivex.Flowable;
import kesean.com.anotherweatherapp.data.model.Weather;

/**
 * Created by Kesean on 2/13/18.
 */

public interface WeatherDataSource {

    Flowable<List<Weather>> loadWeather(String cityName);

    void setWeatherCityName(String cityName);

    String getWeatherCityName();
}
