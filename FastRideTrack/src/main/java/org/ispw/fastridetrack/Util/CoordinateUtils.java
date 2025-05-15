package org.ispw.fastridetrack.Util;

import org.ispw.fastridetrack.Bean.CoordinateBean;

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
}
