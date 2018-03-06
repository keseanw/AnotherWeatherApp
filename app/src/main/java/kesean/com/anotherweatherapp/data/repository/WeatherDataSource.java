package kesean.com.anotherweatherapp.data.repository;

import android.app.Application;
import android.content.Context;

import java.util.List;

import io.reactivex.Flowable;
import kesean.com.anotherweatherapp.data.model.CityWeather;
import kesean.com.anotherweatherapp.data.model.Weather;
import kesean.com.anotherweatherapp.data.model.WeatherResponseData;

/**
 * Created by Kesean on 2/13/18.
 */

public interface WeatherDataSource {

    Flowable<CityWeather> loadWeather(String cityName);

    void setWeatherCityName(String cityName);

    String getWeatherCityName();
}
