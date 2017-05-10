package sri.git.user.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import sri.git.user.utils.L;

/**
 * Created by sridhar on 10/5/17.
 */

public class BaseActivity extends AppCompatActivity {

    protected final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        L.d(TAG, "inside onCreate");
        super.onCreate(savedInstanceState);
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
}
