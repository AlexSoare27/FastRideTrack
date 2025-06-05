package org.ispw.fastridetrack.Bean;

public class AvailableDriverBean extends DriverBean {

    private double estimatedTime;   // Tempo stimato in minuti
    private double estimatedPrice;  // Prezzo stimato in valuta

    /**
     * Costruttore che prende un DriverBean esistente e aggiunge tempo e prezzo stimati.
     * Passa tutti i dati a DriverBean incluso il campo available.
     */
    public AvailableDriverBean(DriverBean driverBean, double estimatedTime, double estimatedPrice) {
        super(
                driverBean.getUsername(),
                driverBean.getPassword(),
                driverBean.getUserID(),
                driverBean.getName(),
                driverBean.getEmail(),
                driverBean.getPhoneNumber(),
                driverBean.getLatitude(),
                driverBean.getLongitude(),
                driverBean.getVehicleInfo(),
                driverBean.getVehiclePlate(),
                driverBean.getAffiliation(),
                driverBean.isAvailable()  // Passo il valore "available" correttamente
        );
        this.estimatedTime = estimatedTime;
        this.estimatedPrice = estimatedPrice;
    }

    public double getEstimatedTime() {
        return estimatedTime;
    }

    public double getEstimatedPrice() {
        return estimatedPrice;
    }

}


