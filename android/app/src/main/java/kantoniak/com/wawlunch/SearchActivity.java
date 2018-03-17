package kantoniak.com.wawlunch;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import kantoniak.com.wawlunch.data.Place;
import kantoniak.com.wawlunch.data.PlaceProvider;

public class SearchActivity extends Activity implements OnMapReadyCallback {

    private static final String TAG = SearchActivity.class.getSimpleName();

    private PlaceProvider mPlaceProvider = PlaceProvider.getInstance();
    private SearchResultAdapter mSearchResultAdapter = new SearchResultAdapter();;

    private RecyclerView mSearchResultsRecyclerView;
    private MapFragment mMapFragment;
    private GoogleMap googleMap;

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

        mMapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.search_result_map);
        mMapFragment.getMapAsync(this);
    }

    private void fetchResults() {
        List<Place> places = mPlaceProvider.getPlaces();

        // TODO(kantoniak): Please be async
        mSearchResultAdapter.updatePlaces(places);

        places.stream().forEach(p -> {
            googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(p.getLatitude(), p.getLongitude()))
                    .title(p.getName()));
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(52.2223586, 21.0152604), 13));
        fetchResults();
    }
}
