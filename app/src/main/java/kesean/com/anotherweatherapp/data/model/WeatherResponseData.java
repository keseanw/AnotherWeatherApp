package kesean.com.anotherweatherapp.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import kesean.com.anotherweatherapp.data.api.WeatherResponse;

/**
 * Created by Kesean on 2/22/18.
 */

public class WeatherResponseData {
    private WeatherResponse weatherResponse;

    public WeatherResponse getWeatherResponse() {
        return weatherResponse;
    }

    public void setWeatherResponse(WeatherResponse weatherResponse) {
        this.weatherResponse = weatherResponse;
    }
}
