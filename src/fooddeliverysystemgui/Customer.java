package fooddeliverysystemgui;

import java.util.*;

public class Customer {
    private final String username;
    private final String password;
    private static int orderIdCounter = 1;
    private final List<Order> orders = new ArrayList<>();

    public Customer(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    public int getOrderId() {
        return orderIdCounter++;
    }

    public List<Order> getOrders() {
        return orders;
    }
}
