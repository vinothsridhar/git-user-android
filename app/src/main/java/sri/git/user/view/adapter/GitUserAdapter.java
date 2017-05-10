package sri.git.user.view.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import sri.git.user.R;
import sri.git.user.databinding.ItemGitUserBinding;
import sri.git.user.model.GitUser;
import sri.git.user.viewmodel.BaseItemViewModel;
import sri.git.user.viewmodel.GitUserItemViewModel;

/**
 * Created by sridhar on 9/5/17.
 */

public class GitUserAdapter extends BaseRecyclerViewAdapter<GitUser, GitUserItemViewModel> {

    @Override
    public GitUserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemGitUserBinding itemGitUserBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_git_user, parent, false);
        return new GitUserViewHolder(itemGitUserBinding);
    }

    public static class GitUserViewHolder extends BaseRecyclerViewAdapter.ItemViewHolder {

        public GitUserViewHolder(ItemGitUserBinding itemGitUserBinding) {
            super(itemGitUserBinding.gitUserItem, itemGitUserBinding);
        }

        @Override
        public BaseItemViewModel getItemViewModel(ViewDataBinding viewDataBinding) {
            ItemGitUserBinding itemGitUserBinding = (ItemGitUserBinding) viewDataBinding;
            if (itemGitUserBinding.getGitUserItemViewModel() == null) {
                itemGitUserBinding.setGitUserItemViewModel(new GitUserItemViewModel(itemView.getContext()));
            }
            return itemGitUserBinding.getGitUserItemViewModel();
        }
    }
}
