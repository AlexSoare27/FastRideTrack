package org.ispw.fastridetrack.Model.Session;

import org.ispw.fastridetrack.DAO.*;
import org.ispw.fastridetrack.DAO.Adapter.*;
import org.ispw.fastridetrack.DAO.InMemory.*;

public class InMemorySessionFactory implements SessionFactory {

    @Override
    public ClientDAO createClientDAO() {
        return new ClientDAOInMemory();
    }

    @Override
    public DriverDAO createDriverDAO() {
        return new DriverDAOInMemory();
    }

    @Override
    public RideRequestDAO createRideRequestDAO() {
        return new RideRequestDAOInMemory();
    }

    @Override
    public TaxiRideDAO createTaxiRideDAO() {
        return new TaxiRideDAOInMemory();
    }

    @Override
    public EmailDAO createEmailDAO() {
        return new EmailDAOInMemory();
    }

    @Override
    public MapDAO createMapDAO() {
        return new MapDAOInMemory() {
            @Override
            public double getCurrentLatitude() {
                return 0;
            }

            @Override
            public double getCurrentLongitude() {
                return 0;
            }
        };
    }
}

