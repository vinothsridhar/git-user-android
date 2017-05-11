package sri.git.user.dagger.module;

import dagger.Module;
import dagger.Provides;
import sri.git.user.dagger.scope.ActivityScope;
import sri.git.user.view.adapter.GitUserRepoAdapter;

/**
 * Created by sridhar on 11/5/17.
 */

@Module
public class GitUserRepoActivityModule {

    @Provides
    @ActivityScope
    public GitUserRepoAdapter providesGitUserRepoAdapter() {
        return new GitUserRepoAdapter();
    }

}
