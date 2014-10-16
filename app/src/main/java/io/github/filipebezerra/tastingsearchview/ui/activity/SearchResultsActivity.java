package io.github.filipebezerra.tastingsearchview.ui.activity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.github.filipebezerra.tastingsearchview.R;
import io.github.filipebezerra.tastingsearchview.provider.SuggestionsProvider;

public class SearchResultsActivity extends BaseActivity {

    private static final String TAG = SearchResultsActivity.class.getName();

    private TextView searchResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        searchResult = (TextView) findViewById(R.id.search_result);
        searchResult.setMovementMethod(new ScrollingMovementMethod());

        handleIntent(getIntent());
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "event <onPause> called");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "event <onRestart> called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "event <onResume> called");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(TAG, "event <onNewIntent> called");

        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        Log.d(TAG, String.format("method <handleIntent> called with action <%s>", intent.getAction()));

        if (Intent.ACTION_SEARCH == intent.getAction()) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                    SuggestionsProvider.AUTHORITY, SuggestionsProvider.MODE);
            suggestions.saveRecentQuery(query, null);

            StringBuilder sb = new StringBuilder(searchResult.getText());

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss ");

            sb.append(String.format("%s - Pesquisou a palavra %s",
                    formatter.format(new Date()), query));

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
