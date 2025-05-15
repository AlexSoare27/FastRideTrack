package org.ispw.fastridetrack.Model;

public class Driver extends User {

    private String vehicleInfo;
    private String vehiclePlate;
    private String affiliation;

    public Driver(Integer userID, String name, String username, String password, String email,
                  String phoneNumber, String vehicleInfo, String vehiclePlate, String affiliation) {
        super(userID, username, password, name, email, phoneNumber, UserType.DRIVER);
        this.vehicleInfo = vehicleInfo;
        this.vehiclePlate = vehiclePlate;
        this.affiliation = affiliation;
    }

    public Driver(Integer userID, String name, String username, String password, String email,
                  String phoneNumber, Coordinate coordinate, String vehicleInfo, String vehiclePlate, String affiliation) {
        this(userID, name, username, password, email, phoneNumber, vehicleInfo, vehiclePlate, affiliation);
        if (coordinate != null) {
            setLatitude(coordinate.getLatitude());
            setLongitude(coordinate.getLongitude());
        }
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

    public Coordinate getCoordinate() {
        return new Coordinate(getLatitude(), getLongitude());
    }

    public void setCoordinate(Coordinate coordinate) {
        if (coordinate != null) {
            setLatitude(coordinate.getLatitude());
            setLongitude(coordinate.getLongitude());
        }
    }


}




