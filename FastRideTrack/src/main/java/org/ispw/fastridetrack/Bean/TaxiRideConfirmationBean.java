package org.ispw.fastridetrack.Bean;

import org.ispw.fastridetrack.Model.Client;
import org.ispw.fastridetrack.Model.Driver;

import java.time.LocalDateTime;

public class TaxiRideConfirmationBean {
    private Integer rideID;
    private Driver driver;
    private Client client;
    private CoordinateBean userLocation; // Ora CoordinateBean invece di String
    private String destination;
    private String status;
    private Double estimatedFare;
    private Double estimatedTime;
    private String paymentStatus;
    private LocalDateTime confirmationTime;


    // Costruttore completo
    public TaxiRideConfirmationBean(Integer rideID, Driver driver, Client client, CoordinateBean userLocation,
                                    String destination, String status, double estimatedFare,
                                    double estimatedTime, String paymentStatus, LocalDateTime confirmationTime) {
        this.rideID = rideID;
        this.driver = driver;
        this.client = client;
        this.userLocation = userLocation;
        this.destination = destination;
        this.status = status;
        this.estimatedFare = estimatedFare;
        this.estimatedTime = estimatedTime;
        this.paymentStatus = paymentStatus;
        this.confirmationTime = confirmationTime;
    }


    // Getter e setter aggiornati
    public Integer getRideID() {
        return rideID;
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

    public CoordinateBean getUserLocation() {
        return userLocation;
    }

    public void setUserLocation(CoordinateBean userLocation) {
        this.userLocation = userLocation;
    }

    public String getDestination() {
        return destination;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getEstimatedFare() {
        return estimatedFare;
    }

    public void setEstimatedFare(Double estimatedFare) {
        this.estimatedFare = estimatedFare;
    }

    public Double getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(Double estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public LocalDateTime getConfirmationTime() {
        return confirmationTime;
    }

    public void setConfirmationTime(LocalDateTime confirmationTime) {
        this.confirmationTime = confirmationTime;
    }

    /**
     * Inizializza la conferma con i dati di una RideRequestBean,
     * imposta paymentStatus a "Pending" e conferma l’ora corrente.
     */
    public void setRideRequest(RideRequestBean rideRequest) {
        if (rideRequest != null) {
            this.rideID = rideRequest.getRequestID();
            this.client = rideRequest.getClient();
            this.userLocation = rideRequest.getOriginAsCoordinateBean();
            this.destination = rideRequest.getDestination();
            this.paymentStatus = "Pending";
            this.confirmationTime = LocalDateTime.now();
            this.status = "Pending";
        }
    }

    /**
     * Marca la corsa come confermata e aggiorna l’ora di conferma.
     */
    public void markConfirmed() {
        this.status = "Confirmed";
        this.confirmationTime = LocalDateTime.now();
    }
}

