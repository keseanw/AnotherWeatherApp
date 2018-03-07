package kesean.com.anotherweatherapp.ui.weather;

import android.app.Application;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import kesean.com.anotherweatherapp.R;
import kesean.com.anotherweatherapp.data.Config;
import kesean.com.anotherweatherapp.data.model.CityWeather;
import kesean.com.anotherweatherapp.data.model.Weather;
import kesean.com.anotherweatherapp.data.model.WeatherResponseData;
import kesean.com.anotherweatherapp.data.repository.WeatherRepository;
import kesean.com.anotherweatherapp.util.RunOn;
import kesean.com.anotherweatherapp.util.SchedulerType;

/**
 * Created by Kesean on 2/15/18.
 */

public class WeatherPresenter implements WeatherContract.WeatherPresenter, LifecycleObserver {

    private WeatherRepository repository;

    private WeatherContract.View view;

    private Scheduler ioScheduler;
    private Scheduler uiScheduler;

    private CompositeDisposable disposeBag;

    @Inject
    public WeatherPresenter(WeatherRepository repository, WeatherContract.View view,
                            @RunOn(SchedulerType.IO) Scheduler ioScheduler, @RunOn(SchedulerType.UI) Scheduler uiScheduler){
        this.repository = repository;
        this.view = view;
        this.ioScheduler = ioScheduler;
        this.uiScheduler = uiScheduler;

        // Initialize this presenter to be lifecycle-aware when a view is a lifecycle owner.
        if (view instanceof LifecycleOwner) {
            ((LifecycleOwner) view).getLifecycle().addObserver(this);
        }

        disposeBag = new CompositeDisposable();

    }

    @Override @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onAttach() {
        //based on shared prefs- check if city name is saved.. if not display error page/empty results
        if(!getWeatherCityName().equals("none")){

            loadWeather(getWeatherCityName());
        }else{
            view.showNoDataMessage();
        }
    }

    @Override @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onDetach() {
        disposeBag.clear();
    }

    @Override
    public void loadWeather(String cityName) {
       // Clear old data on view
        view.clearWeather();
        view.clearView();
        //Add previous query to share preferences
        if(!cityName.isEmpty()) {
            setWeatherCityName(cityName);
        }

        // Load new one and populate it into view
        Disposable disposable = repository.loadWeather(cityName)
                .filter(weather -> weather.getWeather().get(0).getIcon() != null)
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .subscribe(this::handleReturnedData, this::handleError, () -> view.stopLoadingIndicator());
        disposeBag.add(disposable);
    }

    @Override
    public void setWeatherCityName(String cityName) {
        repository.setWeatherCityName(cityName);
    }

    @Override
    public String getWeatherCityName() {
        return repository.getWeatherCityName();
    }

    @Override
    public String getFahrenheit(double kelvin) {
        int calculation = (int) ((kelvin * (9/5f)) - 459.67);
        return String.valueOf(calculation);
    }

    @Override
    public String getCelcius(double kelvin) {
        return null;
    }

    private void handleError(Throwable error) {
        view.stopLoadingIndicator();
        //can handle displaying different views based on errors returned
        view.showErrorMessage(error.getLocalizedMessage());
    }

    private void handleReturnedData(CityWeather obj) {
        view.stopLoadingIndicator();

        if (obj.getWeather() != null && !obj.getWeather().isEmpty()) {
            view.showWeather(obj.getWeather(), obj.getMain());
        } else {
            view.showNoDataMessage();
        }
    }

    public String getWeatherUrl(String weatherIcon) {
        return Config.AWS_WEATHER_PHOTO_BASE_URL + weatherIcon + ".png";
    }
}
