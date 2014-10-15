package io.github.filipebezerra.tastingsearchview;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.widget.SearchView;
import android.widget.Toast;

public class HomeActivity extends Activity {

    public static final String COME_FROM = "COME_FROM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // enable "type-to-search" functionality
        setDefaultKeyMode(DEFAULT_KEYS_SEARCH_LOCAL);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_global, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = (SearchView) menu.findItem(R.id.ic_action_search).getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setSubmitButtonEnabled(true);

        searchManager.setOnCancelListener(new SearchManager.OnCancelListener() {
            @Override
            public void onCancel() {
                Toast.makeText(HomeActivity.this, "OnCancelListener", Toast.LENGTH_SHORT).show();
            }
        });

        searchManager.setOnDismissListener(new SearchManager.OnDismissListener() {
            @Override
            public void onDismiss() {
                Toast.makeText(HomeActivity.this, "OnDismissListener", Toast.LENGTH_SHORT).show();
            }
        });

        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "HomeActivity>onPause", Toast.LENGTH_SHORT).show();
    }

    // is called when the user signals the desire to start a search
    @Override
    public boolean onSearchRequested() {
        Bundle appData = new Bundle();
        appData.putString(COME_FROM, HomeActivity.class.getSimpleName());
        startSearch(null, false, appData, false);

        Toast.makeText(this, "HomeActivity>onSearchRequested", Toast.LENGTH_SHORT).show();

        return true;
    }
}
