package kesean.com.anotherweatherapp.data;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Kesean on 2/20/18.
 */

@Module
public class PrefsModule {
    /*
    * Shared preferences module
    * */

    @Provides
    @Singleton
    SharedPreferences provideSharedPrefs(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

}
