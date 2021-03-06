package io.github.filipebezerra.tastingsearchview.ui.activity;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import io.github.filipebezerra.tastingsearchview.R;
import io.github.filipebezerra.tastingsearchview.provider.SuggestionsProvider;

public class BaseActivity extends Activity {

    private static final String TAG = BaseActivity.class.getName();

    public static final String COME_FROM = "COME_FROM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // enable "type-to-search" functionality
        setDefaultKeyMode(DEFAULT_KEYS_SEARCH_LOCAL);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_base, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = (SearchView) menu.findItem(R.id.ic_action_search).getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);

        searchManager.setOnCancelListener(new SearchManager.OnCancelListener() {
            @Override
            public void onCancel() {
                Log.d(TAG, "event <setOnCancelListener> called");
            }
        });

        searchManager.setOnDismissListener(new SearchManager.OnDismissListener() {
            @Override
            public void onDismiss() {
                Log.d(TAG, "event <setOnDismissListener> called");
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            // For smaller screen the does not place a SearchView Widget on ActionBar, but a
            // a menu in the overflow menu.
            case R.id.ic_action_search:
                onSearchRequested();
                return true;

            case R.id.ic_action_clear_suggestions:
                SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                        SuggestionsProvider.AUTHORITY, SuggestionsProvider.MODE);
                suggestions.clearHistory();
                Toast.makeText(this, "Sugestões de pesquisa removidas", Toast.LENGTH_LONG).show();
                return true;

            default:
                return false;
        }
    }

    // is called when the user signals the desire to start a search
    @Override
    public boolean onSearchRequested() {
        Log.d(TAG, "event <onSearchRequested> called");

        Bundle appData = new Bundle();
        appData.putString(COME_FROM, TAG);
        startSearch(null, false, appData, false);

        return true;
    }

}
