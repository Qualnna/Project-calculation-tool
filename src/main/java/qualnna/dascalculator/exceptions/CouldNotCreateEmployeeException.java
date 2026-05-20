package qualnna.dascalculator.exceptions;

import java.sql.SQLException;

public class CouldNotCreateEmployeeException extends RuntimeException {
    public CouldNotCreateEmployeeException(String message) {
        super(message);
    }
}
