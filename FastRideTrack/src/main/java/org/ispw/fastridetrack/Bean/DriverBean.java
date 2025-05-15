package org.ispw.fastridetrack.Bean;

import org.ispw.fastridetrack.Model.Driver;
import org.ispw.fastridetrack.Model.UserType;

public class DriverBean extends UserBean {

    private String vehicleInfo;
    private String vehiclePlate;
    private String affiliation;

    public DriverBean(String username, String password, int userID, String name,
                      String email, String phoneNumber,
                      double latitude, double longitude,
                      String vehicleInfo, String vehiclePlate, String affiliation) {
        super(username, password, UserType.DRIVER, userID, name, email, phoneNumber, latitude, longitude);
        this.vehicleInfo = vehicleInfo;
        this.vehiclePlate = vehiclePlate;
        this.affiliation = affiliation;
    }

    public DriverBean(String username, String password, int userID, String name,
                      String email, String phoneNumber,
                      CoordinateBean coordinate,
                      String vehicleInfo, String vehiclePlate, String affiliation) {
        this(username, password, userID, name, email, phoneNumber,
                coordinate.getLatitude(), coordinate.getLongitude(),
                vehicleInfo, vehiclePlate, affiliation);
    }

    public String getVehicleInfo() {
        return vehicleInfo;
    }

    public void setVehicleInfo(String vehicleInfo) {
        this.vehicleInfo = vehicleInfo;
    }

    public String getVehiclePlate() {
        return vehiclePlate;
    }

    public void setVehiclePlate(String vehiclePlate) {
        this.vehiclePlate = vehiclePlate;
    }

    public String getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    public CoordinateBean getCoordinate() {
        return new CoordinateBean(getLatitude(), getLongitude());
    }

    public void setCoordinate(CoordinateBean coordinateBean) {
        if (coordinateBean != null) {
            setLatitude(coordinateBean.getLatitude());
            setLongitude(coordinateBean.getLongitude());
        }
    }

    public static DriverBean fromModel(Driver driver) {
        if (driver == null) return null;

        return new DriverBean(
                driver.getUsername(),
                driver.getPassword(),
                driver.getUserID(),
                driver.getName(),
                driver.getEmail(),
                driver.getPhoneNumber(),
                driver.getCoordinate(), // CoordinateBean sarà costruito da Coordinate
                driver.getVehicleInfo(),
                driver.getVehiclePlate(),
                driver.getAffiliation()
        );
    }

    public Driver toModel() {
        return new Driver(
                getUserID(),
                getName(),
                getUsername(),
                getPassword(),
                getEmail(),
                getPhoneNumber(),
                getCoordinate().toModel(),  // Coordinate (model) da CoordinateBean
                getVehicleInfo(),
                getVehiclePlate(),
                getAffiliation()
        );
    }

}


