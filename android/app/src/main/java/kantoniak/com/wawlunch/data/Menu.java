package kantoniak.com.wawlunch.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Menu {

    @SerializedName("id")
    private final int id;

    @SerializedName("placeId")
    private final int placeId;

    @SerializedName("price")
    private final float price;

    @SerializedName("dishes")
    private List<Dish> dishes;

    public Menu(int id, int placeId, float price, List<Dish> dishes) {
        this.id = id;
        this.placeId = placeId;
        this.price = price;
        this.dishes = dishes;
    }

    public int getId() {
        return id;
    }

    public int getPlaceId() {
        return placeId;
    }

    public float getPrice() {
        return price;
    }

    public List<Dish> getDishes() {
        return dishes;
    }
}
