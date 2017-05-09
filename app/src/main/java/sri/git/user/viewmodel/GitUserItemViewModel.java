package sri.git.user.viewmodel;

import android.content.Context;

import sri.git.user.model.GitUser;

/**
 * Created by sridhar on 9/5/17.
 */

public class GitUserItemViewModel {

    private Context context;
    private GitUser gitUser;

    public GitUserItemViewModel(Context context, GitUser gitUser) {
        this.context = context;
        this.gitUser = gitUser;
    }

    public void setGitUser(GitUser gitUser) {
        this.gitUser = gitUser;
    }

}
