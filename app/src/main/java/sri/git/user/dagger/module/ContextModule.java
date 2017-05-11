package sri.git.user.dagger.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by sridhar on 11/5/17.
 */

@Module
public class ContextModule {

    private final Context context;

    public ContextModule(Context context) {
        this.context = context;
    }

    @Provides
    public Context providesContext() {
        return this.context;
    }

}
