package org.ispw.fastridetrack.Model.Session;

import org.ispw.fastridetrack.DAO.*;
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
}

