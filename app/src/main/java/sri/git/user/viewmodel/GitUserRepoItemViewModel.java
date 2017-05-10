package sri.git.user.viewmodel;

import android.content.Context;

import sri.git.user.model.GitRepo;

/**
 * Created by sridhar on 10/5/17.
 */

public class GitUserRepoItemViewModel extends BaseItemViewModel<GitRepo> {

    private Context context;
    private GitRepo gitRepo;

    public GitUserRepoItemViewModel(Context context) {
        this.context = context;
    }


    @Override
    public void setItem(GitRepo item) {
        this.gitRepo = item;
        notifyChange();
    }
}
