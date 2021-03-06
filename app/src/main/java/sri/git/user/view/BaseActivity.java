package sri.git.user.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import sri.git.user.utils.L;
import sri.git.user.viewmodel.BaseViewModel;

/**
 * Created by sridhar on 10/5/17.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected final String TAG = this.getClass().getSimpleName();

    /**
     * BaseViewModel object
     * we use this in child activity
     */
    protected BaseViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        L.d(TAG, "inside onCreate");
        super.onCreate(savedInstanceState);

        viewModel = createViewModel();

        bindView();

        init();

        /**
         * calling this finally in onCreate so we have all necessary objects be initialized
         */
        if (viewModel != null) {
            viewModel.onCreate();
        }
    }

    /**
     * before call viewmodel if we need some initialization from activity
     */
    protected void init() {

    }

    @Override
    protected void onStart() {
        L.d(TAG, "inside onStart");
        super.onStart();
    }

    @Override
    protected void onStop() {
        L.d(TAG, "inside onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        L.d(TAG, "inside onDestroy");
        super.onDestroy();

        //clear viewmodel object
        if (viewModel != null) {
            viewModel.onDestroy();
            viewModel = null;
        }
    }

    @Override
    public void onBackPressed() {
        L.d(TAG, "inside onBackPressed");
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        L.d(TAG, "inside onPause");
        super.onPause();
    }

    @Override
    protected void onResume() {
        L.d(TAG, "inside onResume");
        super.onResume();
    }

    /**
     * Enable actionbar back button
     */
    protected void showBack() {
        if (getSupportActionBar() == null) {
            L.d(TAG, "support action bar is null");
            return;
        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
    }

    /**
     * create ViewModel from child activity
     * @return
     */
    public abstract BaseViewModel createViewModel();

    /**
     * android data binding with ViewModel
     */
    public abstract void bindView();
}
