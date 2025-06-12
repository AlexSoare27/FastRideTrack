package org.ispw.fastridetrack.Util;

import org.ispw.fastridetrack.Bean.AvailableDriverBean;
import org.ispw.fastridetrack.Bean.MapRequestBean;
import org.ispw.fastridetrack.Bean.TaxiRideConfirmationBean;

import java.util.List;

public class TemporaryMemory {
    private static TemporaryMemory instance;

    private MapRequestBean mapRequestBean;
    private List<AvailableDriverBean> availableDrivers;
    private AvailableDriverBean selectedDriver;
    private String selectedPaymentMethod;
    private TaxiRideConfirmationBean rideConfirmation;

    private TemporaryMemory() {}

    public static TemporaryMemory getInstance() {
        if (instance == null) {
            instance = new TemporaryMemory();
        }
        return instance;
    }

    // Getter e setter
    public MapRequestBean getMapRequestBean() { return mapRequestBean; }
    public void setMapRequestBean(MapRequestBean bean) { this.mapRequestBean = bean; }

    public List<AvailableDriverBean> getAvailableDrivers() { return availableDrivers; }
    public void setAvailableDrivers(List<AvailableDriverBean> drivers) { this.availableDrivers = drivers; }

    public AvailableDriverBean getSelectedDriver() { return selectedDriver; }
    public void setSelectedDriver(AvailableDriverBean driver) { this.selectedDriver = driver; }

    public String getSelectedPaymentMethod() { return selectedPaymentMethod; }
    public void setSelectedPaymentMethod(String method) { this.selectedPaymentMethod = method; }

    public TaxiRideConfirmationBean getRideConfirmation() { return rideConfirmation; }
    public void setRideConfirmation(TaxiRideConfirmationBean confirmation) { this.rideConfirmation = confirmation; }


    //Resetto tutti i dati memorizzati a null
    public void clear() {
        this.mapRequestBean = null;
        this.availableDrivers = null;
        this.selectedDriver = null;
        this.selectedPaymentMethod = null;
        this.rideConfirmation = null;
        System.out.println("TemporaryMemory cleared!");
    }

}



