package sri.git.user.dagger.component;

import javax.inject.Singleton;

import dagger.Component;
import io.reactivex.Scheduler;
import sri.git.user.dagger.module.GitAppModule;
import sri.git.user.data.DbHelper;
import sri.git.user.data.GitUserRestService;

/**
 * Created by sridhar on 11/5/17.
 */

@Component(modules = {GitAppModule.class})
@Singleton
public interface GitAppComponent {

    GitUserRestService getGitUserRestService();

    Scheduler getScheduler();

    DbHelper getDbHelper();
}
