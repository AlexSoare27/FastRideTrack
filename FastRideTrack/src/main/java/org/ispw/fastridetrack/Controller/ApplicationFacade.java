package org.ispw.fastridetrack.controller;

import org.ispw.fastridetrack.bean.MapRequestBean;
import org.ispw.fastridetrack.bean.RideRequestBean;
import org.ispw.fastridetrack.bean.TaxiRideConfirmationBean;
import org.ispw.fastridetrack.controller.applicationcontroller.*;
import org.ispw.fastridetrack.exception.ClientDAOException;
import org.ispw.fastridetrack.exception.DatabaseConnectionException;
import org.ispw.fastridetrack.exception.DriverDAOException;
import org.ispw.fastridetrack.exception.MapServiceException;
import org.ispw.fastridetrack.model.session.SessionManager;
import org.ispw.fastridetrack.model.enumeration.UserType;

import java.util.Optional;

public class ApplicationFacade {

    private final LoginApplicationController loginAC;
    private final DriverMatchingApplicationController driverMatchingAC;
    private final ClientRideManagementApplicationController clientRideManagementAC;
    private final RideConfirmationApplicationController rideConfirmationAC;
    private final CurrentRideManagementApplicationController currentRideManagementAC;
    private final SessionDataApplicationController sessionDataAC;
    private final MapApplicationController mapAC;

    public ApplicationFacade() throws DatabaseConnectionException {
        this.loginAC = new LoginApplicationController();
        this.driverMatchingAC = new DriverMatchingApplicationController();
        this.clientRideManagementAC = new ClientRideManagementApplicationController();
        this.rideConfirmationAC = new RideConfirmationApplicationController();
        this.currentRideManagementAC = new CurrentRideManagementApplicationController();
        this.sessionDataAC = new SessionDataApplicationController();
        this.mapAC = new MapApplicationController();
    }
    public LoginApplicationController getLoginAC() {
        return loginAC;
    }
    public DriverMatchingApplicationController getDriverMatchingAC() {
        return driverMatchingAC;
    }
    public ClientRideManagementApplicationController getClientRideManagementAC() {
        return clientRideManagementAC;
    }
    public RideConfirmationApplicationController getRideConfirmationAC() {return rideConfirmationAC;}
    public CurrentRideManagementApplicationController getCurrentRideManagementAC() {return  currentRideManagementAC;}
    public SessionDataApplicationController getSessionDataAC() {return sessionDataAC;}
    public MapApplicationController getMapAC() {return mapAC;}

    public String processRideRequestAndReturnMapHtml(RideRequestBean rideBean, MapRequestBean mapBean) throws MapServiceException {
        driverMatchingAC.saveRideRequest(rideBean);
        return mapAC.showMap(mapBean).getHtmlContent();
    }

    public Boolean validateUserCredentials(String username, String password) throws ClientDAOException, DriverDAOException {
        if(loginAC.validateClientCredentials(username, password,UserType.CLIENT )){
            return true;
        }
        else if(loginAC.validateDriverCredentials(username, password, UserType.DRIVER)){
            return true;
        }
        return false;
    }

    public UserType getLoggedUserType() throws ClientDAOException, DriverDAOException {
        if(SessionManager.getInstance().getLoggedClient() != null){
            return UserType.CLIENT;
        }
        if(SessionManager.getInstance().getLoggedDriver() != null){
            return UserType.DRIVER;
        }
        return null;
    }

    public Optional<TaxiRideConfirmationBean> getNextRideConfirmation(Integer driverID) {
        return rideConfirmationAC.getNextRideConfirmation(driverID);
    }

    public void acceptRideConfirmationAndInitializeRide(TaxiRideConfirmationBean confirmationBean) throws DriverDAOException {
        rideConfirmationAC.acceptRideConfirmationAndRejectOthers(confirmationBean.getRideID(), confirmationBean.getDriver().getUserID());
        currentRideManagementAC.initializeCurrentRide(confirmationBean);
    }

    public void rejectRideConfirmation(int rideID, int driverID) throws DriverDAOException {
        rideConfirmationAC.rejectRideConfirmation(rideID, driverID);
    }

    public void markClientLocated(RideRequestBean rideBean) {
        currentRideManagementAC.confirmClientLocated();
    }
}
