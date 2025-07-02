package org.ispw.fastridetrack.controller.applicationcontroller;

import org.ispw.fastridetrack.bean.TaxiRideConfirmationBean;
import org.ispw.fastridetrack.dao.DriverDAO;
import org.ispw.fastridetrack.dao.TaxiRideConfirmationDAO;
import org.ispw.fastridetrack.exception.DriverDAOException;
import org.ispw.fastridetrack.exception.RideConfirmationNotFoundException;
import org.ispw.fastridetrack.model.TaxiRideConfirmation;
import org.ispw.fastridetrack.session.SessionManager;
import org.ispw.fastridetrack.model.enumeration.RideConfirmationStatus;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class RideConfirmationApplicationController {
    private final TaxiRideConfirmationDAO taxiRideConfirmationDAO;
    private final DriverDAO driverDAO;

    public RideConfirmationApplicationController() {
        this.taxiRideConfirmationDAO = SessionManager.getInstance().getTaxiRideDAO();
        this.driverDAO = SessionManager.getInstance().getDriverDAO();
    }

    public List<TaxiRideConfirmation> getPendingConfirmationsForDriver(int driverId) throws RideConfirmationNotFoundException {
        List<TaxiRideConfirmation> requests = taxiRideConfirmationDAO.findByDriverIDandStatus(driverId, RideConfirmationStatus.PENDING);
        requests.sort(Comparator.comparing(TaxiRideConfirmation::getConfirmationTime));
        return requests;
    }

    public Optional<TaxiRideConfirmationBean> getNextRideConfirmation(int driverId) throws RideConfirmationNotFoundException {
        List<TaxiRideConfirmation> requests = getPendingConfirmationsForDriver(driverId);
        if (!requests.isEmpty()) {
            return Optional.of(TaxiRideConfirmationBean.fromModel(requests.getFirst()));  // prima richiesta in ordine FIFO
        } else {
            return Optional.empty();
        }
    }

    public TaxiRideConfirmationBean acceptRideConfirmationAndRejectOthers(int rideId, int driverId) throws DriverDAOException {

        Optional<TaxiRideConfirmation> maybeConfirmation = taxiRideConfirmationDAO.findById(rideId);

        if (maybeConfirmation.isEmpty()) {
            throw new IllegalStateException("No ride confirmation found with ID: " + rideId);
        }

        TaxiRideConfirmation accepted = maybeConfirmation.get();

        if (!accepted.getDriver().getUserID().equals(driverId)) {
            throw new IllegalStateException("Driver mismatch for confirmation ID: " + rideId);
        }

        // Accetta la conferma corrente
        accepted.setStatus(RideConfirmationStatus.ACCEPTED);
        taxiRideConfirmationDAO.update(accepted);

        // Rifiuta le altre conferme dello stesso driver
        List<TaxiRideConfirmation> others = taxiRideConfirmationDAO.findByDriverIDandStatus(driverId, RideConfirmationStatus.PENDING);
        for (TaxiRideConfirmation other : others) {
            if (!other.getRideID().equals(rideId)) {
                other.setStatus(RideConfirmationStatus.REJECTED);
                taxiRideConfirmationDAO.update(other);
            }
        }

        // Rende il driver non disponibile
        driverDAO.updateAvailability(driverId, false);
        SessionManager.getInstance().getLoggedDriver().setAvailable(false);

        return TaxiRideConfirmationBean.fromModel(accepted);
    }

    public void rejectRideConfirmation(int rideId, int driverId) {
        Optional<TaxiRideConfirmation> request = taxiRideConfirmationDAO.findById(rideId);

        if (request.isPresent() && request.get().getDriver().getUserID().equals(driverId)) {
            request.get().setStatus(RideConfirmationStatus.REJECTED);
            taxiRideConfirmationDAO.update(request.get());
        }
    }
}
