package sri.git.user.viewmodel;

import android.databinding.BaseObservable;

/**
 * Created by sridhar on 10/5/17.
 */

public abstract class BaseItemViewModel<T> extends BaseObservable {

    public abstract void setItem(T item);

}
