package flight_booker.group_0722.system;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;
import java.util.Set;

import flight_booker.group_0722.graph.CityGraph;
import flight_booker.group_0722.graph.CityNode;
import flight_booker.group_0722.graph.NoSuchCityException;
import flight_booker.group_0722.travel.Flight;
import flight_booker.group_0722.user.Client;
import flight_booker.group_0722.user.NoSuchClientException;

/**
 * The system.
 *
 * @author Mark Franciscus
 */
public class FlightSystem implements Serializable, Observer {

	private static final long serialVersionUID = 6129957782320467996L;

	// userList is an empty ArrayList of Users.
	private ArrayList<Client> clientList;

	// pathways is an ArrayList of LinkedList of CityNodes
	private ArrayList<LinkedList<CityNode>> pathways;

	// cityGraph is a new CityGraph
	private CityGraph cityGraph = new CityGraph();

	// flightList is a new ArrayList of Flights
	private ArrayList<Flight> flightList = new ArrayList<Flight>();

	//instance is a static FlightSystem instance variable.
	private static FlightSystem instance = new FlightSystem();

	private FlightSystem() {
		this.clientList = new ArrayList<Client>();
		this.cityGraph = new CityGraph();
		this.flightList = new ArrayList<Flight>();
	}

	public static FlightSystem getInstance(){

		return instance;
	}

	public Map<String[], Client> idList = new HashMap<String[], Client>();


	public void breadthSearch(CityGraph graph, LinkedList<CityNode> visited, String destination)
			throws NoSuchCityException {
		Set<CityNode> nodes = getCityGraph().getConnectionNodes(visited.getLast());
		if (nodes.size() == 0) {
			LinkedList<CityNode> tempList = new LinkedList<CityNode>();
			tempList.addAll(visited);
			pathways.add(tempList);
		}

		// look at adjacent nodes
		for (CityNode node : nodes) {
			if (visited.contains(node)) {
				continue;
			}
			if (node.getCity().equals(destination)) {
				visited.add(node);
				visited.removeLast();
			}
		}
		// recursion needs to come after visiting adjacent nodes
		for (CityNode node : nodes) {
			if (visited.contains(node) || node.equals(destination)) {
				continue;
			}
			visited.addLast(node);
			breadthSearch(graph, visited, destination);
			visited.removeLast();
		}
	}

	/**
	 * Returns an ArrayList of Clients.
	 *
	 * @return userList
	 */
	public ArrayList<Client> getClientList() {
		return clientList;
	}

	/**
	 * Sets userList to the given ArrayList of Users.
	 *
	 * @param clientList
	 */
	public void setClientList(ArrayList<Client> clientList) {
		this.clientList = clientList;
	}

	/**
	 * Returns an ArrayList of Flights.
	 *
	 * @return flightList
	 */
	public ArrayList<Flight> getFlightList() {
		return flightList;
	}

	/**
	 * Sets flightList to the given ArrayList of Flights.
	 *
	 * @param flightList
	 */
	public void setFlightList(ArrayList<Flight> flightList) {
		this.flightList = flightList;
	}

