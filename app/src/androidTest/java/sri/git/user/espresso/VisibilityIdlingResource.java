package sri.git.user.espresso;

import android.support.test.espresso.IdlingResource;
import android.view.View;

/**
 * Idling Resource for view visibility
 *
 * Created by sridhar on 11/5/17.
 */


public class VisibilityIdlingResource implements IdlingResource {

    private View v;
    private int visibility;
    private IdlingResource.ResourceCallback resourceCallback;

    public VisibilityIdlingResource(View v, int visibility) {
        if(visibility!=View.VISIBLE && visibility!=View.INVISIBLE && visibility!=View.GONE) {
            throw new IllegalArgumentException("Passed visibility is invalid");
        }

        this.v = v;
        this.visibility = visibility;
    }

    @Override
    public String getName() {
        return VisibilityIdlingResource.class.getSimpleName();
    }

    @Override
    public boolean isIdleNow() {
        boolean idle = v.getVisibility() == visibility;
        if (idle && resourceCallback != null) {
            resourceCallback.onTransitionToIdle();
        }
        return idle;
    }

    @Override
    public void registerIdleTransitionCallback(IdlingResource.ResourceCallback callback) {
        this.resourceCallback = callback;
    }

}
