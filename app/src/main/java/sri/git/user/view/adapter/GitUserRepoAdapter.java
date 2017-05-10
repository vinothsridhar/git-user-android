package sri.git.user.view.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import sri.git.user.R;
import sri.git.user.databinding.ItemGitUserRepoBinding;
import sri.git.user.viewmodel.BaseItemViewModel;
import sri.git.user.viewmodel.GitUserRepoItemViewModel;

/**
 * Created by sridhar on 10/5/17.
 */

public class GitUserRepoAdapter extends BaseRecyclerViewAdapter {

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemGitUserRepoBinding itemGitUserRepoBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_git_user_repo, parent, false);
        return new GitUserRepoViewHolder(itemGitUserRepoBinding);
    }

    public static class GitUserRepoViewHolder extends BaseRecyclerViewAdapter.ItemViewHolder {

        public GitUserRepoViewHolder(ItemGitUserRepoBinding itemGitUserRepoBinding) {
            super(itemGitUserRepoBinding.gitUserRepoItem, itemGitUserRepoBinding);
        }

        @Override
        public BaseItemViewModel getItemViewModel(ViewDataBinding viewDataBinding) {
            ItemGitUserRepoBinding itemGitUserRepoBinding = (ItemGitUserRepoBinding) viewDataBinding;
            if (itemGitUserRepoBinding.getGitUserRepoViewModel() == null) {
                itemGitUserRepoBinding.setGitUserRepoViewModel(new GitUserRepoItemViewModel(itemView.getContext()));
            }

            return itemGitUserRepoBinding.getGitUserRepoViewModel();
        }
    }

}
