package zmz.zhao.com.zmz.https;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkManager {

    private String INSIDE_BASE_URL = "http://172.17.8.100/small/";
    private String OUTSIDE_BASE_URL = "http://mobile.bwstudent.com/small/";
    private static NetworkManager instance;
    private Retrofit retrofit;

    private NetworkManager() {
        init();
    }

    //单列模式
    public static NetworkManager getInstance(){

        if (instance == null) {
            instance = new NetworkManager();
        }

        return instance;
    }

    private void init() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .writeTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();

        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(INSIDE_BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    public <T> T create(final Class<T> service){
        return retrofit.create(service);
    }
}
