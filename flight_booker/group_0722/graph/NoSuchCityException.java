package flight_booker.group_0722.graph;

/**
 * A exception for when no CityNode exists to fit the criteria.
 * 
 * This code is based on E2.
 */
public class NoSuchCityException extends Exception {

	/**
	 * Exception constructor with no error message.
	 */
	public NoSuchCityException() {
		super();
	}
	
	/**
	 * Exception constructor with an error message.
	 * @param message the error message to be displayed
	 */
	public NoSuchCityException(String message) {
		super(message);
	}
}
