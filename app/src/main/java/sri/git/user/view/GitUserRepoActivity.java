package sri.git.user.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import sri.git.user.R;
import sri.git.user.databinding.ActivityGitUserRepoBinding;
import sri.git.user.viewmodel.GitUserRepoViewModel;

/**
 * Created by sridhar on 9/5/17.
 */

public class GitUserRepoActivity extends AppCompatActivity {

    private GitUserRepoViewModel gitUserRepoViewModel;
    private ActivityGitUserRepoBinding activityGitUserRepoBinding;

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

        init();
    }

    private void init() {
        gitUserRepoViewModel = new GitUserRepoViewModel(this);
        activityGitUserRepoBinding = DataBindingUtil.setContentView(this, R.layout.activity_git_user_repo);
        activityGitUserRepoBinding.setGitUserRepoModel(gitUserRepoViewModel);
    }

}
