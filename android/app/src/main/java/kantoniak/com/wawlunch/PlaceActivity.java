package kantoniak.com.wawlunch;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.Arrays;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import kantoniak.com.wawlunch.data.Api;
import kantoniak.com.wawlunch.data.Menu;
import kantoniak.com.wawlunch.data.Place;

public class PlaceActivity extends AppCompatActivity implements MenuFragment.OnFragmentInteractionListener {

    private static final String TAG = PlaceActivity.class.getSimpleName();

    private int placeId;

    private ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;

    private final AppCompatActivity thisActivity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        Intent intent = getIntent();
        if (intent == null) {
            return;
        }
        placeId = intent.getIntExtra(ShowPlaceIntent.PLACE_ID, 0);

        super.setTitle(intent.getStringExtra(ShowPlaceIntent.PLACE_NAME));
        setupSlider();
        fetchMenus(placeId);
    }

    private void setupSlider() {
        mViewPager = findViewById(R.id.menus_slider);

        mViewPager.setClipToPadding(false);
        int menuSliderHorPaddingPx = (int) getResources().getDimension(R.dimen.menu_slider_hor_padding);
        int menuSliderVertPaddingPx = (int) getResources().getDimension(R.dimen.menu_slider_vert_padding);
        int menuSliderMenuMarginPx = (int) getResources().getDimension(R.dimen.menu_slider_menu_margin);
        mViewPager.setPadding(menuSliderHorPaddingPx, 0, menuSliderHorPaddingPx, menuSliderVertPaddingPx);
        mViewPager.setPageMargin(menuSliderMenuMarginPx);
    }

    private void fetchMenus(int placeId) {
        Api.getInstance().get(Api.Method.MENUS_PLACE_ID + Integer.toString(placeId), new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String response) {
                Gson gson = new GsonBuilder().create();
                List<Menu> menus = Arrays.asList(gson.fromJson(response, Menu[].class));
                Log.i(TAG, "SIZE: " + menus.size());
                runOnUiThread(() -> {
                    menus.stream().forEach(m -> Log.i(TAG, "DISHES " + m.getDishes().size()));
                    mPagerAdapter = new MenuAdapter(thisActivity.getSupportFragmentManager(), menus);
                    mViewPager.setAdapter(mPagerAdapter);
                });
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e(TAG, "FAIL HTTP " + statusCode, throwable);
            }
        });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
