package org.ispw.fastridetrack.Bean;

import org.ispw.fastridetrack.Model.Client;
import org.ispw.fastridetrack.Model.Driver;

public class RideRequestBean {
    private Integer requestID;
    private Client client;
    private String pickupLocation;  // es. "lat,lng"
    private String destination;
    private int radiusKm;
    private String paymentMethod;
    private Driver driver;  // driver assegnato, inizialmente null

    // Costruttore principale con CoordinateBean e conversione lat,long
    public RideRequestBean(CoordinateBean origin, String destination, int radiusKm, String paymentMethod) {
        if (origin == null) throw new IllegalArgumentException("Origin cannot be null");
        if (destination == null || destination.isEmpty()) throw new IllegalArgumentException("Destination cannot be null or empty");
        if (radiusKm <= 0) throw new IllegalArgumentException("Radius must be positive");

        this.pickupLocation = origin.getLatitude() + "," + origin.getLongitude();
        this.destination = destination;
        this.radiusKm = radiusKm;
        this.paymentMethod = paymentMethod;
        this.driver = null;  // nessun driver assegnato all’inizio
    }

    // Costruttore completo (es. ricostruzione da DB)
    public RideRequestBean(Integer requestID, Client client, String pickupLocation, String destination, int radiusKm, String paymentMethod, Driver driver) {
        this.requestID = requestID;
        this.client = client;
        this.pickupLocation = pickupLocation;
        this.destination = destination;
        this.radiusKm = radiusKm;
        this.paymentMethod = paymentMethod;
        this.driver = driver;
    }

    // Getter e setter
    public Integer getRequestID() {
        return requestID;
    }

    public void setRequestID(Integer requestID) {
        this.requestID = requestID;
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

    public int getRadiusKm() {
        return radiusKm;
    }

    public void setRadiusKm(int radiusKm) {
        this.radiusKm = radiusKm;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    /**
     * Restituisce l'origine come CoordinateBean, parsing da pickupLocation string.
     */
    public CoordinateBean getOriginAsCoordinateBean() {
        if (pickupLocation == null || !pickupLocation.contains(",")) return null;
        String[] parts = pickupLocation.split(",");
        try {
            double lat = Double.parseDouble(parts[0]);
            double lng = Double.parseDouble(parts[1]);
            return new CoordinateBean(lat, lng);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return "RideRequestBean{" +
                "requestID=" + requestID +
                ", client=" + client +
                ", pickupLocation='" + pickupLocation + '\'' +
                ", destination='" + destination + '\'' +
                ", radiusKm=" + radiusKm +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", driver=" + (driver != null ? driver.getName() : "null") +
                '}';
    }
}



