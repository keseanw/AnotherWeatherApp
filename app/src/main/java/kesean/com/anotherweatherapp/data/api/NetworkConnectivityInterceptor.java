package kesean.com.anotherweatherapp.data.api;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Kesean on 3/2/18.
 *
 * This class adds a network connectivity check for every api call and provides exception
 */

public class NetworkConnectivityInterceptor implements Interceptor {

    private final ConnectivityManager connectivityManager;

    @Inject
    public NetworkConnectivityInterceptor(ConnectivityManager connectivityManager){
        this.connectivityManager = connectivityManager;
    }


    @Override
    public Response intercept(Chain chain) throws IOException {
        if (!isConnected()) {
            throw new OfflineException("No Available Internet Connectivity");
        }

        return chain.proceed(chain.request());
    }

    protected boolean isConnected() {
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    public class OfflineException extends IOException {

        String s;
        public OfflineException(String s) {
            this.s = s;
        }

        @Override
        public String getMessage() {
            return s;
        }

    }

}
