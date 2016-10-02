package flight_booker.group_0722.graph;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import flight_booker.group_0722.travel.Flight;

/**
 * A node in CityGraph that has a city (represented by a name or airport
 * code) as a key and flights leaving from that city as values.
 * 
 * This code is based on E2.
 */
public class CityNode implements Serializable {

    private static final long serialVersionUID = 5239809561746689837L;

    /** The city stored in this CityNode. */
    private String city; 
    
    /** The list of flights that leave this city. */
    private Set<Flight> flights;
    
    /**
     * Creates a new CityNode with the given city.
     * @param city the city
     */
    public CityNode(String city) {
        this.city = city;
        this.flights = new HashSet<Flight>();
    }

    /**
     * Returns the city of this CityNode.
     * @return the city of this CityNode
     */
    public String getCity() {
        return city;
    }
   
    /**
     * Returns the flights that leave from this CityNode.
     * @return the flights that leave from this CityNode.
     */
    public Set<Flight> getFlights() {
		return flights;
	}

    /**
     * Sets the flights that leave from this CityNode.
     * @param flights the flights that leave from this CityNode.
     */
	public void setFlights(HashSet<Flight> flights) {
		this.flights = flights;
	}
	
	/**
	 * Adds a flight to the list of flights that leave this CityNode.
	 * @param flight a flight that leaves this CityNode.
	 */
	public void addFlight(Flight flight) {
		this.flights.add(flight);
	}
	
	@Override
	public boolean equals(Object other) {
		return other instanceof CityNode && ((CityNode) other).city.equals(this.city);
	}

	
	
	
}
