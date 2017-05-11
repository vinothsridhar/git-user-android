package sri.git.user.viewmodel;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import sri.git.user.data.GitUserRestService;
import sri.git.user.model.GitRepo;
import sri.git.user.model.GitUser;
import sri.git.user.utils.L;
import sri.git.user.view.adapter.GitUserRepoAdapter;

/**
 * Created by sridhar on 9/5/17.
 */

public class GitUserRepoViewModel extends Observable implements BaseViewModel {

    private static final String TAG = GitUserRepoViewModel.class.getSimpleName();

    private static final int SCROLL_THRESHOLD = 5;
    private static final int PAGE_SIZE = 30; //git api default page size

    private Context context;
    private GitUser gitUser;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Disposable gitUserRepoDisposable;
    private int pageNumber = 0;
    private List<GitRepo> gitRepoList;
    private boolean noLoadMore = false;

    @Inject
    GitUserRestService gitUserRestService;

    @Inject
    Scheduler scheduler;

    @Inject
    GitUserRepoAdapter gitUserRepoAdapter;

    public GitUserRepoViewModel(Context context, GitUser gitUser) {
        this.context = context;
        this.gitUser = gitUser;
        this.gitRepoList = new ArrayList<>();
    }

    public String getTitle() {
        return gitUser.getLogin();
    }

    public String getAvatarUrl() {
        return gitUser.getAvatarUrl();
    }

    @BindingAdapter("avatarUrl")
    public static void loadAvatar(ImageView imageView, String url) {
        Picasso.with(imageView.getContext()).load(url).into(imageView);
    }

    public void loadMore() {
        if (noLoadMore) {
            return;
        }

        fetchRepos();
    }

    /**
     * RecyclerView OnScrollListener
     */
    public RecyclerView.OnScrollListener gitUserRepoRecyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            int totalItemCount = linearLayoutManager.getItemCount();
            int lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
            boolean exceedsThreshold = totalItemCount <= lastVisibleItem + SCROLL_THRESHOLD;
            if (exceedsThreshold) {
                loadMore();
            }
        }
    };

    @Override
    public void onCreate() {
        //fetch right away
        fetchRepos();
    }

    @Override
    public void onDestroy() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
            compositeDisposable = null;
        }
        context = null;
    }

    public List<GitRepo> getGitRepoList() {
        return gitRepoList;
    }

    public GitUserRepoAdapter gitUserRepoAdapter() {
        return gitUserRepoAdapter;
    }

    /**
     * return https://api.github.com/users/mojombo/repos?page={x}
     * @return reposUrlWithPageNumber
     */
    private String getReposUrlWithPageNumber() {
        return gitUser.getReposUrl() + "?page=" + pageNumber;
    }

    /**
     * Fetch git user repos page by page
     */
    private void fetchRepos() {
        //
        if (gitUserRepoDisposable != null && !gitUserRepoDisposable.isDisposed()) {
            return;
        }

        //add progress bar to recycler view
        changeGitUserRepoSet(Arrays.asList(new GitRepo[] {null}));

        pageNumber++;

        //load with retry we may face connection issue
        gitUserRepoDisposable = gitUserRestService.fetchRepos(getReposUrlWithPageNumber())
                .subscribeOn(scheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .retry(2)
                .subscribe(new Consumer<List<GitRepo>>() {
                    @Override
                    public void accept(List<GitRepo> gitRepos) throws Exception {
                        if (gitRepos == null) {
                            gitRepos = new ArrayList<>();
                        }

                        //stop loading more if we get less results than page size
                        if (gitRepos.size() < PAGE_SIZE) {
                            noLoadMore = true;
                        }
                        changeGitUserRepoSet(gitRepos);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        L.logException(TAG, (Exception) throwable);
                    }
                });

        compositeDisposable.add(gitUserRepoDisposable);
    }

    /**
     * update recyclerview observer
     * @param gitRepoList
     */
    private void changeGitUserRepoSet(List<GitRepo> gitRepoList) {
        //remove existing null item from the list
        int listCount = this.gitRepoList.size();
        if (listCount > 0 && this.gitRepoList.get(listCount - 1) == null) {
            this.gitRepoList.remove(listCount - 1);
        }
        this.gitRepoList.addAll(gitRepoList);
        setChanged();
        notifyObservers();
    }
}
