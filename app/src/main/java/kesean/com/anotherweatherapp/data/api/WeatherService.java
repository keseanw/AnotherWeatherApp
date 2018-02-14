package kesean.com.anotherweatherapp.data.api;

import io.reactivex.Flowable;
import kesean.com.anotherweatherapp.data.model.CityWeather;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Kesean on 2/2/18.
 */

public interface WeatherService {

    @GET("data/2.5/weather")
    Flowable<WeatherResponse> getWeather(@Query("q") String location);
}
