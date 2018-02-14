package kesean.com.anotherweatherapp;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Kesean on 2/13/18.
 */

@Module
public class AppModule {

    private Context context;

    public AppModule(Application context) {
        this.context = context;
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return context;
    }
}
