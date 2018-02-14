package kesean.com.anotherweatherapp.data;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import kesean.com.anotherweatherapp.data.api.QueryInterceptor;
import kesean.com.anotherweatherapp.data.api.WeatherService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Kesean on 2/13/18.
 */

@Module
public class ApiServiceModule {

    private static final String BASE_URL = "base_url";

     /*
    * Dagger method binding
    * */

    @Provides
    @Named(BASE_URL)
    String provideBaseUrl() {
        return Config.API_HOST;
    }

    @Provides
    @Singleton
    QueryInterceptor provideQueryInterceptor() {
        return new QueryInterceptor();
    }


    @Provides
    @Singleton
    HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        return new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC);
    }

    @Provides
    @Singleton
    OkHttpClient provideHttpClient(QueryInterceptor queryInterceptor, HttpLoggingInterceptor httpInterceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(queryInterceptor)
                .addInterceptor(httpInterceptor)
                .build();
    }

    @Provides
    @Singleton
    Converter.Factory provideGsonConverterFactory() {
        return GsonConverterFactory.create();
    }

    @Provides
    @Singleton
    CallAdapter.Factory provideRxJavaAdapterFactory() {
        return RxJava2CallAdapterFactory.create();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(@Named(BASE_URL) String baseUrl, Converter.Factory converterFactory,
                             CallAdapter.Factory callAdapterFactory, OkHttpClient client) {
        return new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(converterFactory)
                .addCallAdapterFactory(callAdapterFactory)
                .client(client)
                .build();
    }

    @Provides
    @Singleton
    WeatherService provideWeatherService(Retrofit retrofit) {
        return retrofit.create(WeatherService.class);
    }
}
