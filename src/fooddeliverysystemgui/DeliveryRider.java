package fooddeliverysystemgui;

public class DeliveryRider {
    private final String username;
    private final String password;

    public DeliveryRider(String username, String password) {
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
}
