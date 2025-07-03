package org.ispw.fastridetrack.controller.clicontroller;

import org.ispw.fastridetrack.bean.LocationBean;
import org.ispw.fastridetrack.bean.RideBean;
import org.ispw.fastridetrack.bean.TaxiRideConfirmationBean;
import org.ispw.fastridetrack.controller.applicationcontroller.ApplicationFacade;
import org.ispw.fastridetrack.exception.ClientNotFetchedException;
import org.ispw.fastridetrack.model.Map;
import org.ispw.fastridetrack.model.enumeration.RideConfirmationStatus;
import org.ispw.fastridetrack.session.SessionManager;
import org.ispw.fastridetrack.util.DriverSessionContext;

import java.util.Scanner;

public class DriverCliController {

    private final Scanner scanner = new Scanner(System.in);
    private final ApplicationFacade facade;


    public DriverCliController() throws Exception {
        facade = new ApplicationFacade();
    }

    public void startDriverFlow() throws Exception {
        boolean exit = false;

        while (!exit) {
            RideBean currentRide = DriverSessionContext.getInstance().getCurrentRide();

            System.out.println("\n--- DRIVER MENU ---");

            if (currentRide == null) {
                System.out.println("[1] View next PENDING ride confirmation");
                System.out.println("[2] Accept ride confirmation");
                System.out.println("[3] Reject ride confirmation");
                System.out.println("[0] Logout and exit");
                System.out.print("Select an option: ");

                String input = scanner.nextLine();

                switch (input) {
                    case "1": showNextRideConfirmation(); break;
                    case "2": acceptRideConfirmation(); break;
                    case "3": rejectRideConfirmation(); break;
                    case "0":
                        System.out.println("Logging out...");
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid option.");
                }

            } else {
                System.out.printf("Active ride (ID %d) - Status: %s%n", currentRide.getRideID(), currentRide.getStatus());

                switch (currentRide.getStatus()) {
                    case INITIATED:
                        System.out.println("[1] View route: driver -> client");
                        System.out.println("[2] Confirm client located");
                        System.out.println("[0] Logout and exit");
                        System.out.print("Select an option: ");

                        String inputInit = scanner.nextLine();
                        switch (inputInit) {
                            case "1": showRouteDriverToClient(); break;
                            case "2": confirmClientLocated(); break;
                            case "0":
                                System.out.println("Logging out...");
                                exit = true;
                                break;
                            default:
                                System.out.println("Invalid option.");
                        }
                        break;

                    case CLIENT_LOCATED:
                        System.out.println("[1] Start ride");
                        System.out.println("[2] View ride route");
                        System.out.println("[0] Logout and exit");
                        System.out.print("Select an option: ");

                        String inputClientLocated = scanner.nextLine();
                        switch (inputClientLocated) {
                            case "1": startRideIfClientFetchedFalse(); break;
                            case "2": showRideRoute(); break;
                            case "0":
                                System.out.println("Logging out...");
                                exit = true;
                                break;
                            default:
                                System.out.println("Invalid option.");
                        }
                        break;

                    case ONGOING:
                        System.out.println("[1] View ride route");
                        System.out.println("[0] Logout and exit");
                        System.out.print("Select an option: ");

                        String inputOngoing = scanner.nextLine();
                        switch (inputOngoing) {
                            case "1": showRideRoute(); break;
                            case "0":
                                System.out.println("Logging out...");
                                exit = true;
                                break;
                            default:
                                System.out.println("Invalid option.");
                        }
                        break;

                    case FINISHED:
                        System.out.println("Ride has finished. Logging out.");
                        exit = true;
                        break;

                    default:
                        System.out.println("Unhandled ride status.");
                        exit = true;
                }
            }
        }
    }

