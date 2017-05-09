package sri.git.user.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.Observable;
import java.util.Observer;

import sri.git.user.R;
import sri.git.user.databinding.ActivityGitUserBinding;
import sri.git.user.viewmodel.GitUserViewModel;

public class GitUserActivity extends AppCompatActivity implements Observer {

    private GitUserViewModel gitUserViewModel;
    private ActivityGitUserBinding activityGitUserBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
        setSupportActionBar(activityGitUserBinding.toolbar);
        setupGitUserList(activityGitUserBinding.gitUserRecyclerView);
    }

    private void init() {
        gitUserViewModel = new GitUserViewModel(this);
        activityGitUserBinding = DataBindingUtil.setContentView(this, R.layout.activity_git_user);
        activityGitUserBinding.setGitUserModel(gitUserViewModel);
    }

    private void setupGitUserList(RecyclerView recyclerView) {
        GitUserAdapter gitUserAdapter = new GitUserAdapter();
        recyclerView.setAdapter(gitUserAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }

    @Override
    public void update(Observable observable, Object o) {
        if (observable instanceof GitUserViewModel) {
            GitUserAdapter gitUserAdapter = (GitUserAdapter) activityGitUserBinding.gitUserRecyclerView.getAdapter();
            GitUserViewModel gitUserViewModel = (GitUserViewModel) observable;
            gitUserAdapter.setGitUserList(gitUserViewModel.getGitUserList());
        }
    }
}
