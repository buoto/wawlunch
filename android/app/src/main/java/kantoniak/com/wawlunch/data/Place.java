package kantoniak.com.wawlunch.data;

public class Place {

    private final int id;
    private final String name;
    private final double latitude;
    private final double longitude;

    public Place(int id, String name, double latitude, double longitude) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return "[" + getName() + "]";
    }
}
