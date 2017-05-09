package sri.git.user.viewmodel;

import android.content.Context;

import java.util.Collections;
import java.util.List;
import java.util.Observable;

import sri.git.user.model.GitUser;

/**
 * Created by sridhar on 9/5/17.
 */

public class GitUserViewModel extends Observable {

    private List<GitUser> gitUserList = Collections.emptyList();

    public GitUserViewModel(Context context) {

    }

    public List<GitUser> getGitUserList() {
        return this.gitUserList;
    }

    private void fetchGitUsers() {

    }

}
