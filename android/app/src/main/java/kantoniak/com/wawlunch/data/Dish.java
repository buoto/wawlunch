package kantoniak.com.wawlunch.data;

import com.google.gson.annotations.SerializedName;

public class Dish {

    public interface Type {
        String SOUP = "soup";
        String MAIN = "main";
        String DESS = "dess";
        String DRIN = "drin";

        static String typeName(String type) {
            switch (type) {
                case SOUP: return "Zupa";
                case MAIN: return "Danie główne";
                case DESS: return "Deser";
                case DRIN: return "Napój";
                default: return null;
            }
        }
    }

    @SerializedName("id")
    private final int id;

    @SerializedName("menuId")
    private final int menuId;

    @SerializedName("name")
    private final String name;

    @SerializedName("type")
    private final String type;

    @SerializedName("isVege")
    private final boolean isVege;

    @SerializedName("extraPrice")
    private final float extraPrice;

    public Dish(int id, int menuId, String name, String type, boolean isVege, float extraPrice) {
        this.id = id;
        this.menuId = menuId;
        this.name = name;
        this.type = type;
        this.isVege = isVege;
        this.extraPrice = extraPrice;
    }

    public int getId() {
        return id;
    }

    public int getMenuId() {
        return menuId;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public boolean isVege() {
        return isVege;
    }

    public float getExtraPrice() {
        return extraPrice;
    }
}
