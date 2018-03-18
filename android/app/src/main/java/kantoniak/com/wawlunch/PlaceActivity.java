package kantoniak.com.wawlunch;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class PlaceActivity extends Activity {

    private int placeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        Intent intent = getIntent();
        if (intent == null) {
            return;
        }

        setTitle(intent.getStringExtra(ShowPlaceIntent.PLACE_NAME));
        placeId = intent.getIntExtra(ShowPlaceIntent.PLACE_ID, 0);
    }
}
