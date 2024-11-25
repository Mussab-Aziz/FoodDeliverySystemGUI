package fooddeliverysystemgui;


public class Order {
    private static int orderCounter = 1;
    private final int orderId;
    private final Customer customer;
    private final Restaurant restaurant;
    private final String menuItem;

    public Order(Customer customer, Restaurant restaurant, String menuItem) {
        this.orderId = orderCounter++;
        this.customer = customer;
        this.restaurant = restaurant;
        this.menuItem = menuItem;
    }

    public int getOrderId() {
        return orderId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public String getMenuItem() {
        return menuItem;
    }
}
