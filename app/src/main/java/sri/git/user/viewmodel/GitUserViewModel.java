package sri.git.user.viewmodel;

import android.content.Context;
import android.databinding.ObservableInt;
import android.view.View;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import sri.git.user.GitApplication;
import sri.git.user.data.DbHelper;
import sri.git.user.data.GitUserRestService;
import sri.git.user.data.RestFactory;
import sri.git.user.model.GitUser;
import sri.git.user.utils.L;

/**
 * Created by sridhar on 9/5/17.
 */

public class GitUserViewModel extends Observable {

    private static final String TAG = GitUserViewModel.class.getSimpleName();

    public ObservableInt loadingProgressBar;
    public ObservableInt gitUserListRecyclerView;
    public ObservableInt retryLinearLayout;

    private Context context;
    private List<GitUser> gitUserList = new ArrayList<GitUser>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private DbHelper dbHelper;

    public GitUserViewModel(Context context) {
        this.context = context;

        loadingProgressBar = new ObservableInt(View.GONE);
        gitUserListRecyclerView = new ObservableInt(View.GONE);
        retryLinearLayout = new ObservableInt(View.GONE);
    }

    public List<GitUser> getGitUserList() {
        return this.gitUserList;
    }

    /**
     * Reset this viewmodel
     */
    public void reset() {
        unregisterObservable();
        compositeDisposable = null;
        //release db helper
        if (dbHelper != null) {
            OpenHelperManager.releaseHelper();
            dbHelper = null;
        }
        context = null;
    }

    /**
     * Get users from the local db
     * if nothing found fetch it from the server then save it to local db
     */
    public void showGitUsers() {
        showLoadingProgressBar();
        List<GitUser> gitUserList = Collections.emptyList();
        DbHelper dbHelper = OpenHelperManager.getHelper(context, DbHelper.class);
        try {
            L.d(TAG, "getting git users list from local db");
            gitUserList = dbHelper.getGitUserDao().queryForAll();
        } catch (SQLException e) {
            L.logException(TAG, e);
        }

        if (gitUserList != null && !gitUserList.isEmpty()) {
            L.d(TAG, "local db git users list is not empty then use it");
            changeGitUserDataSet(gitUserList);
            showGitUserListRecyclerView();
        } else {
            L.d(TAG, "local db git users list is empty");
            fetchGitUserList();
        }
    }

    public void onRetryClicked(View v) {
        showLoadingProgressBar();
        fetchGitUserList();
    }

    private void showGitUserListRecyclerView() {
        gitUserListRecyclerView.set(View.VISIBLE);
        loadingProgressBar.set(View.GONE);
        retryLinearLayout.set(View.GONE);
    }

    private void showLoadingProgressBar() {
        loadingProgressBar.set(View.VISIBLE);
        gitUserListRecyclerView.set(View.GONE);
        retryLinearLayout.set(View.GONE);
    }

    private void showRetry() {
        retryLinearLayout.set(View.VISIBLE);
        gitUserListRecyclerView.set(View.GONE);
        loadingProgressBar.set(View.GONE);
    }

    /**
     * Notify observers that Git users list has been changed
     * @param gitUserList
     */
    private void changeGitUserDataSet(List<GitUser> gitUserList) {
        L.d(TAG, "git users list changed | count: " + gitUserList.size() + " | notifying observers");
        this.gitUserList.addAll(gitUserList);
        setChanged();
        notifyObservers();
    }

    private DbHelper getDbHelper() {
        if (dbHelper == null) {
            dbHelper = OpenHelperManager.getHelper(context, DbHelper.class);
        }

        return dbHelper;
    }

    /**
     * Fetch the git users from the server
     */
    private void fetchGitUserList() {
        L.d(TAG, "fetching git users from the server");
        GitApplication gitApplication = GitApplication.create(context);
        GitUserRestService gitUserRestService = gitApplication.getRestFactory();

        Disposable disposable = gitUserRestService.fetchUsers(RestFactory.USERS_URL)
                .subscribeOn(gitApplication.getScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .retry(3)
                .subscribe(new Consumer<List<GitUser>>() {
                    @Override
                    public void accept(List<GitUser> gitUserList) throws Exception {
                        saveGitUserToDb(gitUserList);
                        showGitUserListRecyclerView();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        L.logException(TAG, (Exception) throwable);
                        showRetry();
                    }
                });

        compositeDisposable.add(disposable);
    }

    /**
     * Save users list to local db
     * @param gitUserList
     */
    private void saveGitUserToDb(List<GitUser> gitUserList) {
        //save this to db
        try {
            for (GitUser gitUser : gitUserList) {
                getDbHelper().getGitUserDao().create(gitUser);
            }
        } catch (SQLException e) {
            L.logException(TAG, e);
        }

        changeGitUserDataSet(gitUserList);
    }

    /**
     * Unregister rx related disposable
     */
    private void unregisterObservable() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

}
