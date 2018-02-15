package kesean.com.anotherweatherapp.ui.base;

import android.arch.lifecycle.LifecycleRegistry;
import android.support.v7.app.AppCompatActivity;

import kesean.com.anotherweatherapp.AndroidApplication;
import kesean.com.anotherweatherapp.data.WeatherRepositoryComponent;

/**
 * Created by Kesean on 2/14/18.
 */

public class BaseActivity extends AppCompatActivity {
    private final LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);

    public WeatherRepositoryComponent getWeatherRepositoryComponent() {
        return ((AndroidApplication) getApplication()).getWeatherRepositoryComponent();
    }

    @Override
    public LifecycleRegistry getLifecycle() {
        return lifecycleRegistry;
    }
}
