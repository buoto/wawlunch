package kantoniak.com.wawlunch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.Arrays;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import kantoniak.com.wawlunch.data.Api;
import kantoniak.com.wawlunch.data.Place;

public class SearchActivity extends Activity implements OnMapReadyCallback {

    private final LocationListener locationListener = new LocationListener() {

        public void onLocationChanged(Location location) {
            if (location.getAccuracy() < 30) {
                LocationUtils.stopUpdates(activity, locationListener);
            }
            LatLng pos = new LatLng(location.getLatitude(), location.getLongitude());
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, DEFAULT_MAP_ZOOM));
            Log.i(TAG, "Loc update: " + location.getLatitude() + " " + location.getLongitude() + " " + location.getAccuracy());
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {}

        public void onProviderEnabled(String provider) {}

        public void onProviderDisabled(String provider) {}
    };

    private final View.OnClickListener resultClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Place place = (Place) view.getTag(R.id.list_item_place);
            startActivity(new ShowPlaceIntent(activity, place));
        }
    };

    private static final String TAG = SearchActivity.class.getSimpleName();
    private final static int DEFAULT_MAP_ZOOM = 11;

    private SearchResultAdapter mSearchResultAdapter = new SearchResultAdapter(resultClickListener);

    private RecyclerView mSearchResultsRecyclerView;
    private MapFragment mMapFragment;
    private GoogleMap googleMap;

    private final Activity activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ColorDrawable drawable = new ColorDrawable(getResources().getColor(R.color.colorPrimary));
        getActionBar().setBackgroundDrawable(drawable);
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
        Api.getInstance().get(Api.Method.PLACES, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String response) {
                Gson gson = new GsonBuilder().create();
                List<Place> places = Arrays.asList(gson.fromJson(response, Place[].class));
                Log.i(TAG, "SIZE: " + places.size());
                runOnUiThread(() -> {
                    mSearchResultAdapter.updatePlaces(places);
                    places.stream().forEach(p -> googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(p.getLatitude(), p.getLongitude()))
                        .title(p.getName())));
                });
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e(TAG, "FAIL HTTP " + statusCode, throwable);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        fetchResults();
        startCenteringMap();
    }

    private void startCenteringMap() {
        LatLng defPos = new LatLng(52.256202, 21.045592);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defPos, DEFAULT_MAP_ZOOM));

        if (LocationUtils.hasLocationPermission(this)) {
            LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (lastKnownLocation != null) {
                LatLng pos = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, DEFAULT_MAP_ZOOM));
            }

            LocationUtils.startFetchingLocation(this, locationListener);
        } else {
            LocationUtils.askForLocationPermission(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case LocationUtils.PERMISSION_REQ_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startCenteringMap();
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_account) {
            startActivityForResult(new Intent(this, AccountActivity.class), 0);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
