package kantoniak.com.wawlunch;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import kantoniak.com.wawlunch.data.Place;
import kantoniak.com.wawlunch.data.PlaceProvider;

public class SearchActivity extends Activity {

    private static final String TAG = SearchActivity.class.getSimpleName();

    private PlaceProvider mPlaceProvider = PlaceProvider.getInstance();
    private RecyclerView mSearchResultsRecyclerView;
    private SearchResultAdapter mSearchResultAdapter = new SearchResultAdapter();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        setupSearchResults();
    }

    private void setupSearchResults() {
        mSearchResultsRecyclerView = findViewById(R.id.search_result_list);
        mSearchResultsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mSearchResultsRecyclerView.setAdapter(mSearchResultAdapter);

        // TODO(kantoniak): Please be async
        mSearchResultAdapter.updatePlaces(mPlaceProvider.getPlaces());
    }
}
