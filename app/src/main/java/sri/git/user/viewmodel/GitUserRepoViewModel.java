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

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import sri.git.user.GitApplication;
import sri.git.user.data.GitUserRestService;
import sri.git.user.model.GitRepo;
import sri.git.user.model.GitUser;
import sri.git.user.utils.L;

/**
 * Created by sridhar on 9/5/17.
 */

public class GitUserRepoViewModel extends Observable implements BaseViewModel {

    private static final String TAG = GitUserRepoViewModel.class.getSimpleName();

    private static final int SCROLL_THRESHOLD = 5;
    private static final int PAGE_SIZE = 30;

    private Context context;
    private GitUser gitUser;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Disposable gitUserRepoDisposable;
    private int pageNumber = 0;
    private GitApplication gitApplication;
    private List<GitRepo> gitRepoList;
    private boolean noLoadMore = false;

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
        gitApplication = GitApplication.create(context);
        fetchRepos();
    }

    @Override
    public void onDestroy() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
            compositeDisposable = null;
        }
    }

    public List<GitRepo> getGitRepoList() {
        return gitRepoList;
    }

    private String getReposUrlWithPageNumber() {
        return gitUser.getReposUrl() + "?page=" + pageNumber;
    }

    private void fetchRepos() {
        if (gitUserRepoDisposable != null && !gitUserRepoDisposable.isDisposed()) {
            return;
        }

        //add progress bar to recycler view
        changeGitUserRepoSet(Arrays.asList(new GitRepo[] {null}));

        pageNumber++;

        GitUserRestService gitUserRestService = gitApplication.getRestFactory();

        gitUserRepoDisposable = gitUserRestService.fetchRepos(getReposUrlWithPageNumber())
                .subscribeOn(gitApplication.getScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .retry(2)
                .subscribe(new Consumer<List<GitRepo>>() {
                    @Override
                    public void accept(List<GitRepo> gitRepos) throws Exception {
                        if (gitRepos == null) {
                            gitRepos = new ArrayList<GitRepo>();
                        }

                        if (gitRepos.size() < PAGE_SIZE) {
                            noLoadMore = true;
                            changeGitUserRepoSet(gitRepos);
                        } else {
                            changeGitUserRepoSet(gitRepos);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        L.logException(TAG, (Exception) throwable);
                    }
                });

        compositeDisposable.add(gitUserRepoDisposable);
    }

    private void changeGitUserRepoSet(List<GitRepo> gitRepoList) {
        int listCount = this.gitRepoList.size();
        if (listCount > 0 && this.gitRepoList.get(listCount - 1) == null) {
            this.gitRepoList.remove(listCount - 1);
        }
        this.gitRepoList.addAll(gitRepoList);
        setChanged();
        notifyObservers();
    }
}
