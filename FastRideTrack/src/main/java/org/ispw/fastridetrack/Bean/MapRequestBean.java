package org.ispw.fastridetrack.Bean;

public class MapRequestBean {
    private final CoordinateBean origin;
    private final String destination;
    private final int radiusKm;

    public MapRequestBean(CoordinateBean origin, String destination, int radiusKm) {
        this.origin = origin;
        this.destination = destination;
        this.radiusKm = radiusKm;
    }

    public CoordinateBean getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public int getRadiusKm() {
        return radiusKm;
    }
}

