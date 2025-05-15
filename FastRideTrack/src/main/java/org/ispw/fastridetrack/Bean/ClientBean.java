package org.ispw.fastridetrack.Bean;

import org.ispw.fastridetrack.Model.UserType;
import org.ispw.fastridetrack.Model.Client;

public class ClientBean extends UserBean {
    private String paymentMethod;

    public ClientBean(String username, String password, UserType userType, Integer userID, String name, String email, String phoneNumber, String paymentMethod) {
        super(username, password, userType, userID, name, email, phoneNumber,0,0);
        this.paymentMethod = paymentMethod;
    }

    public ClientBean(String username, String password, UserType userType, Integer userID, String name,
                      String email, String phoneNumber, double latitude, double longitude, String paymentMethod) {
        super(username, password, userType, userID, name, email, phoneNumber, latitude, longitude);
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public static ClientBean fromModel(Client client) {
        if (client == null) return null;

        return new ClientBean(
                client.getUsername(),
                client.getPassword(),
                client.getUserType(),
                client.getUserID(),
                client.getName(),
                client.getEmail(),
                client.getPhoneNumber(),
                client.getLatitude(),
                client.getLongitude(),
                client.getPaymentMethod()
        );
    }

}


