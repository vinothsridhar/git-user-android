package sri.git.user.espresso;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import sri.git.user.R;
import sri.git.user.model.GitUser;
import sri.git.user.view.GitUserRepoActivity;
import sri.git.user.view.adapter.GitUserRepoAdapter;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.anything;

/**
 * Created by sridhar on 11/5/17.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class GitUserRepoActivityTest {

    private Intent intent;

    @Rule
    public ActivityTestRule<GitUserRepoActivity> gitUserRepoActivityActivityTestRule = new ActivityTestRule<GitUserRepoActivity>(GitUserRepoActivity.class, true, false);

    @Before
    public void setup() {
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        Context context = instrumentation.getTargetContext();
        intent = new Intent(context, GitUserRepoActivity.class);
    }

    @Test
    public void preConditions() {
        //load activity
        GitUser gitUser = getTempGitUser();
        intent.putExtra(GitUserRepoActivity.EXTRA_USER, gitUser);
        gitUserRepoActivityActivityTestRule.launchActivity(intent);

        //check view existence
        onView(withId(R.id.avatar)).check(matches(anything()));
        onView(withId(R.id.gitUserRepoRecyclerView)).check(matches(anything()));

        AppCompatActivity gitUserRepoActivity = (AppCompatActivity) gitUserRepoActivityActivityTestRule.getActivity();

        //check title
        Assert.assertEquals(gitUser.getLogin(), gitUserRepoActivity.getSupportActionBar().getTitle());

        //check progress bar exists
        RecyclerView recyclerView = getGitUserRepoRecyclerView();
        IdlingResource idlingResource = new RecyclerViewItemCountIdlingResource(recyclerView, 1);
        Espresso.registerIdlingResources(idlingResource);
        int expectedType = GitUserRepoAdapter.VIEW_PROGRESS;
        Assert.assertEquals(expectedType, recyclerView.getAdapter().getItemViewType(0));
        Espresso.unregisterIdlingResources(idlingResource);
    }

    @Test
    public void repoLoad() {
        //load activity
        GitUser gitUser = getTempGitUser();
        intent.putExtra(GitUserRepoActivity.EXTRA_USER, gitUser);
        gitUserRepoActivityActivityTestRule.launchActivity(intent);

        RecyclerView recyclerView = getGitUserRepoRecyclerView();

        IdlingResource idlingResource = new RecyclerViewItemCountIdlingResource(recyclerView, 5);
        Espresso.registerIdlingResources(idlingResource);
        onView(withId(R.id.gitUserRepoRecyclerView)).perform(RecyclerViewActions.scrollToPosition(10));
        Espresso.unregisterIdlingResources(idlingResource);
    }

    @Test
    public void repoLoadMore() {
        //load activity
        GitUser gitUser = getTempGitUser();
        intent.putExtra(GitUserRepoActivity.EXTRA_USER, gitUser);
        gitUserRepoActivityActivityTestRule.launchActivity(intent);

        RecyclerView recyclerView = getGitUserRepoRecyclerView();
        IdlingResource idlingResource = new RecyclerViewItemCountIdlingResource(recyclerView, 5);
        Espresso.registerIdlingResources(idlingResource);
        onView(withId(R.id.gitUserRepoRecyclerView)).perform(RecyclerViewActions.scrollToPosition(recyclerView.getAdapter().getItemCount() - 1));
        Espresso.unregisterIdlingResources(idlingResource);

        int count = recyclerView.getAdapter().getItemCount();
        int expectedType = GitUserRepoAdapter.VIEW_PROGRESS;

        idlingResource = new RecyclerViewItemCountIdlingResource(recyclerView, count + 1);
        Espresso.registerIdlingResources(idlingResource);
        Assert.assertEquals(expectedType, recyclerView.getAdapter().getItemViewType(count + 1));
        Espresso.unregisterIdlingResources(idlingResource);
    }

    private GitUser getTempGitUser() {
        GitUser gitUser = new GitUser();
        gitUser.setLogin("mojombo");
        gitUser.setAvatarUrl("https://avatars3.githubusercontent.com/u/1?v=3");
        gitUser.setReposUrl("https://api.github.com/users/mojombo/repos");
        gitUser.setType("User");

        return gitUser;
    }

    private RecyclerView getGitUserRepoRecyclerView() {
        return (RecyclerView) gitUserRepoActivityActivityTestRule.getActivity().findViewById(R.id.gitUserRepoRecyclerView);
    }

}
