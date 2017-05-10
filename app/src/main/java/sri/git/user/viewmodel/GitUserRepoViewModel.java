package sri.git.user.viewmodel;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import sri.git.user.GitApplication;
import sri.git.user.data.GitUserRestService;
import sri.git.user.model.GitRepo;
import sri.git.user.model.GitUser;

/**
 * Created by sridhar on 9/5/17.
 */

public class GitUserRepoViewModel {

    private Context context;
    private GitUser gitUser;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public GitUserRepoViewModel(Context context, GitUser gitUser) {
        this.context = context;
        this.gitUser = gitUser;
    }

    public String getTitle() {
        return gitUser.getLogin();
    }

    public String getAvatarUrl() {
        return gitUser.getAvatarUrl();
    }

    public String getReposUrl() {
        return gitUser.getReposUrl();
    }

    public void reset() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
            compositeDisposable = null;
        }
    }

    @BindingAdapter("avatarUrl")
    public static void loadAvatar(ImageView imageView, String url) {
        Picasso.with(imageView.getContext()).load(url).into(imageView);
    }

    private void fetchRepos() {
        GitApplication gitApplication = GitApplication.create(context);
        GitUserRestService gitUserRestService = gitApplication.getRestFactory();

        Disposable disposable = gitUserRestService.fetchRepos(gitUser.getReposUrl())
                .subscribeOn(gitApplication.getScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<GitRepo>>() {
                    @Override
                    public void accept(List<GitRepo> gitRepos) throws Exception {

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });

        compositeDisposable.add(disposable);
    }

}
