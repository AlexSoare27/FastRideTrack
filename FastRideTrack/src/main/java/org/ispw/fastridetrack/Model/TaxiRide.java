package org.ispw.fastridetrack.Model;


public class TaxiRide {
    private int rideId;
    private int requestId;
    private String driverUsername;
    private String clientUsername;
    private double fare;
    private boolean completed;

    // Costruttore completo
    public TaxiRide(int rideId, int requestId, String driverUsername, String clientUsername, double fare, boolean completed) {
        this.rideId = rideId;
        this.requestId = requestId;
        this.driverUsername = driverUsername;
        this.clientUsername = clientUsername;
        this.fare = fare;
        this.completed = completed;
    }

    // Costruttore da RideRequest
    public TaxiRide(RideRequest request, double fare) {
        this.requestId = request.getRequestId();
        this.clientUsername = request.getClient().getUsername();
        this.driverUsername = request.getDriverUsername();
        this.fare = fare;
        this.completed = false; // default
    }

    // Getters e Setters
    public int getRideId() {
        return rideId;
    }

    public void setRideId(int rideId) {
        this.rideId = rideId;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public String getDriverUsername() {
        return driverUsername;
    }

    public void setDriverUsername(String driverUsername) {
        this.driverUsername = driverUsername;
    }

    public String getClientUsername() {
        return clientUsername;
    }

    public void setClientUsername(String clientUsername) {
        this.clientUsername = clientUsername;
    }

    public double getFare() {
        return fare;
    }

    public void setFare(double fare) {
        this.fare = fare;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}

