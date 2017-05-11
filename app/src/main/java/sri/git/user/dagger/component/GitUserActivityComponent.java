package sri.git.user.dagger.component;

import dagger.Component;
import sri.git.user.dagger.module.GitUserActivityModule;
import sri.git.user.dagger.scope.ActivityScope;
import sri.git.user.viewmodel.GitUserViewModel;

/**
 * Created by sridhar on 11/5/17.
 */

@ActivityScope
@Component(modules = GitUserActivityModule.class, dependencies = GitAppComponent.class)
public interface GitUserActivityComponent {

    void inject(GitUserViewModel viewModel);

}
