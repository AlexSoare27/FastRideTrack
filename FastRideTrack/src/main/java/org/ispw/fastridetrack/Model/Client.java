package org.ispw.fastridetrack.Model;

public class Client extends User {
    private String paymentMethod;

    public Client(Integer userID, String username, String password, String name, String email,
                  String phoneNumber, String paymentMethod) {
        super(userID, username, password, name, email, phoneNumber, UserType.CLIENT);
        this.paymentMethod = paymentMethod;
    }

    public Client(Integer userID, String username, String password, String name, String email,
                  String phoneNumber, Coordinate coordinate, String paymentMethod) {
        this(userID, username, password, name, email, phoneNumber, paymentMethod);
        if (coordinate != null) {
            setLatitude(coordinate.getLatitude());
            setLongitude(coordinate.getLongitude());
        }
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

}
