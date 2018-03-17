package kantoniak.com.wawlunch;

import android.content.Context;
import android.content.Intent;

import kantoniak.com.wawlunch.data.Place;

public class ShowPlaceIntent extends Intent {

    public static final String PLACE_ID = "kantoniak.com.wawlunch.PLACE_ID";
    public static final String PLACE_NAME = "kantoniak.com.wawlunch.PLACE_NAME";

    public ShowPlaceIntent(Context packageContext, Place place) {
        super(packageContext, PlaceActivity.class);
        this.putExtra(PLACE_ID, place.getId());
        this.putExtra(PLACE_NAME, place.getName());
    }
}
