package kesean.com.anotherweatherapp.data.repository.local;

import android.app.Application;
import android.content.Context;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import kesean.com.anotherweatherapp.data.model.Weather;
import kesean.com.anotherweatherapp.data.prefs.PreferencesHelper;
import kesean.com.anotherweatherapp.data.repository.WeatherDataSource;

/**
 * Created by Kesean on 2/19/18.
 */

public class WeatherLocalDataSource implements WeatherDataSource {

    private PreferencesHelper sharedPrefs;

    @Inject
    public WeatherLocalDataSource(PreferencesHelper sharedPrefs) {
        this.sharedPrefs = sharedPrefs;
    }

    /*
    * Not in use
    * */
    @Override
    public Flowable<List<Weather>> loadWeather(String cityName) {
        return null;
    }

    @Override
    public void setWeatherCityName(String cityName, Context context) {
        sharedPrefs.setWeatherCityName(cityName, context);
    }

    @Override
    public String getWeatherCityName(Context context) {
        return sharedPrefs.getWeatherCityName(context);
    }
}
