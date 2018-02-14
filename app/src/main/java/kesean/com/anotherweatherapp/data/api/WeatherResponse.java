package kesean.com.anotherweatherapp.data.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import kesean.com.anotherweatherapp.data.model.Main;
import kesean.com.anotherweatherapp.data.model.Weather;

/**
 * Created by Kesean on 2/13/18.
 */

public class WeatherResponse {

    @SerializedName("weather")
    private List<Weather> weather;

//    @SerializedName("Main")
//    private Main main;

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

}
