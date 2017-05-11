package sri.git.user.espresso;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import sri.git.user.R;
import sri.git.user.model.GitUser;
import sri.git.user.view.GitUserActivity;
import sri.git.user.view.GitUserRepoActivity;
import sri.git.user.view.adapter.GitUserAdapter;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by sridhar on 11/5/17.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class GitUserAppTest {

    private static final int TOTAL_USERS = 30; //this is what we get from https://api.github.com/users

    private Intent intent;

    @Rule
    public ActivityTestRule<GitUserActivity> gitUserActivityActivityTestRule = new ActivityTestRule(GitUserActivity.class);

    @Before
    public void setup() {
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        Context context = instrumentation.getTargetContext();
        intent = new Intent(context, GitUserActivity.class);
    }

    @Test
    public void preConditions() {
        gitUserActivityActivityTestRule.launchActivity(intent);
        //check view
        onView(ViewMatchers.withId(R.id.gitUserRecyclerView)).check(matches(Matchers.anything()));
        onView(withId(R.id.loadingProgressBar)).check(matches(Matchers.anything()));
        onView(withId(R.id.retryLinearLayout)).check(matches(Matchers.anything()));

        //retry should gone
        onView(withId(R.id.retryLinearLayout)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
    }

    @Test
    public void showProgress() {
        gitUserActivityActivityTestRule.launchActivity(intent);
        RecyclerView recyclerView = getGitUserRecyclerView();

        onView(withId(R.id.loadingProgressBar)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));

        IdlingResource idlingResource = new RecyclerViewItemCountIdlingResource(recyclerView, 1);
        Espresso.registerIdlingResources(idlingResource);
        onView(withId(R.id.loadingProgressBar)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        Espresso.unregisterIdlingResources(idlingResource);
    }

    @Test
    public void showRetry() {
        intent.putExtra(GitUserActivity.RETRY_MODE_KEY, true);
        gitUserActivityActivityTestRule.launchActivity(intent);

        LinearLayout retryLinearLayout = (LinearLayout) gitUserActivityActivityTestRule.getActivity().findViewById(R.id.retryLinearLayout);

        onView(withId(R.id.retryButton)).perform(ViewActions.click());

        IdlingResource visibilityIdlingResource1 = new VisibilityIdlingResource(retryLinearLayout, View.GONE);
        Espresso.registerIdlingResources(visibilityIdlingResource1);
        onView(withId(R.id.loadingProgressBar)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        Espresso.unregisterIdlingResources(visibilityIdlingResource1);
    }

    @Test
    public void loadItems() {
        gitUserActivityActivityTestRule.launchActivity(intent);
        RecyclerView recyclerView = getGitUserRecyclerView();

        IdlingResource idlingResource = new RecyclerViewItemCountIdlingResource(recyclerView, 1);
        Espresso.registerIdlingResources(idlingResource);
        onView(withId(R.id.gitUserRecyclerView)).perform(scrollToPosition(15));
        Espresso.unregisterIdlingResources(idlingResource);
        onView(withId(R.id.gitUserRecyclerView)).check(matches(CustomMatchers.withListItemCount(TOTAL_USERS)));
    }

    @Test
    public void showGitUser() {
        gitUserActivityActivityTestRule.launchActivity(intent);
        RecyclerView recyclerView = getGitUserRecyclerView();

        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        Instrumentation.ActivityMonitor receiverActivityMonitor = instrumentation.addMonitor(GitUserRepoActivity.class.getName(), null, false);

        IdlingResource idlingResource = new RecyclerViewItemCountIdlingResource(recyclerView, 1);
        Espresso.registerIdlingResources(idlingResource);
        onView(withId(R.id.gitUserRecyclerView)).check(matches(Matchers.anything()));
        Espresso.unregisterIdlingResources(idlingResource);

        GitUserAdapter gitUserAdapter = (GitUserAdapter) recyclerView.getAdapter();
        GitUser gitUser = (GitUser) gitUserAdapter.getItem(0);

        onView(withId(R.id.gitUserRecyclerView)).perform(actionOnItemAtPosition(0, ViewActions.click()));

        //waiting for GitUserRepoActivity to show up
        AppCompatActivity gitUserRepoActivity = (AppCompatActivity) receiverActivityMonitor.waitForActivityWithTimeout(1000);
        instrumentation.removeMonitor(receiverActivityMonitor);
        Assert.assertNotNull("launched activity is null", gitUserRepoActivity);
        Assert.assertEquals("Launched Activity is not GitUserRepoActivity", GitUserRepoActivity.class, gitUserRepoActivity.getClass());

        String expectedTitle = gitUser.getLogin();
        Assert.assertEquals(expectedTitle, gitUserRepoActivity.getSupportActionBar().getTitle());
    }

    private RecyclerView getGitUserRecyclerView() {
        return (RecyclerView) gitUserActivityActivityTestRule.getActivity().findViewById(R.id.gitUserRecyclerView);
    }
}
