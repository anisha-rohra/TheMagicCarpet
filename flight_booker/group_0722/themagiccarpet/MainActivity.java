package flight_booker.group_0722.themagiccarpet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("MainActivity", "Main Activity is created");

    }

    public void searchFlights(View view) {
        Intent intent = new Intent(this, FlightSearchEntry.class);
        startActivity(intent);
    }
}
