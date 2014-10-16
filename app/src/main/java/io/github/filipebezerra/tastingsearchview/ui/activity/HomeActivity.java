package io.github.filipebezerra.tastingsearchview.ui.activity;

import android.os.Bundle;
import android.util.Log;

import io.github.filipebezerra.tastingsearchview.R;

public class HomeActivity extends BaseActivity {

    private static final String TAG = HomeActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "event <onPause> called");
    }

}
