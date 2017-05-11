package sri.git.user.dagger.module;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by sridhar on 11/5/17.
 */

@Module(includes = {GitUserRestServiceModule.class, DataBaseModule.class})
public class GitAppModule {

    @Provides
    public Scheduler providesSchduler() {
        return Schedulers.io();
    }

}
