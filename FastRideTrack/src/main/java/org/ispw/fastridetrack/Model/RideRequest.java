package org.ispw.fastridetrack.Model;

public class RideRequest {
    private int requestId;
    private Client client;
    private String pickupLocation;
    private String destination;
    private String driverUsername;
    private String paymentMethod;

    public RideRequest(int requestId, Client client, String pickupLocation, String destination, String driverUsername, String paymentMethod) {
        this.requestId = requestId;
        this.client = client;
        this.pickupLocation = pickupLocation;
        this.destination = destination;
        this.driverUsername = driverUsername;
        this.paymentMethod = paymentMethod;
    }

    // Getters and Setters
    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDriverUsername() {
        return driverUsername;
    }

    public void setDriverUsername(String driverUsername) {
        this.driverUsername = driverUsername;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
