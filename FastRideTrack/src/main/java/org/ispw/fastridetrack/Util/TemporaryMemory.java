package org.ispw.fastridetrack.util;

import org.ispw.fastridetrack.bean.AvailableDriverBean;
import org.ispw.fastridetrack.bean.MapRequestBean;
import org.ispw.fastridetrack.bean.TaxiRideConfirmationBean;
import org.ispw.fastridetrack.model.enumeration.PaymentMethod;

import java.util.List;

public class TemporaryMemory {

    private MapRequestBean mapRequestBean;
    private List<AvailableDriverBean> availableDrivers;
    private AvailableDriverBean selectedDriver;
    private PaymentMethod selectedPaymentMethod;
    private TaxiRideConfirmationBean rideConfirmation;

    private TemporaryMemory() {}

    private static class Holder {
        private static final TemporaryMemory INSTANCE = new TemporaryMemory();
    }

    public static TemporaryMemory getInstance() {
        return Holder.INSTANCE;
    }

    public MapRequestBean getMapRequestBean() { return mapRequestBean; }
    public void setMapRequestBean(MapRequestBean bean) { this.mapRequestBean = bean; }

    public List<AvailableDriverBean> getAvailableDrivers() { return availableDrivers; }
    public void setAvailableDrivers(List<AvailableDriverBean> drivers) { this.availableDrivers = drivers; }

    public AvailableDriverBean getSelectedDriver() { return selectedDriver; }
    public void setSelectedDriver(AvailableDriverBean driver) { this.selectedDriver = driver; }

    public PaymentMethod getSelectedPaymentMethod() { return selectedPaymentMethod; }
    public void setSelectedPaymentMethod(String method) { this.selectedPaymentMethod = PaymentMethod.valueOf(method); }

    public TaxiRideConfirmationBean getRideConfirmation() { return rideConfirmation; }
    public void setRideConfirmation(TaxiRideConfirmationBean confirmation) { this.rideConfirmation = confirmation; }

    public void clear() {
        this.mapRequestBean = null;
        this.availableDrivers = null;
        this.selectedDriver = null;
        this.selectedPaymentMethod = null;
        this.rideConfirmation = null;
        System.out.println("TemporaryMemory cleared!");
    }

}



