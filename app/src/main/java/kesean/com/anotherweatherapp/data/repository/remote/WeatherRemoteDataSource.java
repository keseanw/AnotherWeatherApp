package kesean.com.anotherweatherapp.data.repository.remote;

import android.app.Application;
import android.content.Context;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import kesean.com.anotherweatherapp.data.api.WeatherResponse;
import kesean.com.anotherweatherapp.data.api.WeatherService;
import kesean.com.anotherweatherapp.data.model.Weather;
import kesean.com.anotherweatherapp.data.repository.WeatherDataSource;

/**
 * Created by Kesean on 2/13/18.
 */

public class WeatherRemoteDataSource implements WeatherDataSource {
    private WeatherService weatherService;

    @Inject
    public WeatherRemoteDataSource(WeatherService weatherService){
        this.weatherService = weatherService;
    }

    @Override
    public Flowable<List<Weather>> loadWeather(String cityName) {
        return weatherService.getWeather(cityName).map(WeatherResponse::getWeather);
    }

    /*
    * Not in Use
    * */
    @Override
    public void setWeatherCityName(String cityName) {

    }

    /*
    * Not in Use
    * */
    @Override
    public String getWeatherCityName() {
        return null;
    }
}
