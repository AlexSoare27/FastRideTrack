package org.ispw.fastridetrack.util;

import org.ispw.fastridetrack.bean.CoordinateBean;

public class CoordinateUtils {

    public static CoordinateBean toCoordinate(double latitude, double longitude) {
        return new CoordinateBean(latitude, longitude);
    }

    public static double[] toDoubleArray(CoordinateBean coordinateBean) {
        return new double[]{coordinateBean.getLatitude(), coordinateBean.getLongitude()};
    }

    public static double getLatitude(CoordinateBean coordinateBean) {
        return coordinateBean.getLatitude();
    }

    public static double getLongitude(CoordinateBean coordinateBean) {
        return coordinateBean.getLongitude();
    }

    // Converte CoordinateBean in stringa "lat,lon"
    public static String coordinateToString(CoordinateBean coordinate) {
        if (coordinate == null) return "";
        return coordinate.getLatitude() + "," + coordinate.getLongitude();
    }

    // Converte stringa "lat,lon" in CoordinateBean
    public static CoordinateBean stringToCoordinate(String coordStr) {
        if (coordStr == null || coordStr.isBlank()) return null;
        String[] parts = coordStr.split(",");
        if (parts.length != 2) return null;
        try {
            double lat = Double.parseDouble(parts[0].trim());
            double lon = Double.parseDouble(parts[1].trim());
            return new CoordinateBean(lat, lon);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}

