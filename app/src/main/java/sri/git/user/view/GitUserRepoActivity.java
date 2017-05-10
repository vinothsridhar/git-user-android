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
import sri.git.user.viewmodel.BaseViewModel;
import sri.git.user.viewmodel.GitUserRepoViewModel;

/**
 * Created by sridhar on 9/5/17.
 */

public class GitUserRepoActivity extends BaseActivity {

    private static final String EXTRA_USER = "EXTRA_USER";

    private ActivityGitUserRepoBinding activityGitUserRepoBinding;
    private GitUser gitUser;

    public static Intent newIntent(Context context, GitUser gitUser) {
        Intent i = new Intent(context, GitUserRepoActivity.class);
        i.putExtra(EXTRA_USER, gitUser);
        return i;
    }

    @Override
    public void onCreate(Bundle savedInstance) {
        GitUser gitUser = null;
        if (getIntent().hasExtra(EXTRA_USER)) {
            gitUser = (GitUser) getIntent().getExtras().getSerializable(EXTRA_USER);
        }

        if (gitUser == null) {
            Toast.makeText(this, "No user available", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        this.gitUser = gitUser;

        super.onCreate(savedInstance);

        setSupportActionBar(activityGitUserRepoBinding.toolbar);
        showBack();
    }

    @Override
    public BaseViewModel createViewModel() {
        return new GitUserRepoViewModel(this, gitUser);
    }

    @Override
    public void bindView() {
        activityGitUserRepoBinding = DataBindingUtil.setContentView(this, R.layout.activity_git_user_repo);
        activityGitUserRepoBinding.setGitUserRepoModel(getGitUserRepoViewModel());
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

    private GitUserRepoViewModel getGitUserRepoViewModel() {
        return (GitUserRepoViewModel) viewModel;
    }
}
