package kesean.com.anotherweatherapp.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Inject;

/**
 * Created by Kesean on 2/18/18.
 */

public class AppPreferencesHelper implements PreferencesHelper {

    private static final String WEATHER_CITY_NAME = "WEATHER_CITY_NAME";
    private final SharedPreferences mPrefs;

    //@Inject
    public AppPreferencesHelper(Context context) {
        mPrefs =  PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Override
    public void setWeatherCityName(String cityName, Context context) {

    }

    @Override
    public String getWeatherCityName(Context context) {
        return null;
    }
}
