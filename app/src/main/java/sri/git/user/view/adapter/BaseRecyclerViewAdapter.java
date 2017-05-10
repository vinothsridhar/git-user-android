package sri.git.user.view.adapter;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import sri.git.user.viewmodel.BaseItemViewModel;

/**
 * Created by sridhar on 10/5/17.
 */

public class BaseRecyclerViewAdapter<T, V extends BaseItemViewModel<T>> extends RecyclerView.Adapter<BaseRecyclerViewAdapter.ItemViewHolder<T, V>> {

    protected List<T> items;

    public BaseRecyclerViewAdapter() {
        items = Collections.emptyList();
    }

    public void refresh(List<T> list) {
        this.items = list;
        notifyDataSetChanged();
    }

    @Override
    public ItemViewHolder<T, V> onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        if (items.get(position) != null) {
            holder.bind(items.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public abstract static class ItemViewHolder<T, VT extends BaseItemViewModel<T>> extends RecyclerView.ViewHolder {

        private final ViewDataBinding viewDataBinding;

        public ItemViewHolder(View itemView, ViewDataBinding viewDataBinding) {
            super(itemView);
            this.viewDataBinding = viewDataBinding;
        }

        public void bind(T item) {
            VT itemViewModel = getItemViewModel(viewDataBinding);
            itemViewModel.setItem(item);
        }

        public abstract VT getItemViewModel(ViewDataBinding viewDataBinding);
    }

}
