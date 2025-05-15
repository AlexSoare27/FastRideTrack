package org.ispw.fastridetrack.Bean;

import org.ispw.fastridetrack.Model.Client;
import org.ispw.fastridetrack.Model.Driver;

public class RideRequestBean {
    private Integer requestID;
    private Client client;
    private String destination;
    private String status;

    // Costruttore vuoto
    public RideRequestBean() {
    }

    // Costruttore con tutti i parametri
    public RideRequestBean(Integer requestID, Client client, String destination, String status) {
        this.requestID = requestID;
        this.client = client;
        this.destination = destination;
        this.status = status;
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

    public void setDriver(Driver driver) {

    }
}
