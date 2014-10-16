package io.github.filipebezerra.tastingsearchview.provider;

import android.content.SearchRecentSuggestionsProvider;

/**
 * Created by filipebezerra on 16/10/14.
 */
public class SuggestionsProvider extends SearchRecentSuggestionsProvider {
    public final static String AUTHORITY = "io.github.filipebezerra.tastingsearchview.provider.SuggestionsProvider";
    public final static int MODE = DATABASE_MODE_QUERIES;

    public SuggestionsProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }
}
