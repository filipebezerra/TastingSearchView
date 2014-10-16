package io.github.filipebezerra.tastingsearchview.ui.activity;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.view.Menu;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.github.filipebezerra.tastingsearchview.R;
import io.github.filipebezerra.tastingsearchview.provider.SuggestionsProvider;

public class SearchResultsActivity extends Activity {

    private TextView searchResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        searchResult = (TextView) findViewById(R.id.search_result);

        handleIntent(getIntent());

        // enable "type-to-search" functionality
        setDefaultKeyMode(DEFAULT_KEYS_SEARCH_LOCAL);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_global, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = (SearchView) menu.findItem(R.id.ic_action_search).getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchManager.setOnCancelListener(new SearchManager.OnCancelListener() {
            @Override
            public void onCancel() {
                Toast.makeText(SearchResultsActivity.this, "OnCancelListener", Toast.LENGTH_SHORT).show();
            }
        });

        searchManager.setOnDismissListener(new SearchManager.OnDismissListener() {
            @Override
            public void onDismiss() {
                Toast.makeText(SearchResultsActivity.this, "OnDismissListener", Toast.LENGTH_SHORT).show();
            }
        });

        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "SearchResultsActivity>onPause", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(this, "SearchResultsActivity>onRestart", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "SearchResultsActivity>onResume", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onSearchRequested() {
        Bundle appData = new Bundle();
        appData.putString(HomeActivity.COME_FROM, SearchResultsActivity.class.getSimpleName());
        startSearch(null, false, appData, false);

        Toast.makeText(this, "SearchResultsActivity>onSearchRequested", Toast.LENGTH_SHORT).show();

        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH == intent.getAction()) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                    SuggestionsProvider.AUTHORITY, SuggestionsProvider.MODE);
            suggestions.saveRecentQuery(query, null);

            StringBuilder sb = new StringBuilder(searchResult.getText());

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss ");

            sb.append(String.format("Pesquisou a palavra %s em %s",
                    query, formatter.format(new Date())));

            searchResult.setText(sb.toString());

            Bundle appData = getIntent().getBundleExtra(SearchManager.APP_DATA);

            if (appData != null) {
                String comeFrom = appData.getString(HomeActivity.COME_FROM);

                StringBuilder newSb = new StringBuilder(searchResult.getText());
                newSb.append(String.format("Extra data = %s", comeFrom));

                searchResult.setText(newSb.toString());
            }
        }
    }

}
