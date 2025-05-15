package org.ispw.fastridetrack.Bean;

import org.ispw.fastridetrack.Model.Coordinate;

public class CoordinateBean {
    private double latitude;
    private double longitude;

    public CoordinateBean(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public CoordinateBean toModel() {
        return new CoordinateBean(latitude, longitude);
    }

}
