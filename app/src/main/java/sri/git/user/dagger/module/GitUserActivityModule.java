package sri.git.user.dagger.module;

import dagger.Module;
import dagger.Provides;
import sri.git.user.dagger.scope.ActivityScope;
import sri.git.user.view.adapter.GitUserAdapter;

/**
 * Created by sridhar on 11/5/17.
 */

@Module
public class GitUserActivityModule {

    @Provides
    @ActivityScope
    public GitUserAdapter providesGitUserAdapter() {
        return new GitUserAdapter();
    }

}
