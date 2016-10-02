package flight_booker.group_0722.themagiccarpet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.io.File;

// the next two are for demonstration purposes
import java.io.PrintWriter;

import flight_booker.group_0722.graph.NoSuchCityException;
import flight_booker.group_0722.travel.Flight;
import flight_booker.group_0722.system.FlightSystem;
import flight_booker.group_0722.user.Administrator;
import flight_booker.group_0722.user.Client;

public class ShowFlightResults extends AppCompatActivity {

    ListView listView ;
    Client client = new Client("demoClient@gmail.com");
    ArrayList<Flight> flightResults = new ArrayList<Flight>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_flight_results);

        Log.i("ShowFlightResults",  "1. ShowFlightResults is reached.");


        listView = (ListView) findViewById(R.id.lv);
//        Bundle bundle = this.getIntent().getExtras();

        Intent intent = getIntent();

        // Use the key to extract the StudentManager object that was put into
        // this Intent.
        String origin = (String) intent.getSerializableExtra(FlightSearchEntry.ORIGIN);
        String destination = (String) intent.getSerializableExtra(FlightSearchEntry.DESTINATION);
        String departureDate = (String) intent.getSerializableExtra(FlightSearchEntry.DEPARTURE_DATE);
//
        Administrator admin = new Administrator("anisharohra@hotmail.com");

        File userdata = this.getApplicationContext().getDir("userdata", MODE_PRIVATE);
        File flightFile = new File(userdata, "flights.txt");

        Log.i("ShowFlightResults", "2. " + flightFile.getPath());


        try {
            PrintWriter writer = new PrintWriter(flightFile.getPath());
            writer.print("489,2016-09-09 09:09,2016-09-09 13:27,FlightsRUs,Chicago,Los Angelos,300.99,23\n" +
                    "490,2016-09-30 22:40,2016-10-01 01:59,Go Airline,New York,Boston,532.00,24\n" +
                    "102,2016-09-30 16:43,2016-09-30 17:15,FlightsRUs,New York,Chicago,290.50,25\n" +
                    "103,2016-09-30 16:37,2016-09-30 17:22,Go Airline,New York,Chicago,290.00,26\n");
            writer.close();
        } catch (IOException e) {
            Log.i("ShowFlightResults", "3. not writing to file properly");
        }

//        try {
//            Log.i("ShowFlightResults", "4. " + new Scanner(new File(flightFile.getPath())).useDelimiter("\\Z").next());
//        } catch (FileNotFoundException e) {
//            Log.i("ShowFlightResults", "5. file not found when trying to print the entire file");
//
//        }


//
//        try {
//            admin.setFlightList(userdata);
//        } catch (FileNotFoundException e) {
//
//        } catch (NoSuchCityException f) {
//
//        } catch (ParseException p) {
//
//        }

        try {
            admin.setFlightList(flightFile.getPath());
        } catch (NoSuchCityException|FileNotFoundException|ParseException e) {
            Log.i("ShowFlightResults", e.toString() + "6. not setting to flight list properly");
        }

//        flightResults = admin.getFlightList();

        Log.i("ShowFlightResults", "7. " + admin.getFlightList().toString());



//        Set<String> details = bundle.keySet();
//        Object[] detailsArray = details.toArray();
//        Object origin =  bundle.get((String) detailsArray[0]);
//        Object destination = bundle.get((String)detailsArray[1]);
//        Object departureDate = bundle.get((String) detailsArray[2]);
//
        Log.i("ShowFlightResults", departureDate + " " + origin + " " + destination);
//
//
        flightResults = client.searchFlights(departureDate, origin,
                destination);

        Log.i("ShowFlightResults", "8. " + flightResults.toString());


        Log.i("ShowFlightResults", "Before the Custom Adapter constructor");

        CustomAdapter adapter = new CustomAdapter(this, R.id.lv, flightResults);

        Log.i("ShowFlightResults", "After the Custom Adapter constructor");


        listView.setAdapter(adapter);

        Log.i("ShowFlightResults", "After setAdapter");



    }

}
