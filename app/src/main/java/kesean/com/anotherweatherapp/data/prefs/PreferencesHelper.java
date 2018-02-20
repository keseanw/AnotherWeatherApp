package kesean.com.anotherweatherapp.data.prefs;

import android.app.Application;
import android.content.Context;

/**
 * Created by Kesean on 2/18/18.
 */

public interface PreferencesHelper {

    void setWeatherCityName(String cityName, Context context);

    String getWeatherCityName(Context context);
}
