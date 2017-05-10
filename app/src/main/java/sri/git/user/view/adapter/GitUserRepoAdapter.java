package sri.git.user.view.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sri.git.user.R;
import sri.git.user.databinding.ItemGitUserRepoBinding;
import sri.git.user.model.GitRepo;
import sri.git.user.viewmodel.BaseItemViewModel;
import sri.git.user.viewmodel.GitUserRepoItemViewModel;

/**
 * Created by sridhar on 10/5/17.
 */

public class GitUserRepoAdapter extends BaseRecyclerViewAdapter<GitRepo, GitUserRepoItemViewModel> {

    public static final int VIEW_ITEM = 1;
    public static final int VIEW_PROGRESS = 2;

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_ITEM) {
            ItemGitUserRepoBinding itemGitUserRepoBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_git_user_repo, parent, false);
            return new GitUserRepoViewHolder(itemGitUserRepoBinding);
        } else {
            return new ProgressViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.progress_bar, parent, false));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position) == null) {
            return VIEW_PROGRESS;
        } else {
            return VIEW_ITEM;
        }
    }

    /**
     * Progress view for the recycler view when we load more items
     */
    public static class ProgressViewHolder extends BaseRecyclerViewAdapter.ItemViewHolder {
        @Override
        public BaseItemViewModel getItemViewModel(ViewDataBinding viewDataBinding) {
            return null;
        }

        public ProgressViewHolder(View itemView) {
            super(itemView, null);
        }
    }

    public static class GitUserRepoViewHolder extends BaseRecyclerViewAdapter.ItemViewHolder {

        public GitUserRepoViewHolder(ItemGitUserRepoBinding itemGitUserRepoBinding) {
            super(itemGitUserRepoBinding.gitUserRepoItem, itemGitUserRepoBinding);
        }

        @Override
        public BaseItemViewModel getItemViewModel(ViewDataBinding viewDataBinding) {
            ItemGitUserRepoBinding itemGitUserRepoBinding = (ItemGitUserRepoBinding) viewDataBinding;
            if (itemGitUserRepoBinding.getGitUserRepoItemViewModel() == null) {
                itemGitUserRepoBinding.setGitUserRepoItemViewModel(new GitUserRepoItemViewModel(itemView.getContext()));
            }

            return itemGitUserRepoBinding.getGitUserRepoItemViewModel();
        }
    }

}
