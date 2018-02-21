package kesean.com.anotherweatherapp.ui.weather;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import kesean.com.anotherweatherapp.R;
import kesean.com.anotherweatherapp.data.model.Weather;
import kesean.com.anotherweatherapp.ui.base.BaseActivity;

public class MainActivity extends BaseActivity implements WeatherContract.View {
    @BindView(R.id.refresh)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.text_notification)
    TextView notificationText;
    @BindView(R.id.weather_image)
    ImageView imageView;

    @Inject
    WeatherPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initializePresenter();
        refreshLayout.setOnRefreshListener(() -> presenter.loadWeather(presenter.getWeatherCityName()));

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
        getMenuInflater().inflate(R.menu.search, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setQueryHint(getString(R.string.search_hint));
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override public boolean onQueryTextSubmit(String query) {
//                if(query != null || !query.isEmpty()) {
                    presenter.loadWeather(query);
                    return true;
//                }else
//                {
//                    return false;
//                }
            }

            @Override public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }

    @Override
    public void showWeather(List<Weather> weather) {
        Glide.with(imageView).load(presenter.getWeatherUrl(weather.get(0).getIcon())).into(imageView);
    }

    @Override
    public void clearWeather() {

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
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void showEmptySearchResult() {
        showNotification(getString(R.string.msg_empty_search_result));
    }

    /*
    * Method for displaying error message in view
    * */
    private void showNotification(String message) {
        notificationText.setVisibility(View.VISIBLE);
        notificationText.setText(message);
    }
}
