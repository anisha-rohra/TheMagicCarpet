package flight_booker.group_0722.user;

/**
 * A exception for when no Client exists to fit the criteria.
 *
 * This code is based on E2.
 */
public class NoSuchClientException extends Exception {

    /**
     * Exception constructor with no error message.
     */
    public NoSuchClientException() {
        super();
    }

    /**
     * Exception constructor with an error message.
     *
     * @param message the error message to be displayed
     */
    public NoSuchClientException(String message) {
        super(message);
    }
}
