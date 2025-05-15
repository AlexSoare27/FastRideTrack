package org.ispw.fastridetrack.Model.Session;

import org.ispw.fastridetrack.DAO.Adapter.EmailDAO;
import org.ispw.fastridetrack.DAO.Adapter.MapDAO;
import org.ispw.fastridetrack.DAO.ClientDAO;
import org.ispw.fastridetrack.DAO.DriverDAO;
import org.ispw.fastridetrack.DAO.RideRequestDAO;
import org.ispw.fastridetrack.DAO.TaxiRideDAO;

public interface SessionFactory {
    ClientDAO createClientDAO();
    DriverDAO createDriverDAO();
    RideRequestDAO createRideRequestDAO();
    TaxiRideDAO createTaxiRideDAO();
    EmailDAO createEmailDAO();
    MapDAO createMapDAO();
}