package sri.git.user.view;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Observable;
import java.util.Observer;

import sri.git.user.R;
import sri.git.user.databinding.ActivityGitUserRepoBinding;
import sri.git.user.model.GitUser;
import sri.git.user.view.adapter.GitUserRepoAdapter;
import sri.git.user.viewmodel.BaseViewModel;
import sri.git.user.viewmodel.GitUserRepoViewModel;

/**
 * Created by sridhar on 9/5/17.
 */

public class GitUserRepoActivity extends BaseActivity implements Observer {

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

        //no need to proceed further if we don't have user object
        if (gitUser == null) {
            Toast.makeText(this, "No user available", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        this.gitUser = gitUser;

        super.onCreate(savedInstance);
    }

    @Override
    public void init() {
        setSupportActionBar(activityGitUserRepoBinding.toolbar);
        showBack();
        setupGitUserRepoList(activityGitUserRepoBinding.gitUserRepoRecyclerView);
        getGitUserRepoViewModel().addObserver(this);
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

    @Override
    public void update(Observable observable, Object o) {
        if (observable instanceof GitUserRepoViewModel) {
            final GitUserRepoAdapter gitUserRepoAdapter = (GitUserRepoAdapter) activityGitUserRepoBinding.gitUserRepoRecyclerView.getAdapter();
            if (gitUserRepoAdapter.getItemCount() == 0) {
                gitUserRepoAdapter.refresh(getGitUserRepoViewModel().getGitRepoList());
            } else {
                activityGitUserRepoBinding.gitUserRepoRecyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        int existingCount = gitUserRepoAdapter.getItemCount();
                        //remove existing progress item from the recyclerview
                        if (gitUserRepoAdapter.getItemViewType(existingCount - 1) == GitUserRepoAdapter.VIEW_PROGRESS) {
                            gitUserRepoAdapter.notifyItemRemoved(existingCount - 1);
                        }

                        gitUserRepoAdapter.notifyDataSetChanged();
                    }
                });
            }
        }
    }

    private void setupGitUserRepoList(RecyclerView recyclerView) {
        GitUserRepoAdapter gitUserRepoAdapter = new GitUserRepoAdapter();
        recyclerView.setAdapter(gitUserRepoAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.addOnScrollListener(getGitUserRepoViewModel().gitUserRepoRecyclerViewOnScrollListener);
    }

    private GitUserRepoViewModel getGitUserRepoViewModel() {
        return (GitUserRepoViewModel) viewModel;
    }
}
