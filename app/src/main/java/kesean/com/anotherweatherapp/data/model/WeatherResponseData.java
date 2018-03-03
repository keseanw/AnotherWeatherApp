package kesean.com.anotherweatherapp.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Kesean on 2/22/18.
 */

public class WeatherResponseData {
    @SerializedName("weather")
    private List<Weather> weather;

    @SerializedName("main")
    private Main main;

    public List<Weather> getWeatherList() {
        return weather;
    }

    public void setWeatherList(List<Weather> weather) {
        this.weather = weather;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }


}
