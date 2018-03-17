package kantoniak.com.wawlunch.data;

public class Place {

    private final String name;

    public Place(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "[" + getName() + "]";
    }
}
