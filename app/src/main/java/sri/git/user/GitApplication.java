package sri.git.user;

import android.app.Application;
import android.content.Context;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import sri.git.user.data.GitUserRestService;
import sri.git.user.data.RestFactory;

/**
 * Created by sridhar on 9/5/17.
 */

public class GitApplication extends Application {

    private Scheduler scheduler;
    private GitUserRestService gitUserRestService;

    public static GitApplication get(Context context) {
        return (GitApplication) context.getApplicationContext();
    }

    public static GitApplication create(Context context) {
        return GitApplication.get(context);
    }

    public Scheduler getScheduler() {
        if (scheduler == null) {
            scheduler = Schedulers.io();
        }

        return scheduler;
    }

    public GitUserRestService getRestFactory() {
        if (gitUserRestService == null) {
            gitUserRestService = RestFactory.create();
        }

        return gitUserRestService;
    }
}
