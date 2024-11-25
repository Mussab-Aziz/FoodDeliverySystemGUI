
package fooddeliverysystemgui;
import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    private final String restaurantName;
    private final String password;
    private final List<String> menu;

    public Restaurant(String restaurantName, String password) {
        this.restaurantName = restaurantName;
        this.password = password;
        this.menu = new ArrayList<>();
    }

    public boolean login(String username, String password) {
        return this.restaurantName.equals(username) && this.password.equals(password);
    }

    public void addMenuItem(String item) {
        menu.add(item);
    }

    public List<String> getMenu() {
        return menu;
    }

    public String getRestaurantName() {
        return restaurantName;
    }
}

