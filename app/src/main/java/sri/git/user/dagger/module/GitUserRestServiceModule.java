package sri.git.user.dagger.module;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import sri.git.user.data.Endpoint;
import sri.git.user.data.GitUserRestService;

/**
 * Created by sridhar on 11/5/17.
 */

@Module(includes = NetworkModule.class)
public class GitUserRestServiceModule {

    @Provides
    public GitUserRestService providesGitUserRestService(Retrofit retrofit) {
        return retrofit.create(GitUserRestService.class);
    }

    @Provides
    public GsonConverterFactory providesGsonConverterFactory() {
        return GsonConverterFactory.create();
    }

    @Provides
    public RxJava2CallAdapterFactory providesRxJava2CallAdapterFactory() {
        return RxJava2CallAdapterFactory.create();
    }

    @Provides
    public String providesBaseUrl() {
        return Endpoint.BASE_URL;
    }

    @Provides
    public Retrofit providesRetrofit(String baseUrl, OkHttpClient okHttpClient, GsonConverterFactory gsonConverterFactory, RxJava2CallAdapterFactory rxJava2CallAdapterFactory) {
        return new Retrofit.Builder().baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(rxJava2CallAdapterFactory)
                .build();
    }

}
