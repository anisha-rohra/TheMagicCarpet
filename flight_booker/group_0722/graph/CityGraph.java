package flight_booker.group_0722.graph;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import flight_booker.group_0722.travel.Flight;

/**
 * An directed graph of CityNodes.
 * 
 * This code is based on E2.
 */
public class CityGraph implements Serializable {

    private static final long serialVersionUID = -6599257846445175252L;

    /** The Map of CityNodes to the HashSet of CityNodes it points at. */
	Map<CityNode, HashSet<CityNode>> cities;

    /**
     * Creates a new empty CityGraph.
     */
    public CityGraph() {
		this.cities = new HashMap<CityNode, HashSet<CityNode>>();
	}

    /**
	 * Returns a Set of CityNodes in this Graph.
	 * @return a Set of CityNodes in this Graph
	 */
	public Set<CityNode> getCities() {
		return cities.keySet();
	}

	/**
	 * Returns the number of CityNodes in this CityGraph.
	 * @return The number of CityNodes in this CityGraph.
	 */
	public int getNumCities() {
	    return getCities().size();	
	}

	/**
     * Returns the CityNode from this CityGraph with the given city.
     * @param city the city of the Node to return
     * @return the CityNode from this CityGraph with the given city
     */
    public CityNode cityToCityNode(String city) {
    	
    	addCityNode(city);
    	
    	Set<CityNode> nodeSet = getCities();
    	Iterator<CityNode> iterator = nodeSet.iterator();
	    while(iterator.hasNext()) {
	        CityNode setNode = iterator.next();
	        if (setNode.getCity().equals(city)) {
	        	return setNode;
	        }
	    }
	    return null;
    }

    /**
     * Returns a Set of destination CityNodes from this given CityNode.
     * @param cityNode the CityNode whose destinations are returned
     * @return a Set of possible destination CityNodes from this CityNode
     */
    public Set<CityNode> getConnectionNodes(CityNode cityNode) {
		return cities.get(cityNode);
    }

    /**
     * Returns whether there is a flight from origin and destination.
     * @param origin the CityNode to test pointing to destination
     * @param destination the CityNode to test for being pointed at by origin
     * @return true, if origin is directed at destination in this CityGraph,
     *    and false otherwise
     * @throws NoSuchCityException if origin or destination are not in this
     *    CityGraph
     */
    public boolean areOriginDestination(CityNode origin, CityNode destination) 
    		throws NoSuchCityException {
    	if (cities.containsKey(origin) && cities.containsKey(destination)) {
    		return getConnectionNodes(origin).contains(destination);
    	} else {
    		throw new NoSuchCityException("One or more of those cities does"
    				+ "not exist on this city graph.");
    	}
    }

    /**
     * Adds a new CityNode. Does nothing if the city already exists.
     * @param city the city name of the new CityNode
     */
    public void addCityNode(String city) {
    	CityNode newCityNode = new CityNode(city);
    	
    	boolean inSet = false;
    	Set<CityNode> nodeSet = cities.keySet();
    	Iterator<CityNode> iterator = nodeSet.iterator();
	    while(iterator.hasNext()) {
	        CityNode setNode = iterator.next();
	        if (setNode.getCity().equals(city)) {
	        	inSet = true;
	        }
	    }
    	
    	if (!inSet) {
        	cities.put(newCityNode, new HashSet<CityNode>());
    	}

    }

    /**
     * Adds a directed flight between the flight's origin to destination. 
     * Adds a new directional connection to the CityGraph if it did not 
     * already exist.
     * @param flight the flight to be added to the CityGraph
     * @throws NoSuchCityException if the origin or destination of the flight
     *    are not in this CityGraph
     */
    public void addFlight(flight_booker.group_0722.travel.Flight flight) {
    	
		CityNode origin = cityToCityNode(flight.getOrigin());
		CityNode destination = cityToCityNode(flight.getDestination());

    	if (origin != destination) {
    		    		
    		CityNode newOrigin = new CityNode(origin.getCity()) ;
    		newOrigin.setFlights((HashSet<Flight>) origin.getFlights());
    		newOrigin.addFlight(flight);
    		
    		Set<CityNode> newDestinations = new HashSet<CityNode>();
    		if (cities.containsKey(origin)) {
        		newDestinations = cities.get(origin);
        		cities.remove(origin);
    		}
    		newDestinations.add(destination);

    		cities.put(newOrigin, (HashSet<CityNode>) newDestinations);
    		
    		// update all the references to the origin CityNode in destinations
    		
        	Collection<HashSet<CityNode>> nodeSet = cities.values();
        	Iterator<HashSet<CityNode>> iterator = nodeSet.iterator();
    	    while (iterator.hasNext()) {
    	        HashSet<CityNode> listOfFlights = iterator.next();
    	        if (listOfFlights.contains(origin)) {
    	        	listOfFlights.add(newOrigin);
    	        	listOfFlights.remove(origin);
    	        }
    	    }
    	}
    }
   
//    public static void main(String[] args) {
//    	String testKey;
//    	HashSet<String> testValues;
//    	HashMap<String, HashSet<String>> testMap;
//    	
//    	testKey = "a";
//    	testValues = new HashSet<String>();
//    	testValues.add("1");
//    	testValues.add("2");
//    	testMap = new HashMap<String, HashSet<String>>();
//    	testMap.put(testKey, testValues);
//    	System.out.println(testMap);
//
//		testMap.get("a").add("3");
//    	System.out.println(testMap);
//    	
//    	System.out.println(testMap.get("a"));
//
//    	
//        }
}