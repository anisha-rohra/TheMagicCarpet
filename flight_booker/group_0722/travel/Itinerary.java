package flight_booker.group_0722.travel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * An itinerary.
 * 
 * @author AkankshaShelat
 *
 */
public class Itinerary implements Serializable {

	private static final long serialVersionUID = -3581243619194742341L;

	/** The origin of the journey. **/
	private String origin;

	/** The destination of the journey. **/
	private String destination;

	/** The departure date of the journey. **/
	private String departureDate;

	/** The list of flights in this itinerary. **/
	private ArrayList<Flight> listOfFlights;

	/**
	 * Creates a new Itinerary.
	 * 
	 * @param departureDate
	 * @param origin
	 * @param destination
	 */
	public Itinerary(String origin, String destination, String departureDate) {

		this.destination = destination;
		this.origin = origin;
		this.departureDate = departureDate;
		this.listOfFlights = new ArrayList<Flight>();

	}

	/**
	 * Returns the list of flights in this itinerary.
	 * 
	 * @return the list of flights in this itinerary.
	 */
	public ArrayList<Flight> getListOfFlights() {
		return listOfFlights;
	}

	/**
	 * Sets the list of flights in this itinerary.
	 * 
	 * @param listOfFlights in this itinerary.
	 */
	public void setListOfFlights(ArrayList<Flight> listOfFlights) {
		this.listOfFlights = listOfFlights;
	}

	/**
	 * Returns the origin of this journey.
	 * 
	 * @return the origin of this journey.
	 */
	public String getOrigin() {
		return origin;
	}

	/**
	 * Sets the origin of this journey.
	 * 
	 * @param origin of this journey.
	 */
	public void setOrigin(String origin) {
		this.origin = origin;
	}

	/**
	 * Returns the destination of this journey.
	 * 
	 * @return the destination of this journey.
	 */
	public String getDestination() {
		return destination;
	}

	/**
	 * Sets the destination of this journey.
	 * 
	 * @param destination of this journey.
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}

	/**
	 * Returns the date of the departure.
	 * 
	 * @return the date of the departure.
	 */
	public String getDepartureDate() {
		return departureDate;
	}

	/**
	 * Sets the date of the departure.
	 * 
	 * @param departureDate of the departure.
	 */
	public void setDepartureDate(String departureDate) {
		this.departureDate = departureDate;
	}

	/**
	 * Returns the total travel time for entire journey from Origin to
	 * Destination.
	 * 
	 * @return total travel time for entire journey
	 */
	public Long[] getTotalTime() {

		// totalTime of the format: {hours,minutes}
		Long[] totalTime = new Long[2];
		Flight originFlight = listOfFlights.get(0);
		Flight destinationFlight = listOfFlights.get((listOfFlights.size() - 1));
		Calendar arrivalDate = destinationFlight.getArrivalDateTime();
		Calendar departureDate = originFlight.getDepartureDateTime();
		long diff = arrivalDate.getTime().getTime() - departureDate.getTime().getTime();
		long diffMinutes = diff / (60 * 1000) % 60;
		long diffHours = diff / (60 * 60 * 1000);

		totalTime[0] = diffHours;
		totalTime[1] = diffMinutes;

		return totalTime;

	}

	/**
	 * Returns the total cost for entire journey from Origin to Destination.
	 * 
	 * @return total cost for entire journey
	 */
	public double getTotalCost() {

		double totalCost = 0;
		Flight flight;
		for (int i = 0; i < this.listOfFlights.size(); i++) {
			flight = this.listOfFlights.get(i);
			totalCost = totalCost + flight.getCost();
		}
		
		return totalCost;
	}

	/**
	 * Returns this itinerary where each flight is formatted as:
	 * Number,DepartureDateTime,ArrivalDateTime,Airline,Origin, Destination
	 * followed by total price (on its own line, exactly two decimal places),
	 * followed by total duration (on its own line, in format HH:MM).
	 */
	@Override
	public String toString() {
		String itineraryAsString = "";
		for (Flight flight : listOfFlights) {
			itineraryAsString += flight.toString().substring(0, flight.toString().lastIndexOf(",")) + "\n";
		}
		String formattedCost = String.format("%.2f", getTotalCost());
		String formattedTime = String.format("%02d", getTotalTime()[0]) + ":" + String.format("%02d", getTotalTime()[1]); 
		itineraryAsString += formattedCost + "\n" + formattedTime + "\n";
		return itineraryAsString.substring(0, itineraryAsString.length() - 1);
	}

}
