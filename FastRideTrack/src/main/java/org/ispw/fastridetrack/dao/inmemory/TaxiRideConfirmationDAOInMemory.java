package org.ispw.fastridetrack.dao.inmemory;

import org.ispw.fastridetrack.dao.TaxiRideConfirmationDAO;
import org.ispw.fastridetrack.exception.RideConfirmationNotFoundException;
import org.ispw.fastridetrack.model.Client;
import org.ispw.fastridetrack.model.Coordinate;
import org.ispw.fastridetrack.model.Driver;
import org.ispw.fastridetrack.model.TaxiRideConfirmation;
import org.ispw.fastridetrack.model.enumeration.PaymentMethod;
import org.ispw.fastridetrack.model.enumeration.RideConfirmationStatus;

import java.time.LocalDateTime;
import java.util.*;

public class TaxiRideConfirmationDAOInMemory implements TaxiRideConfirmationDAO {
    private final Map<Integer, TaxiRideConfirmation> rides = new HashMap<>();

    public TaxiRideConfirmationDAOInMemory() {
        TaxiRideConfirmation confirmation = new TaxiRideConfirmation(
                201,                         // rideID
                d2,                          // Driver istanza già creata
                client1,                     // recuperato dal DAO
                new Coordinate(45.6370, 8.8330), // posizione stimata del client (vicino al driver)
                "Via Garibaldi 20, Varese", // destinazione
                RideConfirmationStatus.PENDING,
                17.80,                       // stima costo
                9.0,                         // stima tempo (in minuti)
                PaymentMethod.CARD,         // metodo pagamento del client
                LocalDateTime.of(2025, 7, 2, 15, 45) // ora di conferma
        );
        save(confirmation);
    }

    Driver d2 = new Driver(
            2,
            "luca88",
            "pass2",
            "Luca Bianchi",
            "luca@example.com",
            "0987654321",
            45.6382,      // ~1 km nord
            8.8320,       // ~1 km est
            "Toyota Yaris",
            "BB456CC",
            "AFFILIATION",
            true
    );

    Client client1 = new Client(
            1,
            "testclient",
            "testpass",
            "Mario Rossi",
            "mario@gmail.com",
            "1234567890",
            PaymentMethod.CARD
    );

    @Override
    public void save(TaxiRideConfirmation ride) {
        rides.put(ride.getRideID(), ride);
    }

    @Override
    public Optional<TaxiRideConfirmation> findById(int rideID) {
        return Optional.ofNullable(rides.get(rideID));
    }

    @Override
    public void update(TaxiRideConfirmation updatedRide) {
        int rideID = updatedRide.getRideID();
        if (!rides.containsKey(rideID)) {
            throw new RideConfirmationNotFoundException(rideID);
        }
        rides.put(rideID, updatedRide);
    }

    @Override
    public boolean exists(int rideID) {
        return rides.containsKey(rideID);
    }

    @Override
    public List<TaxiRideConfirmation> findByDriverIDandStatus(int driverID, RideConfirmationStatus status) {
        return rides.values().stream()
                .filter(ride -> ride.getDriver() != null
                        && ride.getDriver().getUserID() == driverID)
                .filter(ride -> ride.getStatus() == status)
                .sorted(Comparator.comparing(
                        TaxiRideConfirmation::getConfirmationTime,
                        Comparator.nullsLast(Comparator.naturalOrder()) // gestisce i null in fondo
                ))
                .toList();
    }

    @Override
    public void updateRideConfirmationStatus(int rideID, RideConfirmationStatus newStatus) {
        TaxiRideConfirmation ride = rides.get(rideID);
        if (ride == null) {
            throw new RideConfirmationNotFoundException(rideID);
        }
        ride.setStatus(newStatus);
    }

}

