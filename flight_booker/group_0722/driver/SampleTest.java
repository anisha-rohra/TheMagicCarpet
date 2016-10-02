//package flight_booker.group_0722.driver;
//
//import org.junit.Test;
//
//import static org.junit.Assert.*;
//
//public class SampleTest {
//
//    // NOTE: set the PATH variable to the location of the input csv files.
//    public static final String PATH = "src/";
//    public static final String CLIENTS = PATH + "clients.txt";
//    public static final String FLIGHTS1 = PATH + "flights1.txt";
//    public static final String FLIGHTS2 = PATH + "flights2.txt";
//
//    @Test(timeout=500)
//    public void testGetClient() throws Exception {
//
//        Driver.uploadClientInfo(CLIENTS);
//
//        String expected = "Roe,Richard,richard@email.com,21 First Lane Way,9999888877776666,2017-10-01";
//        String found = Driver.getClient("richard@email.com");
//        String msg = "Unexpected (incorrect or incorrectly formatted) client information or string output was returned.";
//
//        assertFalse("getClient(String) didn't find any clients.", found.isEmpty());
//        assertEquals(msg, expected, found);
//    }
//
//    @Test
//    public void testGetFlights() throws Exception {
//
//        Driver.uploadFlightInfo(FLIGHTS1);
//
//        String expected = "490,2016-09-30 22:40,2016-10-01 01:59,Go Airline,New York,Boston,532.00";
//        String found = Driver.getFlights("2016-09-30", "New York", "Boston").trim();
//        String msg = "Unexpected (incorrect or incorrectly formatted) flight information or string output was returned.";
//
//        assertFalse("getFlights(String, String, String) didn't find any flights.", found.isEmpty());
//        assertEquals(msg, expected, found);
//    }
//
//    @Test(timeout=500)
//    public void testGetItineraries() throws Exception {
//
//        Driver.uploadFlightInfo(FLIGHTS2);
//
//        String expected = "490,2016-09-30 22:40,2016-10-01 01:59,Go Airline,London,Rome\n532.99\n03:19\n"
//                +"102,2016-09-30 16:37,2016-09-30 17:22,Go Airline,London,Paris\n"
//                +"249,2016-09-30 19:22,2016-09-30 22:40,Go Airline,Paris,Rome\n580.00\n06:03";
//        String expected2 = "102,2016-09-30 16:37,2016-09-30 17:22,Go Airline,London,Paris\n"
//                +"249,2016-09-30 19:22,2016-09-30 22:40,Go Airline,Paris,Rome\n580.00\n06:03\n"
//                +"490,2016-09-30 22:40,2016-10-01 01:59,Go Airline,London,Rome\n532.99\n03:19";
//        String found = Driver.getItineraries("2016-09-30", "London", "Rome").trim();
//
//        String msg = "Unexpected (incorrect or incorrectly formatted) itinerary information or string output was returned.";
//
//        assertFalse("getIntineraries(String, String, String) didn't find any flights.", found.isEmpty());
//
//        boolean match1 = expected.equals(found);
//        boolean match2 = expected2.equals(found);
//        assertTrue(msg, match1 || match2);
//
//    }
//
//    @Test(timeout=50000000)
//    public void testGetItineraries2() throws Exception {
//
//    	Driver.uploadFlightInfo(FLIGHTS2);
//
//    	String expected3 = "488,2016-09-09 09:09,2016-09-09 13:27,FlightsRUs,Paris,Stockholm\n300.50\n04:18";
//        String found = Driver.getItineraries("2016-09-09", "Paris", "Stockholm").trim();
//
//        String msg = "Unexpected (incorrect or incorrectly formatted) itinerary information or string output was returned.";
//
//        assertFalse("getIntineraries(String, String, String) didn't find any flights.", found.isEmpty());
//
//        boolean match3 = expected3.equals(found);
//        assertTrue(msg, match3);
//    }
//
//    @Test(timeout=50000000)
//    public void testGetItineraries3() throws Exception {
//
//    	Driver.uploadFlightInfo(FLIGHTS2);
//
//    	String expected3 = "249,2016-09-30 19:22,2016-09-30 22:40,Go Airline,Paris,Rome\n290.00\n03:18\n" +
//    						"214,2016-09-30 16:42,2016-09-30 18:39,Go Airline,Paris,Rome\n290.00\n01:57";
//    	String expected4 = "214,2016-09-30 16:42,2016-09-30 18:39,Go Airline,Paris,Rome\n290.00\n01:57\n" +
//    						"249,2016-09-30 19:22,2016-09-30 22:40,Go Airline,Paris,Rome\n290.00\n03:18";
//        String found = Driver.getItineraries("2016-09-30", "Paris", "Rome").trim();
//
//        String msg = "Unexpected (incorrect or incorrectly formatted) itinerary information or string output was returned.";
//
//        System.out.println(found);
//
//        assertFalse("getIntineraries(String, String, String) didn't find any flights.", found.isEmpty());
//
//        boolean match3 = expected3.equals(found);
//        boolean match4 = expected4.equals(found);
//        assertTrue(msg, match3 || match4);
//    }
//
//    @Test(timeout=50000000)
//    public void testGetItineraries4() throws Exception {
//
//    	Driver.uploadFlightInfo(FLIGHTS2);
//        String found = Driver.getItineraries("2016-09-09", "London", "Stockholm").trim();
//
//        String msg = "found wasn't empty!";
//
//        assertTrue(msg, found.isEmpty());
//    }
//
//    @Test(timeout=50000000)
//    public void testGetItineraries5() throws Exception {
//
//    	Driver.uploadFlightInfo(FLIGHTS1);
//    	String found = Driver.getItineraries("2016-09-09", "Chicago", "Los Angelos");
//
//    	String expected = "489,2016-09-09 09:09,2016-09-09 13:27,FlightsRUs,Chicago,Los Angelos\n300.99\n04:18";
//
//    	String msg = "found is empty!";
//
//    	assertFalse(msg, found.isEmpty());
//    	assertEquals(found, expected);
//    }
//
//    @Test(timeout=50000000)
//    public void testGetItineraries6() throws Exception {
//
//    	Driver.uploadFlightInfo(FLIGHTS1);
//    	String found = Driver.getItineraries("2016-09-30", "New York", "Chicago");
//
//    	String expected = "102,2016-09-30 16:43,2016-09-30 17:15,FlightsRUs,New York,Chicago\n290.50\n00:32\n" +
//    						"103,2016-09-30 16:37,2016-09-30 17:22,Go Airline,New York,Chicago\n290.00\n00:45";
//    	String expected2 = "103,2016-09-30 16:37,2016-09-30 17:22,Go Airline,New York,Chicago\n290.00\n00:45\n" +
//    						"102,2016-09-30 16:43,2016-09-30 17:15,FlightsRUs,New York,Chicago\n290.50\n00:32";
//
//    	String msg = "found is empty!";
//
//    	boolean match1 = expected.equals(found);
//        boolean match2 = expected2.equals(found);
//
//    	assertFalse(msg, found.isEmpty());
//    	assertTrue(found, match1 || match2);
//    }
//
//    @Test(timeout=50000000)
//    public void testGetItineraries7() throws Exception {
//
//    	Driver.uploadFlightInfo(FLIGHTS1);
//    	String found = Driver.getItineraries("2016-09-30", "New York", "Boston");
//
//    	String expected = "490,2016-09-30 22:40,2016-10-01 01:59,Go Airline,New York,Boston\n532.00\n03:19";
//
//    	String msg = "found is empty!";
//
//    	assertFalse(msg, found.isEmpty());
//    	assertEquals(found, expected);
//    }
//
//    @Test
//    public void testGetItinerariesSortedByTime() throws Exception {
//
//        Driver.uploadFlightInfo(FLIGHTS2);
//
//        String expected = "490,2016-09-30 22:40,2016-10-01 01:59,Go Airline,London,Rome\n532.99\n03:19\n"
//                +"102,2016-09-30 16:37,2016-09-30 17:22,Go Airline,London,Paris\n"
//                +"249,2016-09-30 19:22,2016-09-30 22:40,Go Airline,Paris,Rome\n580.00\n06:03";
//        String found = Driver.getItinerariesSortedByTime("2016-09-30", "London", "Rome").trim();
//
//        String msg = "These are not sorted by time.";
//
//        assertEquals(msg, expected, found);
//    }
//
//
//    @Test
//    public void testGetItinerariesSortedByCost() throws Exception {
//
//        Driver.uploadFlightInfo(FLIGHTS2);
//
//        String expected = "490,2016-09-30 22:40,2016-10-01 01:59,Go Airline,London,Rome\n532.99\n03:19\n"
//                +"102,2016-09-30 16:37,2016-09-30 17:22,Go Airline,London,Paris\n"
//                +"249,2016-09-30 19:22,2016-09-30 22:40,Go Airline,Paris,Rome\n580.00\n06:03";
//        String found = Driver.getItinerariesSortedByCost("2016-09-30", "London", "Rome").trim();
//
//        String msg = "These are not sorted by cost.";
//
//        assertEquals(msg, expected, found);
//    }
//}