    private void showNextRideConfirmation() {
        Integer driverID = SessionManager.getInstance().getLoggedDriver().getUserID();
        var optConfirmation = facade.getNextRideConfirmation(driverID);

        if (optConfirmation.isEmpty()) {
            System.out.println("No ride confirmations available.");
            return;
        }

        TaxiRideConfirmationBean conf = optConfirmation.get();

        if (!conf.getStatus().equals(RideConfirmationStatus.PENDING)) {
            System.out.println("No PENDING confirmations.");
            return;
        }

        System.out.println("Next ride confirmation:");
        System.out.println("Ride ID: " + conf.getRideID());
        System.out.println("Client: " + conf.getClient().getName());
        System.out.println("Pickup location: " + conf.getUserLocation());
        System.out.println("Destination: " + conf.getDestination());
        System.out.printf("Estimated fare: €%.2f%n", conf.getEstimatedFare());
        System.out.printf("Estimated time: %.2f minutes%n", conf.getEstimatedTime());
        System.out.println("Confirmation status: " + conf.getStatus());
    }

    private void acceptRideConfirmation() throws Exception {
        Integer driverID = SessionManager.getInstance().getLoggedDriver().getUserID();
        var optConfirmation = facade.getNextRideConfirmation(driverID);

        if (optConfirmation.isEmpty()) {
            System.out.println("No confirmation to accept.");
            return;
        }

        TaxiRideConfirmationBean conf = optConfirmation.get();

        if (!conf.getStatus().equals(RideConfirmationStatus.PENDING)) {
            System.out.println("No PENDING confirmations to accept.");
            return;
        }

        facade.acceptRideConfirmationAndInitializeRide(conf);
        System.out.println("Ride confirmation accepted and ride initialized.");
    }

    private void rejectRideConfirmation() throws Exception {
        Integer driverID = SessionManager.getInstance().getLoggedDriver().getUserID();
        var optConfirmation = facade.getNextRideConfirmation(driverID);

        if (optConfirmation.isEmpty()) {
            System.out.println("No confirmation to reject.");
            return;
        }

        TaxiRideConfirmationBean conf = optConfirmation.get();

        if (!conf.getStatus().equals(RideConfirmationStatus.PENDING)) {
            System.out.println("No PENDING confirmations to reject.");
            return;
        }

        System.out.print("Enter rejection reason: ");
        String reason = scanner.nextLine();

        facade.rejectRideConfirmation(conf, reason);
        System.out.println("Ride confirmation rejected and client notified.");
    }

    private void showRouteDriverToClient() {
        try {
            RideBean ride = DriverSessionContext.getInstance().getCurrentRide();
            if (ride == null) {
                System.out.println("No active ride.");
                return;
            }

            LocationBean start = DriverSessionContext.getInstance().getStartPoint();
            LocationBean end = DriverSessionContext.getInstance().getEndPoint();

            if (start == null || end == null) {
                System.out.println("Route information not available.");
                return;
            }

            Map map = facade.loadDriverRouteBasedOnRideStatus();

            System.out.println("Route from driver to client:");
            System.out.println("Estimated distance: " + map.getDistanceKm() + " km");
            System.out.println("Estimated time: " + map.getEstimatedTimeMinutes() + " minutes");

        } catch (Exception e) {
            System.out.println("Error loading route: " + e.getMessage());
        }
    }

    private void confirmClientLocated() {
        try {
            System.out.print("Enter estimated arrival time (minutes): ");
            double eta = Double.parseDouble(scanner.nextLine());

            facade.markClientLocated(eta);
            System.out.println("Client marked as located and notified.");

        } catch (NumberFormatException e) {
            System.out.println("Invalid input for estimated time.");
        } catch (Exception e) {
            System.out.println("Error marking client as located: " + e.getMessage());
        }
    }

    private void startRideIfClientFetchedFalse() {
        RideBean currentRide = DriverSessionContext.getInstance().getCurrentRide();
        if (currentRide == null) {
            System.out.println("No active ride.");
            return;
        }
        try {
            facade.startRide();
            System.out.println("Ride started successfully.");
        } catch (ClientNotFetchedException e) {
            System.out.println("Error: Client has not been picked up. " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error starting ride: " + e.getMessage());
        }
    }

    private void showRideRoute() {
        try {
            Map map = facade.loadDriverRouteBasedOnRideStatus();
            System.out.println("Ride route:");
            System.out.println("Estimated distance: " + map.getDistanceKm() + " km");
            System.out.println("Estimated time: " + map.getEstimatedTimeMinutes() + " minutes");
        } catch (Exception e) {
            System.out.println("Error loading ride route: " + e.getMessage());
        }
    }
}

