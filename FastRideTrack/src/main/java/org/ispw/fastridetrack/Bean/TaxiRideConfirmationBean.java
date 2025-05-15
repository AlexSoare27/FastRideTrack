package org.ispw.fastridetrack.Bean;

import org.ispw.fastridetrack.Model.Client;
import org.ispw.fastridetrack.Model.Driver;

import java.util.List;

public class TaxiRideConfirmationBean {
    private Integer rideID;
    private Driver driver;
    private Client client;
    private List<Double> userLocation; // [latitude, longitude]
    private String destination;
    private String status;
    private Float estimatedFare;
    private Float estimatedTime;
    private String paymentStatus;

    public TaxiRideConfirmationBean() {
        // Costruttore vuoto
    }

    public TaxiRideConfirmationBean(Integer rideID, Driver driver, Client client, List<Double> userLocation,
                                String destination, String status, Float estimatedFare,
                                Float estimatedTime, String paymentStatus) {
        this.rideID = rideID;
        this.driver = driver;
        this.client = client;
        this.userLocation = userLocation;
        this.destination = destination;
        this.status = status;
        this.estimatedFare = estimatedFare;
        this.estimatedTime = estimatedTime;
        this.paymentStatus = paymentStatus;
    }

    public Integer getRideID() {
        return rideID;
    }

    public void setRideID(Integer rideID) {
        this.rideID = rideID;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<Double> getUserLocation() {
        return userLocation;
    }

    public void setUserLocation(List<Double> userLocation) {
        this.userLocation = userLocation;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Float getEstimatedFare() {
        return estimatedFare;
    }

    public void setEstimatedFare(Float estimatedFare) {
        this.estimatedFare = estimatedFare;
    }

    public Float getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(Float estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public void setRideRequest(RideRequestBean rideRequest) {

    }
}
