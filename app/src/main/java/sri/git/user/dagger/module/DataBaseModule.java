package sri.git.user.dagger.module;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import dagger.Module;
import dagger.Provides;
import sri.git.user.data.DbHelper;

/**
 * Created by sridhar on 11/5/17.
 */

@Module(includes = ContextModule.class)
public class DataBaseModule {

    @Provides
    public DbHelper providesDbHelper(Context context) {
        return OpenHelperManager.getHelper(context, DbHelper.class);
    }

}
