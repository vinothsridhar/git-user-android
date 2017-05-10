package sri.git.user.view;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import sri.git.user.R;
import sri.git.user.databinding.ActivityGitUserRepoBinding;
import sri.git.user.model.GitUser;
import sri.git.user.viewmodel.GitUserRepoViewModel;

/**
 * Created by sridhar on 9/5/17.
 */

public class GitUserRepoActivity extends BaseActivity {

    private static final String EXTRA_USER = "EXTRA_USER";

    private GitUserRepoViewModel gitUserRepoViewModel;
    private ActivityGitUserRepoBinding activityGitUserRepoBinding;

    public static Intent newIntent(Context context, GitUser gitUser) {
        Intent i = new Intent(context, GitUserRepoActivity.class);
        i.putExtra(EXTRA_USER, gitUser);
        return i;
    }

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

        GitUser gitUser = null;
        if (getIntent().hasExtra(EXTRA_USER)) {
            gitUser = (GitUser) getIntent().getExtras().getSerializable(EXTRA_USER);
        }

        if (gitUser == null) {
            Toast.makeText(this, "No user available", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        init(gitUser);
        setSupportActionBar(activityGitUserRepoBinding.toolbar);
        showBack();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (gitUserRepoViewModel != null) {
            gitUserRepoViewModel.reset();
            gitUserRepoViewModel = null;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(menuItem);
    }

    private void init(GitUser gitUser) {
        gitUserRepoViewModel = new GitUserRepoViewModel(this, gitUser);
        activityGitUserRepoBinding = DataBindingUtil.setContentView(this, R.layout.activity_git_user_repo);
        activityGitUserRepoBinding.setGitUserRepoModel(gitUserRepoViewModel);
    }

}
