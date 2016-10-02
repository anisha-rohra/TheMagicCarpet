package flight_booker.group_0722.themagiccarpet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class FlightSearchEntry extends AppCompatActivity {

    public static final String ORIGIN = "origin";
    public static final String DESTINATION = "destination";
    public static final String DEPARTURE_DATE = "departureDate";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flight_search_entry);
    }

    public void getFlightResults(View view) {

        Intent intent = new Intent(this, ShowFlightResults.class);

        EditText originField = (EditText) findViewById(R.id.originField);
        String origin = originField.getText().toString();

        EditText destinationField = (EditText) findViewById(R.id.destinationField);
        String destination = destinationField.getText().toString();

        EditText departureDateField = (EditText) findViewById(R.id.departureDateField);
        String departureDate = departureDateField.getText().toString();

        intent.putExtra(ORIGIN, origin);
        intent.putExtra(DESTINATION, destination);
        intent.putExtra(DEPARTURE_DATE, departureDate);

        startActivity(intent);

    }
}

