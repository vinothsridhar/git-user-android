package sri.git.user.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import sri.git.user.GitApplication;
import sri.git.user.R;
import sri.git.user.dagger.component.DaggerGitUserActivityComponent;
import sri.git.user.dagger.component.GitUserActivityComponent;
import sri.git.user.databinding.ActivityGitUserBinding;
import sri.git.user.viewmodel.BaseViewModel;
import sri.git.user.viewmodel.GitUserViewModel;

public class GitUserActivity extends BaseActivity {

    public static final String RETRY_MODE_KEY = "RETRY_MODE_KEY";

    private ActivityGitUserBinding activityGitUserBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void init() {
        GitUserActivityComponent gitUserActivityComponent = DaggerGitUserActivityComponent.builder()
                .gitAppComponent(GitApplication.create(this).getGitAppComponent())
                .build();
        gitUserActivityComponent.inject(getGitUserViewModel());

        setSupportActionBar(activityGitUserBinding.toolbar);
        setupGitUserList(activityGitUserBinding.gitUserRecyclerView);

        if (getIntent().hasExtra(RETRY_MODE_KEY)) {
            getGitUserViewModel().retryMode();
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
        recyclerView.setAdapter(getGitUserViewModel().getGitUserAdapter());
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }
}
