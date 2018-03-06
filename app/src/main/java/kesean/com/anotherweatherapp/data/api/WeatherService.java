package kesean.com.anotherweatherapp.data.api;

import java.util.List;

import io.reactivex.Flowable;
import kesean.com.anotherweatherapp.data.model.CityWeather;
import kesean.com.anotherweatherapp.data.model.Weather;
import kesean.com.anotherweatherapp.data.model.WeatherResponseData;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Kesean on 2/2/18.
 */

public interface WeatherService {

    @GET("data/2.5/weather")
    Flowable<CityWeather> getWeatherResponse(@Query("q") String location);
}
