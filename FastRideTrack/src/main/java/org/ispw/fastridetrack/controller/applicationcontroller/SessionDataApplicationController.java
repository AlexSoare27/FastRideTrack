package org.ispw.fastridetrack.controller.applicationcontroller;

import org.ispw.fastridetrack.bean.ClientBean;
import org.ispw.fastridetrack.bean.DriverBean;
import org.ispw.fastridetrack.bean.RideBean;
import org.ispw.fastridetrack.bean.TaxiRideConfirmationBean;
import org.ispw.fastridetrack.model.Client;
import org.ispw.fastridetrack.model.Driver;
import org.ispw.fastridetrack.model.Ride;
import org.ispw.fastridetrack.model.TaxiRideConfirmation;
import org.ispw.fastridetrack.model.session.SessionManager;

public class SessionDataApplicationController {

    public DriverBean getDriverBean() {
        Driver d = SessionManager.getInstance().getLoggedDriver();
        return DriverBean.fromModel(d);
    }

    public void setLoggedDriver(Driver driver) {
        SessionManager.getInstance().setLoggedDriver(driver);
    }

    public ClientBean getClientBean() {
        Client c = SessionManager.getInstance().getLoggedClient();
        return ClientBean.fromModel(c);
    }

    public void setLoggedClient(Client client) {
        SessionManager.getInstance().setLoggedClient(client);
    }

    public TaxiRideConfirmationBean getDriverConfirmationBean() {
        TaxiRideConfirmation r = SessionManager.getInstance().getDriverSessionContext().getCurrentConfirmation();
        return TaxiRideConfirmationBean.fromModel(r);
    }

    public void setDriverConfirmation(TaxiRideConfirmation confirmation){
        SessionManager.getInstance().setCurrentConfirmation(confirmation);
    }

    public void setCurrentRide(Ride ride) {
        SessionManager.getInstance().setCurrentRide(ride);
    }

    public RideBean getCurrentRideBean() {
        Ride r = SessionManager.getInstance().getDriverSessionContext().getCurrentRide();
        return RideBean.fromModel(r);
    }
}
