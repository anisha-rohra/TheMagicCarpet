package flight_booker.group_0722.user;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.text.ParseException;

import flight_booker.group_0722.graph.NoSuchCityException;
import flight_booker.group_0722.system.FlightSystem;
import flight_booker.group_0722.travel.Flight;
import flight_booker.group_0722.travel.Itinerary;

/**
 * Created by anisharohra on 2015-11-25.
 */
public class Client extends Observable implements Serializable {

    private static final long serialVersionUID = -531039443636626101L;

    private String email;

    private String lastName;

    private String firstNames;

    private String address;

    private String creditCardNumber;

    private String expiryDate;

    private FlightSystem flightSystem;

    private ArrayList<Itinerary> bookings;

    public Client(String email, String lastName, String firstNames, String address, String creditCardNumber, String expiryDate) {
        this.email = email;
        this.lastName = lastName;
        this.firstNames = firstNames;
        this.address = address;
        this.creditCardNumber = creditCardNumber;
        this.expiryDate = expiryDate;
        this.flightSystem = FlightSystem.getInstance();
        this.bookings = new ArrayList<Itinerary>();
        addObserver(flightSystem);

    }

    public Client(String email) {
        this.email = email;
        this.flightSystem = FlightSystem.getInstance();
        this.bookings = new ArrayList<Itinerary>();
    }

    /**
     * Takes a depeartureDate, origin, and destination and returns an ArrayList
     * of Flights that matches the parameters.
     *
     * @param departureDate
     * @param origin
     * @param destination
     * @return ArrayList<ArrayList<<Flight>>
     * @throws NoSuchCityException
     */
    public ArrayList<Flight> searchFlights(String departureDate, String origin, String destination) {
        return flightSystem.searchDirectFlights(departureDate, origin, destination);
        //will end up returning ArrayList<Flight>
    }

    /**
     * Takes a depeartureDate, origin, and destination and returns an ArrayList
     * of Itineraries that matches the parameters.
     *
     * @param departureDate
     * @param origin
     * @param destination
     * @return ArrayList<Itinerary>
     * @throws NoSuchCityException
     */
    public ArrayList<Itinerary> searchItineraries(String departureDate, String origin, String destination)
            throws NoSuchCityException, ParseException {
        ArrayList<Itinerary> itineraryList = new ArrayList<Itinerary>();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Calendar departureDateTime  = Calendar.getInstance();
        departureDateTime.setTime(df.parse(departureDate));
        ArrayList<ArrayList<Flight>> searchedFlights = flightSystem.searchFlight(departureDateTime, origin, destination);
        for (ArrayList<Flight> flights : searchedFlights) {
            // make a new itinerary
            Itinerary itinerary = new Itinerary(origin, destination, departureDate);
            itinerary.setListOfFlights(flights);
            itineraryList.add(itinerary);
        }
        return itineraryList;
    }

    /**
     * Takes an ArrayList of Itineraries and returns an ArrayList of Itineraries
     * sorted by their total time, fastest to slowest.
     *
     * @param itineraryList
     * @return ArrayList<Itinerary>
     */
    public ArrayList<Itinerary> displayByTime(ArrayList<Itinerary> itineraryList) {
        ArrayList<Itinerary> sortedByTime = new ArrayList<Itinerary>();
        Map<Double, Itinerary> hashMapSort = new HashMap<Double, Itinerary>();
        // maps the itineraries against their total time
        Double[] totalTimeArray = new Double[itineraryList.size()];
        for (int i = 0; i < itineraryList.size(); i++) {
            Long[] totalTime = itineraryList.get(i).getTotalTime();
            double actualTime = totalTime[0] + (totalTime[1] / 100);
            hashMapSort.put(actualTime, itineraryList.get(i));
            totalTimeArray[i] = actualTime;
        }
        Arrays.sort(totalTimeArray); // sort in ascending order
        for (Double time : totalTimeArray) {
            sortedByTime.add(hashMapSort.get(time));
        }
        return sortedByTime;
    }

    /**
     * Takes an ArrayList of Itineraries and returns an ArrayList of Itineraries
     * sort by their total cost, least expensive to most expensive.
     *
     * @param itineraryList
     * @return ArrayList<Itinerary>
     */
    public ArrayList<Itinerary> displayByCost(ArrayList<Itinerary> itineraryList) {
        ArrayList<Itinerary> sortedByCost = new ArrayList<Itinerary>();
        Map<Double, Itinerary> hashMapSort = new HashMap<Double, Itinerary>();
        // maps the itineraries against their total cost
        Double[] totalCostArray = new Double[itineraryList.size()];
        for (int i = 0; i < itineraryList.size(); i++) {
            hashMapSort.put(itineraryList.get(i).getTotalCost(), itineraryList.get(i));
            totalCostArray[i] = itineraryList.get(i).getTotalCost();
        }
        Arrays.sort(totalCostArray); // sort in ascending order
        for (Double cost : totalCostArray) {
            sortedByCost.add(hashMapSort.get(cost));
        }
        return sortedByCost;
    }

    public void makeBooking(Itinerary itinerary) {
        this.bookings.add(itinerary);
        ArrayList<Flight> flights = itinerary.getListOfFlights();
        for (Flight flight: flights){
            flight.setNumSeats(flight.getNumSeats() - 1);
        }
        setChanged();
        notifyObservers(this.bookings);
    }

    @Override
    public String toString() {
        String infoAsString = "";
        int n = 0;
        infoAsString += lastName + "," + firstNames + "," + email + "," + address + "," + creditCardNumber + "," + expiryDate;
        return infoAsString;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        setChanged();
        notifyObservers(email);
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        setChanged();
        notifyObservers(lastName);
    }

    public String getFirstNames() {
        return firstNames;
    }

    public void setFirstNames(String firstNames) {
        this.firstNames = firstNames;
        setChanged();
        notifyObservers(firstNames);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
        setChanged();
        notifyObservers();
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
        setChanged();
        notifyObservers(creditCardNumber);
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
        setChanged();
        notifyObservers(expiryDate);
    }

    public ArrayList<Itinerary> getBookings() {
        return bookings;
    }

    public void setBookings(ArrayList<Itinerary> itineraries) {
        this.bookings = itineraries;
        setChanged();
        notifyObservers(this.bookings);
    }

    public boolean equals(Client other) {
        if (this.email == other.getEmail()) {
            return true;
        }
        return false;
    }
}
