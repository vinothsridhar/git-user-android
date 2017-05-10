package sri.git.user.data;

import android.content.Context;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sridhar on 9/5/17.
 */

public class RestFactory {

    private static final int CACHE_SIZE = 10 * 1024 * 1024; //10MB

    public static final String BASE_URL = "https://api.github.com/";
    public static final String USERS_URL = BASE_URL + "users";

    public static GitUserRestService create(Context context) {

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        Cache cache = new Cache(context.getCacheDir(), CACHE_SIZE);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .cache(cache)
                .build();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return retrofit.create(GitUserRestService.class);
    }

}
