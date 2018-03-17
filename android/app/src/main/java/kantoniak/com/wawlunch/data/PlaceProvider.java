package kantoniak.com.wawlunch.data;

import java.util.Arrays;
import java.util.List;

/** Fetches list of places. */
public class PlaceProvider {

    private static PlaceProvider sInstance;

    public static PlaceProvider getInstance() {
        if (sInstance == null) {
            sInstance = new PlaceProvider();
        }
        return sInstance;
    }

    public List<Place> getPlaces() {
        return Arrays.asList(
                new Place("Place 1"),
                new Place("Place 2")
        );
    }

}
