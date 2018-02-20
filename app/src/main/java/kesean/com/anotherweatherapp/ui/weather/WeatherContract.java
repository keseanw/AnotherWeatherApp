package kesean.com.anotherweatherapp.ui.weather;

import android.app.Application;
import android.content.Context;

import java.util.List;

import kesean.com.anotherweatherapp.data.model.Weather;
import kesean.com.anotherweatherapp.ui.base.BasePresenter;

/**
 * Created by Kesean on 2/14/18.
 */

public class WeatherContract {

    interface View{

        void showWeather(List<Weather> weather);

        void clearWeather();

        void showNoDataMessage();

        void showErrorMessage(String error);

        void stopLoadingIndicator();

        void showEmptySearchResult();
    }

    interface WeatherPresenter extends BasePresenter<WeatherContract.View>{

        void loadWeather(String cityName);

        void setWeatherCityName(String cityName, Context context);

        String getWeatherCityName(Context context);

    }
}
