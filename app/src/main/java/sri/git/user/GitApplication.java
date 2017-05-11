package sri.git.user;

import android.app.Application;
import android.content.Context;

import sri.git.user.dagger.component.DaggerGitAppComponent;
import sri.git.user.dagger.component.GitAppComponent;
import sri.git.user.dagger.module.ContextModule;

/**
 * Created by sridhar on 9/5/17.
 */

public class GitApplication extends Application {

    private GitAppComponent gitAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        gitAppComponent = DaggerGitAppComponent.builder()
                .contextModule(new ContextModule(this))
                .build();
    }

    public GitAppComponent getGitAppComponent() {
        return gitAppComponent;
    }

    public static GitApplication get(Context context) {
        return (GitApplication) context.getApplicationContext();
    }

    public static GitApplication create(Context context) {
        return GitApplication.get(context);
    }
}
