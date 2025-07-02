package org.ispw.fastridetrack.controller.clicontroller;

import org.ispw.fastridetrack.controller.applicationcontroller.ApplicationFacade;
import org.ispw.fastridetrack.model.enumeration.UserType;
import org.ispw.fastridetrack.session.SessionManager;

import java.util.Scanner;

public class LoginCliController {

    private final Scanner scanner = new Scanner(System.in);
    private final ApplicationFacade facade;

    public LoginCliController() throws Exception {
        facade = new ApplicationFacade();
    }

    public void start() {
        try {
            UserType loggedUserType = loginFlow();

            if (loggedUserType == UserType.CLIENT) {
                new ClientCliController().startClientFlow();
            } else if (loggedUserType == UserType.DRIVER) {
                new DriverCliController().startDriverFlow();
            } else {
                System.out.println("Unrecognized user type.");
            }

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            System.out.println("Closing application...");
            scanner.close();
            SessionManager.getInstance().shutdown();
        }
    }

    private UserType loginFlow() throws Exception {
        System.out.println("Welcome to FastRideTrack CLI!");

        while (true) {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();

            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            if (facade.login(username, password)) {
                UserType type = facade.getLoggedUserType();
                System.out.println("Logged in as " + type);
                return type;
            } else {
                System.out.println("Invalid credentials. Please try again.");
            }
        }
    }
}