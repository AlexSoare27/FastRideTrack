package org.ispw.fastridetrack.Bean;

import org.ispw.fastridetrack.Model.Driver;
import org.ispw.fastridetrack.Model.UserType;


public class DriverBean extends UserBean {

    private String vehicleInfo;
    private String vehiclePlate;
    private String affiliation;
    private boolean available;


    public DriverBean(String username, String password, int userID, String name,
                      String email, String phoneNumber,
                      double latitude, double longitude,
                      String vehicleInfo, String vehiclePlate, String affiliation,
                      boolean available) {
        super(username, password, UserType.DRIVER, userID, name, email, phoneNumber, latitude, longitude);
        this.vehicleInfo = vehicleInfo;
        this.vehiclePlate = vehiclePlate;
        this.affiliation = affiliation;
        this.available = available;
    }

    public DriverBean(String username, String password, int userID, String name,
                      String email, String phoneNumber,
                      CoordinateBean coordinate,
                      String vehicleInfo, String vehiclePlate, String affiliation,
                      boolean available) {
        this(username, password, userID, name, email, phoneNumber,
                coordinate.getLatitude(), coordinate.getLongitude(),
                vehicleInfo, vehiclePlate, affiliation, available);
    }



    public String getVehicleInfo() {
        return vehicleInfo;
    }


    public String getVehiclePlate() {
        return vehiclePlate;
    }


    public String getAffiliation() {
        return affiliation;
    }


    public boolean isAvailable() {
        return available;
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
                new CoordinateBean(driver.getCoordinate()),
                driver.getVehicleInfo(),
                driver.getVehiclePlate(),
                driver.getAffiliation(),
                driver.isAvailable()  // <-- aggiunto qui
        );
    }

    public Driver toModel() {
        return new Driver(
                getUserID(),
                getUsername(),
                getPassword(),
                getName(),
                getEmail(),
                getPhoneNumber(),
                getCoordinate().toModel(),
                getVehicleInfo(),
                getVehiclePlate(),
                getAffiliation(),
                this.available  // <-- aggiunto qui
        );
    }


    public static DriverBean fromAvailableDriver(AvailableDriverBean aDriver) {
        if (aDriver == null) return null;
        return new DriverBean(
                aDriver.getUsername(),
                aDriver.getPassword(),
                aDriver.getUserID(),
                aDriver.getName(),
                aDriver.getEmail(),
                aDriver.getPhoneNumber(),
                aDriver.getLatitude(),
                aDriver.getLongitude(),
                aDriver.getVehicleInfo(),
                aDriver.getVehiclePlate(),
                aDriver.getAffiliation(),
                aDriver.isAvailable()
        );
    }


}


