package fooddeliverysystemgui;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class FoodDeliverySystemGUI extends JFrame {
    private static final Map<String, Customer> customers = new HashMap<>();
    private static final Map<String, Restaurant> restaurants = new HashMap<>();
    private static final Map<String, DeliveryRider> riders = new HashMap<>();
    private static final Map<Integer, Order> orders = new HashMap<>();
    private static final Map<DeliveryRider, List<Order>> riderOrders = new HashMap<>();

    public FoodDeliverySystemGUI() {
        setTitle("Food Delivery System");
        setSize(400, 300);
        setLocationRelativeTo(null); 
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1));

        JButton loginCustomerButton = new JButton("Login as Customer");
        JButton loginRestaurantButton = new JButton("Login as Restaurant");
        JButton loginRiderButton = new JButton("Login as Rider");
        JButton createCustomerButton = new JButton("Create Customer Account");
        JButton createRestaurantButton = new JButton("Create Restaurant Account");
        JButton createRiderButton = new JButton("Create Delivery Rider Account");

        loginCustomerButton.addActionListener(e -> loginCustomer());
        loginRestaurantButton.addActionListener(e -> loginRestaurant());
        loginRiderButton.addActionListener(e -> loginRider());
        createCustomerButton.addActionListener(e -> createCustomerAccount());
        createRestaurantButton.addActionListener(e -> createRestaurantAccount());
        createRiderButton.addActionListener(e -> createRiderAccount());

        panel.add(loginCustomerButton);
        panel.add(loginRestaurantButton);
        panel.add(loginRiderButton);
        panel.add(createCustomerButton);
        panel.add(createRestaurantButton);
        panel.add(createRiderButton);

        add(panel, BorderLayout.CENTER);
    }

    private void createCustomerAccount() {
        String username = JOptionPane.showInputDialog(this, "Enter a Username:");
        String password = JOptionPane.showInputDialog(this, "Enter a Password:");

        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Invalid input. Try again.");
            return;
        }

        if (customers.containsKey(username)) {
            JOptionPane.showMessageDialog(this, "Username already exists.");
        } else {
            customers.put(username, new Customer(username, password));
            JOptionPane.showMessageDialog(this, "Customer account created successfully!");
        }
    }

    private void createRestaurantAccount() {
        String username = JOptionPane.showInputDialog(this, "Enter a Restaurant Name:");
        String password = JOptionPane.showInputDialog(this, "Enter a Password:");

        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Invalid input. Try again.");
            return;
        }

        if (restaurants.containsKey(username)) {
            JOptionPane.showMessageDialog(this, "Restaurant already exists.");
        } else {
            restaurants.put(username, new Restaurant(username, password));
            JOptionPane.showMessageDialog(this, "Restaurant account created successfully!");
        }
    }

    private void createRiderAccount() {
        String username = JOptionPane.showInputDialog(this, "Enter a Username:");
        String password = JOptionPane.showInputDialog(this, "Enter a Password:");

        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Invalid input. Try again.");
            return;
        }

        if (riders.containsKey(username)) {
            JOptionPane.showMessageDialog(this, "Username already exists.");
        } else {
            riders.put(username, new DeliveryRider(username, password));
            JOptionPane.showMessageDialog(this, "Rider account created successfully!");
        }
    }

    private void loginCustomer() {
        String username = JOptionPane.showInputDialog(this, "Enter Username:");
        String password = JOptionPane.showInputDialog(this, "Enter Password:");

        Customer customer = customers.get(username);
        if (customer != null && customer.login(username, password)) {
            JOptionPane.showMessageDialog(this, "Login successful!");
            customerMenu(customer);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials.");
        }
    }

    private void loginRestaurant() {
        String username = JOptionPane.showInputDialog(this, "Enter Username:");
        String password = JOptionPane.showInputDialog(this, "Enter Password:");

        Restaurant restaurant = restaurants.get(username);
        if (restaurant != null && restaurant.login(username, password)) {
            JOptionPane.showMessageDialog(this, "Login successful!");
            restaurantMenu(restaurant);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials.");
        }
    }

    private void loginRider() {
        String username = JOptionPane.showInputDialog(this, "Enter Username:");
        String password = JOptionPane.showInputDialog(this, "Enter Password:");

        DeliveryRider rider = riders.get(username);
        if (rider != null && rider.login(username, password)) {
            JOptionPane.showMessageDialog(this, "Login successful!");
            riderMenu(rider);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials.");
        }
    }

    private void customerMenu(Customer customer) {
        String[] options = {"Place Order", "View Orders", "Logout"};
        OUTER:
        while (true) {
            int choice = JOptionPane.showOptionDialog(
                    this, "Select an option:", "Customer Menu",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
            switch (choice) {
                case 0 -> placeOrder(customer);
                case 1 -> viewOrders(customer);
                default -> {
                    break OUTER;
                }
            }
        }
    }

private void restaurantMenu(Restaurant restaurant) {
    String[] options = {"Add Item to Menu", "View Orders", "Logout"};
        OUTER:
        while (true) {
            int choice = JOptionPane.showOptionDialog(
                    this, "Select an option:", "Restaurant Menu",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
            switch (choice) {
                case 0 -> addMenuItem(restaurant);
                case 1 -> viewRestaurantOrders(restaurant);
                default -> {
                    break OUTER;
            }
            }
        }
}


    private void riderMenu(DeliveryRider rider) {
        String[] options = {"Accept Order", "Logout"};
        while (true) {
            int choice = JOptionPane.showOptionDialog(
                    this, "Select an option:", "Rider Menu",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

            if (choice == 0) {
                acceptOrder(rider);
            } else {
                break;
            }
        }
    }

    private void placeOrder(Customer customer) {
        if (restaurants.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No restaurants available.");
            return;
        }

        String[] restaurantNames = restaurants.keySet().toArray(new String[0]);
        String selectedRestaurant = (String) JOptionPane.showInputDialog(
                this, "Choose a restaurant:", "Place Order",
                JOptionPane.QUESTION_MESSAGE, null, restaurantNames, restaurantNames[0]);

        if (selectedRestaurant != null) {
            Restaurant restaurant = restaurants.get(selectedRestaurant);

            if (restaurant.getMenu().isEmpty()) {
                JOptionPane.showMessageDialog(this, "No items available in the menu.");
                return;
            }

            String[] menuItems = restaurant.getMenu().toArray(new String[0]);
            String selectedItem = (String) JOptionPane.showInputDialog(
                    this, "Choose an item:", "Place Order",
                    JOptionPane.QUESTION_MESSAGE, null, menuItems, menuItems[0]);

            if (selectedItem != null) {
                Order order = new Order(customer, restaurant, selectedItem);
                orders.put(order.getOrderId(), order);
                customer.addOrder(order);
                JOptionPane.showMessageDialog(this, "Order placed successfully!");
            }
        }
    }

    private void viewOrders(Customer customer) {
        if (customer.getOrders().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No orders found.");
            return;
        }

        StringBuilder orderDetails = new StringBuilder("Your Orders:\n");
        for (Order order : customer.getOrders()) {
            orderDetails.append("Order ID: ").append(order.getOrderId())
                    .append(", Restaurant: ").append(order.getRestaurant().getRestaurantName())
                    .append(", Item: ").append(order.getMenuItem()).append("\n");
        }
        JOptionPane.showMessageDialog(this, orderDetails.toString());
    }

    private void viewRestaurantOrders(Restaurant restaurant) {
        List<Order> restaurantOrders = orders.values().stream()
                .filter(order -> order.getRestaurant().equals(restaurant))
                .toList();

        if (restaurantOrders.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No orders found.");
            return;
        }

        StringBuilder orderDetails = new StringBuilder("Orders:\n");
        for (Order order : restaurantOrders) {
            orderDetails.append("Order ID: ").append(order.getOrderId())
                    .append(", Customer: ").append(order.getCustomer().getUsername())
                    .append(", Item: ").append(order.getMenuItem()).append("\n");
        }
        JOptionPane.showMessageDialog(this, orderDetails.toString());
    }
    
    private void addMenuItem(Restaurant restaurant) {
    String newItem = JOptionPane.showInputDialog(this, "Enter the name of the menu item to add:");

    if (newItem == null || newItem.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Invalid input. Try again.");
        return;
    }

    restaurant.addMenuItem(newItem);
    JOptionPane.showMessageDialog(this, "Item added to the menu successfully!");
}




    private void acceptOrder(DeliveryRider rider) {
        List<Order> unassignedOrders = orders.values().stream()
                .filter(order -> riderOrders.values().stream().flatMap(List::stream).noneMatch(o -> o.equals(order)))
                .toList();

        if (unassignedOrders.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No unassigned orders available.");
            return;
        }

        String[] orderDetails = unassignedOrders.stream()
                .map(order -> "Order ID: " + order.getOrderId() + ", Item: " + order.getMenuItem())
                .toArray(String[]::new);

        String selectedOrderInfo = (String) JOptionPane.showInputDialog(
                this, "Select an order:", "Accept Order",
                JOptionPane.QUESTION_MESSAGE, null, orderDetails, orderDetails[0]);

        if (selectedOrderInfo != null) {
            int selectedOrderId = Integer.parseInt(selectedOrderInfo.split(":")[1].split(",")[0].trim());
            Order selectedOrder = unassignedOrders.stream()
                    .filter(order -> order.getOrderId() == selectedOrderId)
                    .findFirst().orElse(null);

            if (selectedOrder != null) {
                riderOrders.computeIfAbsent(rider, k -> new ArrayList<>()).add(selectedOrder);
                JOptionPane.showMessageDialog(this, "Order accepted successfully!");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FoodDeliverySystemGUI app = new FoodDeliverySystemGUI();
            app.setVisible(true);
        });
    }
}