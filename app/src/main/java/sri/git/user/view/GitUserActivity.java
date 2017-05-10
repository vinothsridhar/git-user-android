package sri.git.user.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.Observable;
import java.util.Observer;

import sri.git.user.R;
import sri.git.user.databinding.ActivityGitUserBinding;
import sri.git.user.utils.L;
import sri.git.user.view.adapter.GitUserAdapter;
import sri.git.user.viewmodel.BaseViewModel;
import sri.git.user.viewmodel.GitUserViewModel;

public class GitUserActivity extends BaseActivity implements Observer {

    private ActivityGitUserBinding activityGitUserBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void init() {
        setSupportActionBar(activityGitUserBinding.toolbar);
        setupGitUserList(activityGitUserBinding.gitUserRecyclerView);
        getGitUserViewModel().addObserver(this);
    }

    @Override
    public void update(Observable observable, Object o) {
        if (observable instanceof GitUserViewModel) {
            L.d(TAG, "update from observable");
            GitUserAdapter gitUserAdapter = (GitUserAdapter) activityGitUserBinding.gitUserRecyclerView.getAdapter();
            GitUserViewModel gitUserViewModel = (GitUserViewModel) observable;
            gitUserAdapter.refresh(gitUserViewModel.getGitUserList());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public BaseViewModel createViewModel() {
        return new GitUserViewModel(this);
    }

    @Override
    public void bindView() {
        activityGitUserBinding = DataBindingUtil.setContentView(this, R.layout.activity_git_user);
        activityGitUserBinding.setGitUserModel(getGitUserViewModel());
    }

    private GitUserViewModel getGitUserViewModel() {
        return (GitUserViewModel) viewModel;
    }

    private void setupGitUserList(RecyclerView recyclerView) {
        GitUserAdapter gitUserAdapter = new GitUserAdapter();
        recyclerView.setAdapter(gitUserAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }
}
