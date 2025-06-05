package org.ispw.fastridetrack.Bean;

import org.ispw.fastridetrack.Model.Client;
import org.ispw.fastridetrack.Model.Coordinate;
import org.ispw.fastridetrack.Model.UserType;

public class ClientBean extends UserBean {
    private String paymentMethod;


    public ClientBean(String username, String password, Integer userID, String name,
                      String email, String phoneNumber, double latitude, double longitude, String paymentMethod) {
        super(username, password, UserType.CLIENT, userID, name, email, phoneNumber, latitude, longitude);
        this.paymentMethod = paymentMethod;
    }

    public ClientBean(String username, String password, int userID, String name,
                      String email, String phoneNumber,
                      CoordinateBean coordinate,
                      String paymentMethod) {
        this(username, password, userID, name, email, phoneNumber,
                coordinate.getLatitude(), coordinate.getLongitude(),
                paymentMethod);
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }


    public static ClientBean fromModel(Client client) {
        if (client == null) return null;

        return new ClientBean(
                client.getUsername(),
                client.getPassword(),
                client.getUserID(),
                client.getName(),
                client.getEmail(),
                client.getPhoneNumber(),
                new CoordinateBean(client.getCoordinate()),
                client.getPaymentMethod()
        );
    }

    public Client toModel() {
        Coordinate coordinate = getCoordinate() != null ? getCoordinate().toModel() : null;
        return new Client(
                getUserID(),
                getUsername(),
                getPassword(),
                getName(),
                getEmail(),
                getPhoneNumber(),
                coordinate,
                getPaymentMethod()
        );

    }
}


