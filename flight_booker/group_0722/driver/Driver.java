//package flight_booker.group_0722.driver;
//
//import java.io.FileNotFoundException;
//import java.util.ArrayList;
//
//import flight_booker.group_0722.graph.NoSuchCityException;
//import flight_booker.group_0722.travel.Flight;
//import flight_booker.group_0722.travel.Itinerary;
//
//// "In Phase II, there are no features that modify the data, so there is
//// nothing to save between runs of the program."
//
///** A Driver used for autotesting the project backend. */
//public class Driver {
//
//	static User driverUser = new User(null, null);
//
//	/**
//	 * Uploads client information to the application from the file at the given
//	 * path.
//	 *
//	 * @param path
//	 *            the path to an input csv file of client information with lines
//	 *            in the format:
//	 *            LastName,FirstNames,Email,Address,CreditCardNumber,ExpiryDate
//	 *            (the ExpiryDate is stored in the format YYYY-MM-DD)
//	 */
//	public static void uploadClientInfo(String path) {
//		try {
//			driverUser.setUserList(path);
//		} catch (FileNotFoundException e) {
//			System.out.println("File not found");
//		}
//	}
//
//	/**
//	 * Uploads flight information to the application from the file at the given
//	 * path.
//	 *
//	 * @param path
//	 *            the path to an input csv file of flight information with lines
//	 *            in the format:
//	 *            Number,DepartureDateTime,ArrivalDateTime,Airline,Origin,
//	 *            Destination,Price (the dates are in the format YYYY-MM-DD; the
//	 *            price has exactly two decimal places)
//	 */
//	public static void uploadFlightInfo(String path) {
//		try {
//			driverUser.setFlightList(path);
//		} catch (FileNotFoundException e) {
//			System.out.println("File not found");
//		} catch (NoSuchCityException e) {
//			System.out.println(e.getMessage());
//		}
//
//	}
//
//	/**
//	 * Returns the information stored for the client with the given email.
//	 *
//	 * @param email
//	 *            the email address of a client
//	 * @return the information stored for the client with the given email in
//	 *         this format:
//	 *         LastName,FirstNames,Email,Address,CreditCardNumber,ExpiryDate
//	 *         (the ExpiryDate is stored in the format YYYY-MM-DD)
//	 */
//	public static String getClient(String email) {
//		for (User user : driverUser.getUserList()) {
//			if (user.getEmail().equals(email)) {
//				return user.toString();
//			}
//		}
//		return "There is no such user with email " + email;
//	}
//
//	/**
//	 * Returns all flights that depart from origin and arrive at destination on
//	 * the given date.
//	 *
//	 * @param date
//	 *            a departure date (in the format YYYY-MM-DD)
//	 * @param origin
//	 *            a flight origin
//	 * @param destination
//	 *            a flight destination
//	 * @return the flights that depart from origin and arrive at destination on
//	 *         the given date formatted with one flight per line in exactly this
//	 *         format: Number,DepartureDateTime,ArrivalDateTime,Airline,Origin,
//	 *         Destination,Price (the dates are in the format YYYY-MM-DD; the
//	 *         price has exactly two decimal places)
//	 */
//	public static String getFlights(String date, String origin, String destination) {
//
//		ArrayList<Flight> flightList = driverUser.getFlightList();
//		ArrayList<Flight> matchingFlightList = new ArrayList<Flight>();
//
//		// convert date from a String to a LocalDate object
//		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//		LocalDate localDate = LocalDate.parse(date, format);
//
//		// find matching flights
//		for (Flight flight : flightList) {
//			if (flight.getOrigin().equals(origin)
//					&& flight.getDestination().equals(destination)
//					&& flight.getDepartureDateTime().toLocalDate().equals(localDate)) {
//				matchingFlightList.add(flight);
//			}
//		}
//
//		String flightsAsString = "";
//		for (Flight matchingFlight : matchingFlightList) {
//			flightsAsString += matchingFlight.toString() + "\n";
//		}
//		return flightsAsString.trim();
//	}
//
//	/**
//	 * Returns all itineraries that depart from origin and arrive at destination
//	 * on the given date. If an itinerary contains two consecutive flights F1
//	 * and F2, then the destination of F1 should match the origin of F2. To
//	 * simplify our task, if there are more than 6 hours between the arrival of
//	 * F1 and the departure of F2, then we do not consider this sequence for a
//	 * possible itinerary (we judge that the stopover is too long).
//	 *
//	 * @param date
//	 *            a departure date (in the format YYYY-MM-DD)
//	 * @param origin
//	 *            a flight original
//	 * @param destination
//	 *            a flight destination
//	 * @return itineraries that depart from origin and arrive at destination on
//	 *         the given date with stopovers at or under 6 hours. Each itinerary
//	 *         in the output should contain one line per flight, in the format:
//	 *         Number,DepartureDateTime,ArrivalDateTime,Airline,Origin,
//	 *         Destination followed by total price (on its own line, exactly two
//	 *         decimal places), followed by total duration (on its own line, in
//	 *         format HH:MM).
//	 */
//	public static String getItineraries(String date, String origin, String destination) {
//		ArrayList<Itinerary> listItineraries = new ArrayList<Itinerary>();
//
//		try {
//			listItineraries = driverUser.searchItineraries(date, origin, destination);
//		} catch (NoSuchCityException e) {
//			System.out.println("No such city exists.");
//			return null;
//		}
//
//		String itinerariesAsString = "";
//		for (Itinerary itinerary : listItineraries) {
//			itinerariesAsString += itinerary + "\n";
//		}
//		return itinerariesAsString.trim();
//	}
//
//	/**
//	 * Returns the same itineraries as getItineraries produces, but sorted
//	 * according to total itinerary cost, in non-decreasing order.
//	 *
//	 * @param date
//	 *            a departure date (in the format YYYY-MM-DD)
//	 * @param origin
//	 *            a flight original
//	 * @param destination
//	 *            a flight destination
//	 * @return itineraries (sorted in non-decreasing order of total itinerary
//	 *         cost) that depart from origin and arrive at destination on the
//	 *         given date with stopovers at or under 6 hours. Each itinerary in
//	 *         the output should contain one line per flight, in the format:
//	 *         Number,DepartureDateTime,ArrivalDateTime,Airline,Origin,
//	 *         Destination followed by total price (on its own line, exactly two
//	 *         decimal places), followed by total duration (on its own line, in
//	 *         format HH:MM).
//	 */
//	public static String getItinerariesSortedByCost(String date, String origin, String destination) {
//        ArrayList<Itinerary> listItineraries = new ArrayList<Itinerary>();
//        try {
//                listItineraries = driverUser.displayByCost(
//                                driverUser.searchItineraries(date, origin, destination));
//        } catch (NoSuchCityException e) {
//                System.out.println("No such city exists.");
//                return null;
//        }
//
//        String itinerariesAsString = "";
//        for (Itinerary itinerary : listItineraries) {
//                itinerariesAsString += itinerary + "\n";
//        }
//        return itinerariesAsString.trim();
//	}
//
//	/**
//	 * Returns the same itineraries as getItineraries produces, but sorted
//	 * according to total itinerary travel time, in non-decreasing order.
//	 *
//	 * @param date
//	 *            a departure date (in the format YYYY-MM-DD)
//	 * @param origin
//	 *            a flight original
//	 * @param destination
//	 *            a flight destination
//	 * @return itineraries (sorted in non-decreasing order of travel itinerary
//	 *         travel time) that depart from origin and arrive at destination on
//	 *         the given date with stopovers at or under 6 hours. Each itinerary
//	 *         in the output should contain one line per flight, in the format:
//	 *         Number,DepartureDateTime,ArrivalDateTime,Airline,Origin,
//	 *         Destination followed by total price (on its own line, exactly two
//	 *         decimal places), followed by total duration (on its own line, in
//	 *         format HH:MM).
//	 */
//	public static String getItinerariesSortedByTime(String date, String origin, String destination) {
//        ArrayList<Itinerary> listItineraries = new ArrayList<Itinerary>();
//        try {
//                listItineraries = driverUser.displayByTime(
//                                driverUser.searchItineraries(date, origin, destination));
//        } catch (NoSuchCityException e) {
//                System.out.println("No such city exists.");
//                return null;
//        }
//
//        String itinerariesAsString = "";
//        for (Itinerary itinerary : listItineraries) {
//                itinerariesAsString += itinerary + "\n";
//        }
//        return itinerariesAsString.trim();
//	}
//
//}
