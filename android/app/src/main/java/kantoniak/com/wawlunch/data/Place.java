package kantoniak.com.wawlunch.data;

import com.google.gson.annotations.SerializedName;

public class Place {

    @SerializedName("id")
    private final int id;

    @SerializedName("name")
    private final String name;

    @SerializedName("latitude")
    private final double latitude;

    @SerializedName("longitude")
    private final double longitude;

    @SerializedName("street")
    private final String street;

    @SerializedName("picture")
    private final String picture;

    public Place(int id, String name, double latitude, double longitude, String street, String picture) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.street = street;
        this.picture = picture;
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

    public String getStreet() {
        return street;
    }

    public String getPicture() {
        return picture;
    }

    @Override
    public String toString() {
        return "[" + getName() + "]";
    }
}
