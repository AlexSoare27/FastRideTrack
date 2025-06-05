package org.ispw.fastridetrack.Model;

public class RideRequest {
    private Integer requestId;
    private Client client;
    private String pickupLocation;
    private String destination;


    public RideRequest(int requestId, Client client, String pickupLocation, String destination, String driverUsername, String paymentMethod) {
        this.requestId = requestId;
        this.client = client;
        this.pickupLocation = pickupLocation;
        this.destination = destination;
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

}
