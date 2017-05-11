package sri.git.user.dagger.module;

import android.content.Context;

import java.io.File;
import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import sri.git.user.utils.L;

/**
 * Created by sridhar on 11/5/17.
 */

@Module(includes = ContextModule.class)
public class NetworkModule {

    @Provides
    public HttpLoggingInterceptor providesLoggingInterceptor() {
        return new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                L.d("Okhttp", message);
            }
        });
    }

    @Provides
    public Cache providesCache(File cacheFile) {
        return new Cache(cacheFile, 10 * 1024 * 1024);
    }

    @Provides
    public File providesCacheFile(Context context) {
        return new File(context.getCacheDir(), "git-user-okhttp-cache");
    }

    @Provides
    public OkHttpClient providesOkHttpClient(HttpLoggingInterceptor httpLoggingInterceptor, Cache cache) {
        return new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .cache(cache)
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();
    }
}