	/**
	 * Returns an ArrayList of an ArrayList of Flights that is every possible
	 * way to get from the origin to the destination.
	 *
	 * @param departureDate
	 * @param origin
	 * @param destination
	 * @return finalFlightList
	 */
	public ArrayList<ArrayList<Flight>> searchFlight(Calendar departureDate, String origin,
													 String destination) {
		pathways = new ArrayList<LinkedList<CityNode>>();

		CityNode originNode = getCityGraph().cityToCityNode(origin);

		ArrayList<ArrayList<Flight>> flightArrayList = new ArrayList<ArrayList<Flight>>();
		boolean first = true;
		boolean found = false;

		LinkedList<CityNode> queue = new LinkedList<CityNode>();
		queue.add(originNode);

		try {
			breadthSearch(getCityGraph(), queue, destination);
		} catch (NoSuchCityException e) {
			e.printStackTrace();
		}
		ArrayList<LinkedList<CityNode>> prunePathways = new ArrayList<LinkedList<CityNode>>();
		for (LinkedList<CityNode> path : pathways) {
			if (path.getLast().getCity().equals(destination)) {
				prunePathways.add(path);
			} else if (path.contains(cityGraph.cityToCityNode(destination))){
				LinkedList<CityNode> tempPath = new LinkedList<CityNode>();
				for (CityNode city : path){
					if (city.equals(cityGraph.cityToCityNode(destination))){
						tempPath.add(city);
						prunePathways.add(tempPath);
						break;
					}
					tempPath.add(city);

				}
			}
		}
		Calendar departureCalendar = Calendar.getInstance();
		departureCalendar.setTime(departureDate.getTime());

		Calendar nextDay = new GregorianCalendar();
		nextDay.setTime(departureDate.getTime());
		nextDay.set(Calendar.DAY_OF_MONTH, departureCalendar.get(Calendar.DAY_OF_MONTH) + 1);

		for (LinkedList<CityNode> list : prunePathways) {

			Calendar currentTime = Calendar.getInstance();
			currentTime.setTime(departureCalendar.getTime());

			ArrayList<Flight> flightArray1 = new ArrayList<Flight>();
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getCity() != destination) {
					Set<Flight> flightArray = (Set<Flight>) list.get(i).getFlights();
					for (Flight flight : flightArray) {

						Calendar layoverDay = Calendar.getInstance();
						layoverDay.setTime(flight.getArrivalDateTime().getTime());
						layoverDay.add(Calendar.HOUR_OF_DAY, 6);

						if (first && !found
								&& list.contains(cityGraph.cityToCityNode(flight.getOrigin()))
								&& flight.getOrigin().equals(origin)
								&& flight.getDestination().equals(list.get(i + 1).getCity())
								&& flight.getDepartureDateTime().after(departureCalendar)
								&& flight.getDepartureDateTime().before(nextDay)) {
							if (flight.getOrigin().equals(origin) && flight.getDestination().equals(destination)){
								Flight addOneFlight = flight;
								ArrayList<Flight> tempOneFlightArray = new ArrayList<Flight>();
								tempOneFlightArray.add(addOneFlight);
								flightArrayList.add(tempOneFlightArray);
								found = false;
								first = true;
								continue;
							} else if (flight.getDestination().equals(destination)) {
								flightArrayList.add(flightArray1);
								continue;
							} else {
								first = false;
								Flight addFlight = flight;
								flightArray1.add(addFlight);
								ArrayList<Flight> tempFlightArray = new ArrayList<Flight>();
								tempFlightArray.addAll(flightArray1);
								flightArrayList.add(tempFlightArray);

								currentTime.setTime(flight.getArrivalDateTime().getTime());
							}


						} else if (!first && !found
								&& flight.getDestination().equals(list.get(i + 1).getCity())
								&& flight.getDepartureDateTime().after(currentTime)
								&& currentTime.before(flight.getDepartureDateTime())
								&& layoverDay.after(flight.getDepartureDateTime())) {
							if (flight.getDestination().equals(destination)){
								found = true;
								Flight addFlight = flight;
								flightArray1.add(addFlight);
								ArrayList<Flight> tempFlightArray = new ArrayList<Flight>();
								tempFlightArray.addAll(flightArray1);
								flightArrayList.add(tempFlightArray);
								flightArray1.remove(flightArray1.size()-1);

								currentTime.setTime(flight.getArrivalDateTime().getTime());
//
								continue;
							}
							Flight addFlight = flight;
							flightArray1.add(addFlight);

							currentTime.setTime(flight.getArrivalDateTime().getTime());
						}
					}
					found = false;
				}


			}
			first = true;
		}

		ArrayList<ArrayList<Flight>> finalFlightList = new ArrayList<ArrayList<Flight>>();
		for (ArrayList<Flight> test : flightArrayList) {
			if (test.get(test.size() - 1).getDestination().equals(destination) && !finalFlightList.contains(test)) {
				finalFlightList.add(test);
			}
		}
		return finalFlightList;
	}

	/**
	 * Returns a CityGraph
	 * @return cityGraph
	 */
	public CityGraph getCityGraph() {
		return cityGraph;
	}

	/**
	 * Sets a given CityGraph as cityGraph
	 * @param cityGraph
	 */
	public void setCityGraph(CityGraph cityGraph) {
		this.cityGraph = cityGraph;
	}

	public void loadPasswords(String filepath) throws FileNotFoundException {
		File file = new File(filepath);
		Scanner scan = new Scanner(file);
		while (scan.hasNextLine()) {
			String line = scan.nextLine();
			if (line == "") {
				break;
			}
			String email = "";
			int i = 0;
			while (line.charAt(i) != ',') {
				email += line.charAt(i);
				i++;
			}
			i++;
			String password = "";
			while (i < line.length()) {
				password += line.charAt(i);
				i++;
			}
			i++;

			Client newClient= new Client(email);

			for (Client c: clientList) {
				if (c.getEmail() == email) {
					newClient = c;
				}
			}

			idList.put(new String[] {email, password}, newClient);
		}

	}

	public void save() {
		try {
			FileOutputStream fileOut = new FileOutputStream("src/savedAppInfo.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(clientList);
			out.writeObject(flightList);
			out.writeObject(idList);
			out.close();
			fileOut.close();
		} catch(IOException i) {
			i.printStackTrace();
		}
	}

	public void load() {
		try {
			FileInputStream fileIn = new FileInputStream("src/savedAppInfo.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			setClientList((ArrayList<Client>) in.readObject());
			setFlightList((ArrayList<Flight>) in.readObject());
			setIdList((Map<String[], Client>) in.readObject());
		} catch(IOException i) {
			i.printStackTrace();
		} catch(ClassNotFoundException c) {
			c.printStackTrace();
		}
	}

	public boolean loginValidity(String email, String password) throws NoSuchClientException {
		if (idList.keySet().contains(new String[] {email, password})) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public void update(Observable o, Object arg) {
//		setFlightList((ArrayList<Flight>) arg);
		this.save();

	}

	public Map<String[], Client> getIdList() {
		return idList;
	}

	public void setIdList(Map<String[], Client> idList) {
		this.idList = idList;
	}

	public ArrayList<Flight> searchDirectFlights(String departureDate, String origin, String destination) {
		ArrayList<Flight> searchedFlights = new ArrayList<Flight>();

		for (Flight f: this.flightList) {

			if (f.getOrigin().equals(origin) && f.getDestination().equals(destination)) {
				int year = f.getDepartureDateTime().get(Calendar.YEAR);
				int month = f.getDepartureDateTime().get(Calendar.MONTH) + 1;
				int day = f.getDepartureDateTime().get(Calendar.DAY_OF_MONTH);
				String monthString = String.format("%02d", month);
				String dayString = String.format("%02d", day);
				String date = String.valueOf(year) + "-" + monthString + "-" + dayString;

				if (departureDate.equals(date)) {
					searchedFlights.add(f);
				}
			}
		}
		return searchedFlights;
	}
}