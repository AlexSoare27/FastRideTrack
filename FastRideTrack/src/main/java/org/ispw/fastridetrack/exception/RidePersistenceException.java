package org.ispw.fastridetrack.exception;

import java.io.IOException;

public class RidePersistenceException extends RuntimeException {
    public RidePersistenceException(String message) {super(message);}

    public RidePersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
