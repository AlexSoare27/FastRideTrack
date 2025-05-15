package org.ispw.fastridetrack.Bean;

import org.ispw.fastridetrack.Model.Driver;

public class DriverAssignmentBean {
    private Integer requestID;
    private Driver driver;

    // Costruttore vuoto
    public DriverAssignmentBean() {
    }

    // Costruttore con tutti i parametri
    public DriverAssignmentBean(RideRequestBean requestID, Driver driver) {
        this.requestID = requestID.getRequestID();
        this.driver = driver;
    }

    // Getter e setter
    public Integer getRequestID() {
        return requestID;
    }

    public void setRequestID(Integer requestID) {
        this.requestID = requestID;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }
}
