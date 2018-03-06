package kesean.com.anotherweatherapp.ui.weather;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import kesean.com.anotherweatherapp.R;
import kesean.com.anotherweatherapp.data.model.Main;
import kesean.com.anotherweatherapp.data.model.Weather;
import kesean.com.anotherweatherapp.ui.base.BaseActivity;

public class MainActivity extends BaseActivity implements WeatherContract.View {
//    @BindView(R.id.refresh)
//    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.text_notification)
    TextView notificationText;
    @BindView(R.id.weather_image)
    ImageView imageView;
    @BindView(R.id.weather_location)
    TextView location;
    @BindView(R.id.weather_forecast)
    TextView forecast;
    @BindView(R.id.weather_temp)
    TextView weatherTemp;

    private Menu refreshMenu;
    List<Weather> weatherList;

    @Inject
    WeatherPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initializePresenter();
        weatherList = new ArrayList<>();
        //refreshLayout.setOnRefreshListener(() -> presenter.loadWeather(presenter.getWeatherCityName()));

    }

    private void initializePresenter() {
        DaggerWeatherComponent.builder()
                .weatherPresenterModule(new WeatherPresenterModule(this))
                .weatherRepositoryComponent(getWeatherRepositoryComponent())
                .build()
                .inject(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //TODO rename menu file name - confusing
        getMenuInflater().inflate(R.menu.search, menu);

        refreshMenu = menu;

        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setQueryHint(getString(R.string.search_hint));
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override public boolean onQueryTextSubmit(String query) {
                View view = searchView.getFocusedChild();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                    presenter.loadWeather(query);
                    return true;

            }

            @Override public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_refresh:
                // Do animation start
                LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                ImageView iv = (ImageView)inflater.inflate(R.layout.iv_refresh, null);
                Animation rotation = AnimationUtils.loadAnimation(this, R.anim.rotate_refresh);
                rotation.setRepeatCount(Animation.INFINITE);
                iv.startAnimation(rotation);
                item.setActionView(iv);

                //getting weather data
                presenter.loadWeather(presenter.getWeatherCityName());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showWeather(List<Weather> weather, Main temp) {
        weatherList = weather;
        Glide.with(imageView)
                .load(presenter.getWeatherUrl(weatherList.get(0).getIcon()))
                .into(imageView);
        forecast.setText(weatherList.get(0).getDescription());
        String d = String.valueOf(temp.getTemp());
        weatherTemp.setText(d);
        //check if null
        location.setText(presenter.getWeatherCityName());
    }

    @Override
    public void clearWeather() {
        if(!weatherList.isEmpty()){
            weatherList.clear();
            Glide.with(imageView).clear(imageView);
        }
    }

    @Override
    public void showNoDataMessage() {
        showNotification(getString(R.string.msg_no_data));
    }

    @Override
    public void showErrorMessage(String error) {
        showNotification(error);
    }

    @Override
    public void stopLoadingIndicator() {
        if(refreshMenu != null) {
            // Get our refresh item from the menu
            MenuItem m = refreshMenu.findItem(R.id.action_refresh);
            if (m.getActionView() != null) {
                // Remove the animation.
                m.getActionView().clearAnimation();
                m.setActionView(null);
            }
        }
    }

    @Override
    public void showEmptySearchResult() {
        showNotification(getString(R.string.msg_empty_search_result));
    }

    @Override
    public void clearView() {
        notificationText.setVisibility(View.GONE);
    }

    /*
    * Method for displaying error message in view
    * */
    private void showNotification(String message) {
        notificationText.setVisibility(View.VISIBLE);
        notificationText.setText(message);
    }
}
