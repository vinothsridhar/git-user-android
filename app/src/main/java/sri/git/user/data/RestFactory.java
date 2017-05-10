package sri.git.user.data;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sridhar on 9/5/17.
 */

public class RestFactory {

    public static final String BASE_URL = "https://api.github.com/";
    public static final String USERS_URL = BASE_URL + "users";

    public static GitUserRestService create() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return retrofit.create(GitUserRestService.class);
    }

}
