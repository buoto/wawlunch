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
            new Place(1, "Aioli", 52.2360099, 21.011496),
            new Place(2, "Orzo", 52.2223586, 21.0152604)
        );
    }

}
