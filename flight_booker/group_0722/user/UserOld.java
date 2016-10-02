//package flight_booker.group_0722.user;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Scanner;
//
//import flight_booker.group_0722.graph.NoSuchCityException;
//import flight_booker.group_0722.system.FlightSystem;
//import flight_booker.group_0722.travel.Flight;
//import flight_booker.group_0722.travel.Itinerary;
//
///**
// * The class that the user input interacts with, able to upload information into
// * the system from csv files, search for flights or itineraries, and view all
// * stored client information.
// *
// * @author Anisha Rohra
// */
//public class UserOld {
//
//	private ArrayList<UserOld> userList;
//
//	private ArrayList<Flight> flightList;
//
//	private String email;
//
//	private String[] information;
//
//	private FlightSystem flightSystem;
//
//	/**
//	 * Initializes a User with parameters String email and String[] of
//	 * information with 5 elements. Gets an ArrayList of user information from
//	 * FlightSystem of user's and gets an ArrayList of Flights from
//	 * FlightSystem.
//	 *
//	 * @param email
//	 * @param information
//	 */
//	public UserOld(String email, String[] information) {
//		this.flightSystem = new FlightSystem();
//		this.userList = flightSystem.getUserList();
//		this.flightList = flightSystem.getFlightList();
//		this.email = email;
//		this.information = information;
//	}
//
//	/**
//	 * Takes a csv file as a parameter and adds every line of information to
//	 * this.userInformation.
//	 *
//	 * @param filePath
//	 * @throws FileNotFoundException
//	 */
//	public void setUserList(String filePath) throws FileNotFoundException {
//		Scanner scan = new Scanner(new File(filePath));
//		while (scan.hasNextLine()) { // while there's another line in the file
//			String line = scan.nextLine();
//			if (line.isEmpty()) { // if the next line is empty, stop the loop
//				break;
//			}
//			int i = 0; // to keep track of the iterator placement in the line
//			int n = 0; // to keep track of information in store
//			String email = new String();
//			String[] newInformation = new String[5];
//			while (i < line.length()) { // checks every character in the line
//				String store = "";
//				while (i < line.length() && line.charAt(i) != ',') {
//					store += line.charAt(i);
//					i++;
//				}
//				i++;
//				n++;
//				switch (n) { // stores area into appropriate place for user
//				case 1:
//					newInformation[0] = store;
//					break;
//				case 2:
//					newInformation[1] = store;
//					break;
//				case 3:
//					email = store;
//					break;
//				case 4:
//					newInformation[2] = store;
//					break;
//				case 5:
//					newInformation[3] = store;
//					break;
//				case 6:
//					newInformation[4] = store;
//					break;
//				}
//			}
//			this.userList.add(new UserOld(email, newInformation));
//		}
//		scan.close();
//		flightSystem.setUserList(this.userList); // updates FlightSystem
//	}
//
//	/**
//	 * Returns this.userInfomration so that this user can view all of the user's
//	 * information.
//	 *
//	 * @return ArrayList<User>
//	 */
//	public ArrayList<User> getUserList() {
//		return this.userList;
//	}
//
//	/**
//	 * Takes a csv file as a parameter and creates a Flight from every line of
//	 * information and adds that flight to the listOfFlights.
//	 *
//	 * @param filePath
//	 * @throws FileNotFoundException
//	 * @throws NoSuchCityException
//	 */
//	public void setFlightList(String filePath) throws FileNotFoundException, NoSuchCityException {
//		Scanner scan = new Scanner(new File(filePath));
//		while (scan.hasNextLine()) { // while there's another line in the file
//			String line = scan.nextLine();
//			if (line.isEmpty()) { // if the line is empty, break from the loop
//				break;
//			}
//			int i = 0; // to keep track of the iterator's placement in a line
//			int n = 0; // to keep track of information in store
//			String[] flightInformation = new String[7];
//			while (i < line.length()) {
//				String store = "";
//				while (i < line.length() && line.charAt(i) != ',') {
//					store += line.charAt(i);
//					i++;
//				}
//				i++;
//				n++;
//				switch (n) { // checks which piece of flight information
//				case 1:
//					flightInformation[0] = store;
//					break;
//				case 2:
//					flightInformation[1] = store;
//					break;
//				case 3:
//					flightInformation[2] = store;
//					break;
//				case 4:
//					flightInformation[3] = store;
//					break;
//				case 5:
//					flightInformation[4] = store;
//					break;
//				case 6:
//					flightInformation[5] = store;
//					break;
//				case 7:
//					flightInformation[6] = store;
//					break;
//				}
//			}
//			// convert String to Double
//			double costDouble = Double.parseDouble(flightInformation[6]);
//			DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//			LocalDateTime departureDateTime = LocalDateTime.parse(flightInformation[1], format);
//			LocalDateTime arrivalDateTime = LocalDateTime.parse(flightInformation[2], format);
//			Flight newFlight = new Flight(flightInformation[0], departureDateTime, arrivalDateTime,
//					flightInformation[3], flightInformation[4], flightInformation[5], costDouble);
//			flightList.add(newFlight);
//
//			// update FlightSystem's city graph
//			flightSystem.getCityGraph().addFlight(newFlight);
//		}
//		scan.close();
//
//	}
//
//	/**
//	 * Takes a depeartureDate, origin, and destination and returns an ArrayList
//	 * of ArrayList of Flights that matches the parameters.
//	 *
//	 * @param departureDate
//	 * @param origin
//	 * @param destination
//	 * @return ArrayList<ArrayList<<Flight>>
//	 * @throws NoSuchCityException
//	 */
//	public ArrayList<ArrayList<Flight>> searchFlights(String departureDate, String origin, String destination)
//			throws NoSuchCityException {
//		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//		LocalDate departureDateTime = LocalDate.parse(departureDate, format);
//		return flightSystem.searchFlight(departureDateTime, origin, destination);
//		// calls the search algorithm in FlightSystem with the needed parameters
//	}
//
//	/**
//	 * Takes a depeartureDate, origin, and destination and returns an ArrayList
//	 * of Itineraries that matches the parameters.
//	 *
//	 * @param departureDate
//	 * @param origin
//	 * @param destination
//	 * @return ArrayList<Itinerary>
//	 * @throws NoSuchCityException
//	 */
//	public ArrayList<Itinerary> searchItineraries(String departureDate, String origin, String destination)
//			throws NoSuchCityException {
//		ArrayList<Itinerary> itineraryList = new ArrayList<Itinerary>();
//		ArrayList<ArrayList<Flight>> searchedFlights = searchFlights(departureDate, origin, destination);
//		for (ArrayList<Flight> flights : searchedFlights) {
//			// make a new itinerary
//			Itinerary itinerary = new Itinerary(origin, destination, departureDate);
//			itinerary.setListOfFlights(flights);
//			itineraryList.add(itinerary);
//		}
//		return itineraryList;
//	}
//
//	/**
//	 * Takes an ArrayList of Itineraries and returns an ArrayList of Itineraries
//	 * sorted by their total time, fastest to slowest.
//	 *
//	 * @param itineraryList
//	 * @return ArrayList<Itinerary>
//	 */
//	public ArrayList<Itinerary> displayByTime(ArrayList<Itinerary> itineraryList) {
//		ArrayList<Itinerary> sortedByTime = new ArrayList<Itinerary>();
//		Map<Double, Itinerary> hashMapSort = new HashMap<Double, Itinerary>();
//		// maps the itineraries against their total time
//		Double[] totalTimeArray = new Double[itineraryList.size()];
//		for (int i = 0; i < itineraryList.size(); i++) {
//			Long[] totalTime = itineraryList.get(i).getTotalTime();
//			double actualTime = totalTime[0] + (totalTime[1] / 100);
//			hashMapSort.put(actualTime, itineraryList.get(i));
//			totalTimeArray[i] = actualTime;
//		}
//		Arrays.sort(totalTimeArray); // sort in ascending order
//		for (Double time : totalTimeArray) {
//			sortedByTime.add(hashMapSort.get(time));
//		}
//		return sortedByTime;
//	}
//
//	/**
//	 * Takes an ArrayList of Itineraries and returns an ArrayList of Itineraries
//	 * sort by their total cost, least expensive to most expensive.
//	 *
//	 * @param itineraryList
//	 * @return ArrayList<Itinerary>
//	 */
//	public ArrayList<Itinerary> displayByCost(ArrayList<Itinerary> itineraryList) {
//		ArrayList<Itinerary> sortedByCost = new ArrayList<Itinerary>();
//		Map<Double, Itinerary> hashMapSort = new HashMap<Double, Itinerary>();
//		// maps the itineraries against their total cost
//		Double[] totalCostArray = new Double[itineraryList.size()];
//		for (int i = 0; i < itineraryList.size(); i++) {
//			hashMapSort.put(itineraryList.get(i).getTotalCost(), itineraryList.get(i));
//			totalCostArray[i] = itineraryList.get(i).getTotalCost();
//		}
//		Arrays.sort(totalCostArray); // sort in ascending order
//		for (Double cost : totalCostArray) {
//			sortedByCost.add(hashMapSort.get(cost));
//		}
//		return sortedByCost;
//	}
//
//	/**
//	 * Gets the current flightList.
//	 *
//	 * @return ArrayList<Flight>
//	 */
//	public ArrayList<Flight> getFlightList() {
//		return flightList;
//	}
//
//	/**
//	 * Gets the email of the user.
//	 *
//	 * @return String
//	 */
//	public String getEmail() {
//		return email;
//	}
//
//	/**
//	 * Set the email of the user to the give email parameter.
//	 *
//	 * @param email
//	 */
//	public void setEmail(String email) {
//		this.email = email;
//	}
//
//	/**
//	 * Gets the information array of the user, containing their last name, first
//	 * name, address, credit card number, expiry date.
//	 *
//	 * @return
//	 */
//	public String[] getInformation() {
//		return information;
//	}
//
//	/**
//	 * Sets the information array to the given information parameter.
//	 *
//	 * @param information
//	 */
//	public void setInformation(String[] information) {
//		this.information = information;
//	}
//
//	/**
//	 * Returns a string representation of this user in the form:
//	 * LastName,FirstNames,Email,Address,CreditCardNumber,ExpiryDate (where the
//	 * ExpiryDate is stored in the format YYYY-MM-DD)
//	 */
//	@Override
//	public String toString() {
//		String infoAsString = "";
//		int n = 0;
//		for (int i = 0; i < 6; i++) {
//			if (i == 2) {
//				infoAsString += email + ",";
//				continue;
//			}
//			infoAsString += information[n] + ",";
//			n++;
//
//		}
//		return infoAsString.substring(0, infoAsString.length() - 1);
//	}
//
//}
