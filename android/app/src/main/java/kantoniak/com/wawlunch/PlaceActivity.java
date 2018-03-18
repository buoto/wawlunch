package kantoniak.com.wawlunch;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PlaceActivity extends AppCompatActivity implements MenuFragment.OnFragmentInteractionListener {

    private int placeId;

    private ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        Intent intent = getIntent();
        if (intent == null) {
            return;
        }

        setTitle(intent);
        setupSlider();
    }

    private void setTitle(Intent intent) {
        super.setTitle(intent.getStringExtra(ShowPlaceIntent.PLACE_NAME));
        placeId = intent.getIntExtra(ShowPlaceIntent.PLACE_ID, 0);
    }

    private void setupSlider() {
        mViewPager = findViewById(R.id.menus_slider);

        mViewPager.setClipToPadding(false);
        int menuSliderHorPaddingPx = (int) getResources().getDimension(R.dimen.menu_slider_hor_padding);
        int menuSliderVertPaddingPx = (int) getResources().getDimension(R.dimen.menu_slider_vert_padding);
        int menuSliderMenuMarginPx = (int) getResources().getDimension(R.dimen.menu_slider_menu_margin);
        mViewPager.setPadding(menuSliderHorPaddingPx, 0, menuSliderHorPaddingPx, menuSliderVertPaddingPx);
        mViewPager.setPageMargin(menuSliderMenuMarginPx);

        mPagerAdapter = new MenuAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
