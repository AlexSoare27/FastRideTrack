package org.ispw.fastridetrack.Model;

import org.ispw.fastridetrack.Bean.ClientBean;

public class Client extends User {
    private String paymentMethod;

    public Client(Integer userID, String username, String password, String name, String email,
                  String phoneNumber, String paymentMethod) {
        super(userID, username, password, name, email, phoneNumber, UserType.CLIENT);
        this.paymentMethod = paymentMethod;
    }

    public Client(Integer userID, String username, String password, String name, String email,
                  String phoneNumber, double latitude, double longitude, String paymentMethod) {
        super(userID, username, password, name, email, phoneNumber, UserType.CLIENT);
        this.setLatitude(latitude);
        this.setLongitude(longitude);
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public static Client fromBean(ClientBean bean) {
        if (bean == null) return null;

        return new Client(
                bean.getUserID(),
                bean.getUsername(),
                bean.getPassword(),
                bean.getName(),
                bean.getEmail(),
                bean.getPhoneNumber(),
                bean.getLatitude(),
                bean.getLongitude(),
                bean.getPaymentMethod()
        );
    }
}

