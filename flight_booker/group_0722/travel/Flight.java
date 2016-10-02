package flight_booker.group_0722.travel;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Observable;

/**
 * A flight.
 * 
 * @author AkankshaShelat
 *
 */
public class Flight extends Observable implements Serializable{

	private static final long serialVersionUID = -188997764349365283L;

	/** The departure date of the flight. **/
	private Calendar departureDateTime;

	/** The arrival date of the flight. **/
	private Calendar arrivalDateTime;

	/** The flight number of the flight. **/
	private String flightNumber;

	/** The origin of the flight. **/
	private String origin;

	/** The destination of the flight. **/
	private String destination;

	/** The airline of the flight. **/
	private String airline;

	/** The cost of the flight. **/
	private double cost;

	/**The number of seats in this flight.**/
	private int numSeats;

	/** The travel time of the flight **/
	private long[] travelTime;

	/**
	 * Creates a new Flight.
	 * 
	 * @param departureDateTime
	 * @param arrivalDateTime
	 * @param flightNumber
	 * @param origin
	 * @param destination
	 * @param airline
	 * @param cost
	 * @param numSeats
	 */
	public Flight(String flightNumber, Calendar departureDateTime, Calendar arrivalDateTime, String airline,
			String origin, String destination, double cost, int numSeats) {
		this.departureDateTime = departureDateTime;
		this.arrivalDateTime = arrivalDateTime;
		this.airline = airline;
		this.cost = cost;
		this.origin = origin;
		this.destination = destination;
		this.flightNumber = flightNumber;
		this.numSeats = numSeats;
		this.travelTime = new long[2];
        long diff = arrivalDateTime.getTime().getTime() - departureDateTime.getTime().getTime();
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000);
        this.travelTime[0] = diffHours;
        this.travelTime[1] = diffMinutes;

//		this.travelTime[0] = departureDateTime.until(arrivalDateTime, ChronoUnit.HOURS);
//		this.travelTime[1] = departureDateTime.until(arrivalDateTime, ChronoUnit.MINUTES) % 60;

	}

	/**
	 * Returns the date and time of the departure flight.
	 * 
	 * @return the date and time of the departure flight.
	 */
	public Calendar getDepartureDateTime() {
		return departureDateTime;
	}

	/**
	 * Sets the departureDateTime for this flight.
	 * 
	 * @param departureDateTime
	 */
	public void setDepartureDateTime(Calendar departureDateTime) {
		this.departureDateTime = departureDateTime;
        setChanged();
        notifyObservers(departureDateTime);
	}

	/**
	 * Returns the date and time of the arrival flight.
	 * 
	 * @return the date and time of the arrival flight.
	 */
	public Calendar getArrivalDateTime() {
		return arrivalDateTime;
	}

	/**
	 * Sets the arrivalDateTime for this flight.
	 * 
	 * @param arrivalDateTime
	 */
	public void setArrivalDateTime(Calendar arrivalDateTime) {
		this.arrivalDateTime = arrivalDateTime;
        setChanged();
        notifyObservers(arrivalDateTime);
	}

	/**
	 * Returns the flight number for this flight.
	 * 
	 * @return the flight number for this flight.
	 */
	public String getFlightNumber() {
		return flightNumber;
	}

	/**
	 * Returns the origin of this flight.
	 * 
	 * @return the origin of this flight.
	 */
	public String getOrigin() {
		return origin;
	}

	/**
	 * Sets the origin of this flight.
	 * 
	 * @param origin of this flight.
	 */
	public void setOrigin(String origin) {
		this.origin = origin;
        setChanged();
        notifyObservers(origin);
	}

	/**
	 * Returns the destination of this flight.
	 * 
	 * @return the destination of this flight.
	 */
	public String getDestination() {
		return destination;
	}

	/**
	 * Sets the destination of this flight.
	 * 
	 * @param destination of this flight.
	 */
	public void setDestination(String destination) {
		this.destination = destination;
        setChanged();
        notifyObservers(destination);
	}

	/**
	 * Returns the airline of this flight.
	 * 
	 * @return the airline of this flight.
	 */
	public String getAirline() {
		return airline;
	}

	/**
	 * Sets the airline of this flight.
	 * 
	 * @param airline of this flight.
	 */
	public void setAirline(String airline) {
		this.airline = airline;
        setChanged();
        notifyObservers();
	}

	/**
	 * Returns the cost of this flight.
	 * 
	 * @return the cost of this flight.
	 */
	public double getCost() {
		return cost;
	}

	/**
	 * Sets the cost of this flight.
	 * 
	 * @param cost of this flight.
	 */
	public void setCost(double cost) {
		this.cost = cost;
        setChanged();
        notifyObservers(cost);
	}

	public int getNumSeats() {
		return numSeats;
	}

		/**
	 * Sets this Flight's numSeats to newNumSeats and notifies its Observers.
	 * @param newNumSeats This Flights's new number of seats.
	 */
	public void setNumSeats(int newNumSeats) {
		this.numSeats = newNumSeats;
        setChanged();
        notifyObservers(numSeats);
	}

	/**
	 * Returns this flight as a String with the format:
	 * Number,DepartureDateTime,ArrivalDateTime,Airline,Origin,
	 * Destination,Price (the dates are in the format YYYY-MM-DD; the price has
	 * exactly two decimal places)
	 */
	@Override
	public String toString() {
		String formattedCost = String.format("%.2f", cost);
		Date arrivalDate = arrivalDateTime.getTime();
		Date departureDate = departureDateTime.getTime();
		String pattern = "yyyy-MM-dd HH:mm";
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return flightNumber + "," + format.format(departureDate) + ","
				+ format.format(arrivalDate) + "," + airline + "," + origin
				+ "," + destination + "," + formattedCost;
	}

	/**
	 * Returns the travel time of this flight.
	 * @return travelTime
	 */
	public long[] getTravelTime() {
		return travelTime;
	}

	/**
	 * Sets the travel time of this flight.
	 * @param travelTime
	 */
	public void setTravelTime(long[] travelTime) {
		this.travelTime = travelTime;
        setChanged();
        notifyObservers(travelTime);
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Flight flight = (Flight) o;

        if (Double.compare(flight.cost, cost) != 0) return false;
        if (numSeats != flight.numSeats) return false;
        if (departureDateTime != null ? !departureDateTime.equals(flight.departureDateTime) : flight.departureDateTime != null)
            return false;
        if (arrivalDateTime != null ? !arrivalDateTime.equals(flight.arrivalDateTime) : flight.arrivalDateTime != null)
            return false;
        if (flightNumber != null ? !flightNumber.equals(flight.flightNumber) : flight.flightNumber != null)
            return false;
        if (origin != null ? !origin.equals(flight.origin) : flight.origin != null) return false;
        if (destination != null ? !destination.equals(flight.destination) : flight.destination != null)
            return false;
        if (airline != null ? !airline.equals(flight.airline) : flight.airline != null)
            return false;
        return Arrays.equals(travelTime, flight.travelTime);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = departureDateTime != null ? departureDateTime.hashCode() : 0;
        result = 31 * result + (arrivalDateTime != null ? arrivalDateTime.hashCode() : 0);
        result = 31 * result + (flightNumber != null ? flightNumber.hashCode() : 0);
        result = 31 * result + (origin != null ? origin.hashCode() : 0);
        result = 31 * result + (destination != null ? destination.hashCode() : 0);
        result = 31 * result + (airline != null ? airline.hashCode() : 0);
        temp = Double.doubleToLongBits(cost);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + numSeats;
        result = 31 * result + (travelTime != null ? Arrays.hashCode(travelTime) : 0);
        return result;
    }

    //	public void updateNumSeats(int newNumSeats) {
//		numSeats = newNumSeats;
//		setChanged();
//		notifyObservers("Updated number of available seats to " + numSeats + ".");
//	}

}
