package org.ispw.fastridetrack.Bean;

import org.ispw.fastridetrack.Model.Driver;

public class DriverAssignmentBean {
    private Integer requestID;
    private Driver driver;

    // Costruttore principale
    public DriverAssignmentBean(RideRequestBean rideRequest, Driver driver) {
        if (rideRequest == null) throw new IllegalArgumentException("RideRequest cannot be null");
        if (driver == null) throw new IllegalArgumentException("Driver cannot be null");

        this.requestID = rideRequest.getRequestID();
        if (this.requestID == null) {
            throw new IllegalArgumentException("RideRequest must have a valid requestID");
        }
        this.driver = driver;
    }

    // Costruttore vuoto
    public DriverAssignmentBean() {}

    // Costruttore alternativo con requestID diretto
    public DriverAssignmentBean(Integer requestID, Driver driver) {
        if (requestID == null) throw new IllegalArgumentException("requestID cannot be null");
        if (driver == null) throw new IllegalArgumentException("Driver cannot be null");

        this.requestID = requestID;
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

    @Override
    public String toString() {
        return "DriverAssignmentBean{" +
                "requestID=" + requestID +
                ", driver=" + (driver != null ? driver.getName() : "null") +
                '}';
    }
}


