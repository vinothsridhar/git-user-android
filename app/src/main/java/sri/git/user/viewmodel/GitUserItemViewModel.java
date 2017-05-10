package sri.git.user.viewmodel;

import android.content.Context;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import sri.git.user.R;
import sri.git.user.model.GitUser;
import sri.git.user.view.GitUserRepoActivity;

/**
 * Created by sridhar on 9/5/17.
 */

public class GitUserItemViewModel extends BaseItemViewModel<GitUser> {

    private static final String TAG = GitUserItemViewModel.class.getSimpleName();

    private Context context;
    private GitUser gitUser;

    public GitUserItemViewModel(Context context) {
        this.context = context;
    }

    @Bindable
    public String getLogin() {
        return gitUser.getLogin();
    }

    @Bindable
    public String getAvatarUrl() {
        return gitUser.getAvatarUrl();
    }

    @BindingAdapter("avatarUrl")
    public static void setAvatarUrl(ImageView imageView, String url) {
        Picasso.with(imageView.getContext()).load(url).placeholder(R.drawable.git_default_user).into(imageView);
    }

    public void onItemClick(View v) {
        context.startActivity(GitUserRepoActivity.newIntent(context, gitUser));
    }

    @Override
    public void setItem(GitUser item) {
        this.gitUser = item;
        notifyChange();
    }
}
