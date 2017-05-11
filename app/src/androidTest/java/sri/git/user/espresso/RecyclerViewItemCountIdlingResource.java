package sri.git.user.espresso;

import android.support.test.espresso.IdlingResource;
import android.support.v7.widget.RecyclerView;

/**
 * IdlingResource for recyclerview count
 *
 * Created by sridhar on 11/5/17.
 */

public class RecyclerViewItemCountIdlingResource implements IdlingResource {

    private ResourceCallback resourceCallback;
    private RecyclerView recyclerView;
    private int target;

    public RecyclerViewItemCountIdlingResource(RecyclerView recyclerView, int target) {
        this.recyclerView = recyclerView;
        this.target = target;
    }

    @Override
    public String getName() {
        return RecyclerViewItemCountIdlingResource.class.getSimpleName();
    }

    @Override
    public boolean isIdleNow() {
        boolean idle = isItemLoaded(recyclerView);
        if (idle && resourceCallback != null) {
            resourceCallback.onTransitionToIdle();
        }
        return idle;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        this.resourceCallback = callback;
    }

    private boolean isItemLoaded(RecyclerView list) {
        return list.getAdapter()!=null && list.getAdapter().getItemCount() >= target;
    }

}
