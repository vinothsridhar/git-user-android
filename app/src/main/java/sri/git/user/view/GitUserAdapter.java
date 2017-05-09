package sri.git.user.view;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import sri.git.user.R;
import sri.git.user.databinding.ItemGitUserBinding;
import sri.git.user.model.GitUser;
import sri.git.user.viewmodel.GitUserItemViewModel;

/**
 * Created by sridhar on 9/5/17.
 */

public class GitUserAdapter extends RecyclerView.Adapter<GitUserAdapter.GitUserViewHolder> {

    private List<GitUser> gitUserList;

    public GitUserAdapter() {
        this.gitUserList = Collections.emptyList();
    }

    @Override
    public GitUserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemGitUserBinding itemGitUserBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_git_user, parent, false);
        return new GitUserViewHolder(itemGitUserBinding);
    }

    @Override
    public void onBindViewHolder(GitUserViewHolder holder, int position) {
        holder.bind(gitUserList.get(position));
    }

    @Override
    public int getItemCount() {
        return gitUserList.size();
    }

    public void setGitUserList(List<GitUser> gitUserList) {
        this.gitUserList = gitUserList;
        notifyDataSetChanged();
    }

    static class GitUserViewHolder extends RecyclerView.ViewHolder {

        private ItemGitUserBinding itemGitUserBinding;

        public GitUserViewHolder(ItemGitUserBinding itemGitUserBinding) {
            super(itemGitUserBinding.gitUserItem);
            this.itemGitUserBinding = itemGitUserBinding;
        }

        void bind(GitUser user) {
            if (itemGitUserBinding.getGitUserItemViewModel() == null) {
                itemGitUserBinding.setGitUserItemViewModel(new GitUserItemViewModel(itemView.getContext(), user));
            } else {
                itemGitUserBinding.getGitUserItemViewModel().setGitUser(user);
            }
        }
    }
}
