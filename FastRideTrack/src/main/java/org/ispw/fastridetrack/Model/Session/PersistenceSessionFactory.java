package org.ispw.fastridetrack.Model.Session;

import org.ispw.fastridetrack.DAO.*;
import org.ispw.fastridetrack.DAO.Adapter.*;
import org.ispw.fastridetrack.DAO.MYSQL.*;

public class PersistenceSessionFactory implements SessionFactory {

    private final SingletonDBSession dbSession = SingletonDBSession.getInstance();

    @Override
    public ClientDAO createClientDAO() {
        return new ClientDAOMYSQL(dbSession.getConnection());
    }

    @Override
    public DriverDAO createDriverDAO() {
        return new DriverDAOMYSQL(dbSession.getConnection());
    }

    @Override
    public RideRequestDAO createRideRequestDAO() {
        return new RideRequestDAOMYSQL(dbSession.getConnection());
    }

    @Override
    public TaxiRideDAO createTaxiRideDAO() {
        return new TaxiRideDAOMYSQL(dbSession.getConnection());
    }

    @Override
    public EmailDAO createEmailDAO() {
        return new GmailAdapter();
    }

    @Override
    public MapDAO createMapDAO() {
        return new GoogleMapsAdapter();
    }
}

