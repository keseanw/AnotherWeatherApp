package kesean.com.anotherweatherapp.ui.weather;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;

import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import kesean.com.anotherweatherapp.data.model.Weather;
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
    }

    @Override @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onDetach() {
        disposeBag.clear();
    }

    @Override
    public void loadWeather(String cityName) {
        // Clear old data on view
        view.clearWeather();

        // Load new one and populate it into view
        Disposable disposable = repository.loadWeather(cityName)
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .subscribe(this::handleReturnedData, this::handleError, () -> view.stopLoadingIndicator());
        disposeBag.add(disposable);
    }

    private void handleError(Throwable error) {
        view.stopLoadingIndicator();
        view.showErrorMessage(error.getLocalizedMessage());
    }

    private void handleReturnedData(List<Weather> list) {
        view.stopLoadingIndicator();
        if (list != null && !list.isEmpty()) {
            view.showWeather(list);
        } else {
            view.showNoDataMessage();
        }
    }
}
