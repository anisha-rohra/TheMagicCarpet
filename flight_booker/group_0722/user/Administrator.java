package flight_booker.group_0722.user;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Observable;
import java.util.Scanner;

import flight_booker.group_0722.graph.NoSuchCityException;
import flight_booker.group_0722.system.FlightSystem;
import flight_booker.group_0722.travel.Flight;

/**
 * Created by anisharohra on 2015-11-25.
 */
public class Administrator extends Observable implements Serializable {

    private static final long serialVersionUID = -6690283425112323670L;

    private String email;

    private ArrayList<Client> clientList;

    private ArrayList<Flight> flightList;

    private FlightSystem flightSystem;

    public Administrator(String email) {
        this.email = email;
        this.flightSystem = FlightSystem.getInstance();
        this.clientList = flightSystem.getClientList();
        this.flightList = flightSystem.getFlightList();
        addObserver(flightSystem);
    }

    /**
     * Takes a csv file as a parameter and adds every line of information to
     * this.userInformation.
     *
     * @param filePath
     * @throws FileNotFoundException
     */
    public void setClientList(String filePath) throws FileNotFoundException {
        Scanner scan = new Scanner(new File(filePath));
        while (scan.hasNextLine()) { // while there's another line in the file
            String line = scan.nextLine();
            if (line == "") { // if the next line is empty, stop the loop
                break;
            }
            int i = 0; // to keep track of the iterator placement in the line
            int n = 0; // to keep track of information in store
            String email = new String();
            String[] newInformation = new String[5];
            while (i < line.length()) { // checks every character in the line
                String store = "";
                while (i < line.length() && line.charAt(i) != ',') {
                    store += line.charAt(i);
                    i++;
                }
                i++;
                n++;
                switch (n) { // stores area into appropriate place for user
                    case 1:
                        newInformation[0] = store;
                        break;
                    case 2:
                        newInformation[1] = store;
                        break;
                    case 3:
                        email = store;
                        break;
                    case 4:
                        newInformation[2] = store;
                        break;
                    case 5:
                        newInformation[3] = store;
                        break;
                    case 6:
                        newInformation[4] = store;
                        break;
                }
            }
            boolean b = false;
            for (Client c: this.clientList) {
                if (c.getEmail().equals(email)) {
                    b = true;
                    c.setLastName(newInformation[0]);
                    c.setFirstNames(newInformation[1]);
                    c.setAddress(newInformation[2]);
                    c.setCreditCardNumber(newInformation[3]);
                    c.setExpiryDate(newInformation[4]);
                }
            }
            if (!b) {
                this.clientList.add(new Client(email, newInformation[0], newInformation[1], newInformation[2], newInformation[3], newInformation[4]));
            }
        }
        scan.close();
        flightSystem.setClientList(this.clientList); // updates FlightSystem
        setChanged();
        notifyObservers(clientList);
    }

    /**
     * Returns this.userInfomration so that this user can view all of the user's
     * information.
     *
     * @return ArrayList<User>
     */
    public ArrayList<Client> getClientList() {
        return this.clientList;
    }

    /**
     * Takes a csv file as a parameter and creates a Flight from every line of
     * information and adds that flight to the listOfFlights.
     *
     * @param filePath
     * @throws FileNotFoundException
     * @throws NoSuchCityException
     */
    public void setFlightList(String filePath) throws FileNotFoundException, NoSuchCityException, ParseException {
        Scanner scan = new Scanner(new File(filePath));
        while (scan.hasNextLine()) { // while there's another line in the file
            String line = scan.nextLine();
            if (line == "") { // if the line is empty, break from the loop
                break;
            }
            int i = 0; // to keep track of the iterator's placement in a line
            int n = 0; // to keep track of information in store
            String[] flightInformation = new String[8];
            while (i < line.length()) {
                String store = "";
                while (i < line.length() && line.charAt(i) != ',') {
                    store += line.charAt(i);
                    i++;
                }
                i++;
                n++;
                switch (n) { // checks which piece of flight information
                    case 1:
                        flightInformation[0] = store;
                        break;
                    case 2:
                        flightInformation[1] = store;
                        break;
                    case 3:
                        flightInformation[2] = store;
                        break;
                    case 4:
                        flightInformation[3] = store;
                        break;
                    case 5:
                        flightInformation[4] = store;
                        break;
                    case 6:
                        flightInformation[5] = store;
                        break;
                    case 7:
                        flightInformation[6] = store;
                        break;
                    case 8:
                        flightInformation[7] = store;
                        break;
                }
            }
            // convert String to Double
            double costDouble = Double.parseDouble(flightInformation[6]);
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Calendar departureDateTime  = Calendar.getInstance();
            departureDateTime.setTime(df.parse(flightInformation[1]));
            Calendar arrivalDateTime  = Calendar.getInstance();
            arrivalDateTime.setTime(df.parse(flightInformation[2]));
            boolean b = false;
            for (Flight f: this.flightList) {
                if (f.getFlightNumber().equals(flightInformation[0])) {
                    b = true;
                    f.setDepartureDateTime(departureDateTime);
                    f.setArrivalDateTime(arrivalDateTime);
                    f.setAirline(flightInformation[3]);
                    // need to update cityGraph
                    f.setOrigin(flightInformation[4]);
                    f.setDestination(flightInformation[5]);
                    f.setCost(costDouble);
                    f.setNumSeats(Integer.parseInt(flightInformation[7]));

                }
            }
            if (!b) {
                this.flightList.add(new Flight(flightInformation[0], departureDateTime, arrivalDateTime,
                        flightInformation[3], flightInformation[4], flightInformation[5], costDouble, Integer.parseInt(flightInformation[7])));
                flightSystem.getCityGraph().addFlight(new Flight(flightInformation[0], departureDateTime, arrivalDateTime,
                        flightInformation[3], flightInformation[4], flightInformation[5], costDouble, Integer.parseInt(flightInformation[7])));
            }

        }
        scan.close();
        setChanged();
        notifyObservers(flightList);

    }

    public ArrayList<Flight> getFlightList() {
        return this.flightList;
    }

    public Client enterClient(String username) throws NoSuchClientException {
        boolean b = false;
        for (Client c: this.clientList) {
            if (c.getEmail().equals(username)) {
                b = true;
                return c;
            }
        }
        throw new NoSuchClientException();
    }

    public void editFlightInfo(String flightNumber, String departureDateTime, String arrivalDateTime, String airline,
                               String origin, String destination, double cost, int numSeats) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Calendar departureCalendar  = Calendar.getInstance();
        departureCalendar.setTime(df.parse(departureDateTime));
        Calendar arrivalCalendar  = Calendar.getInstance();
        arrivalCalendar.setTime(df.parse(arrivalDateTime));
        for (Flight f: this.flightList) {
            if (f.getFlightNumber().equals(flightNumber)) {
                f.setDepartureDateTime(departureCalendar);
                f.setArrivalDateTime(arrivalCalendar);
                f.setAirline(airline);
                // need to update cityGraph
                f.setOrigin(origin);
                f.setDestination(destination);
                f.setCost(cost);
                f.setNumSeats(numSeats);
                setChanged();
                notifyObservers(flightList);
            }
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
