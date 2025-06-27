package org.ispw.fastridetrack.model.session;

import org.ispw.fastridetrack.dao.*;

public interface SessionFactory {
    ClientDAO createClientDAO();
    DriverDAO createDriverDAO();
    RideRequestDAO createRideRequestDAO();
    TaxiRideConfirmationDAO createTaxiRideDAO();
    RideDAO createRideDAO();
}