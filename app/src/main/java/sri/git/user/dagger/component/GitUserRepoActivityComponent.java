package sri.git.user.dagger.component;

import dagger.Component;
import sri.git.user.dagger.module.GitUserRepoActivityModule;
import sri.git.user.dagger.scope.ActivityScope;
import sri.git.user.viewmodel.GitUserRepoViewModel;

/**
 * Created by sridhar on 11/5/17.
 */

@ActivityScope
@Component(modules = GitUserRepoActivityModule.class, dependencies = GitAppComponent.class)
public interface GitUserRepoActivityComponent {

    void inject(GitUserRepoViewModel viewModel);

}
