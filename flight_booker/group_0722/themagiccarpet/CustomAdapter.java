package flight_booker.group_0722.themagiccarpet;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.view.View;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import android.view.LayoutInflater;
import android.widget.TextView;
import flight_booker.group_0722.travel.Flight;

/**
 * Created by AkankshaShelat on 11/29/2015.
 */
public class CustomAdapter extends ArrayAdapter<Flight> {

    ArrayList<Flight> flightResults = new ArrayList<Flight>();
    public CustomAdapter(Context context, int  textViewResourceId, ArrayList<Flight> flightResults){
        super(context, R.layout.individual_flight, flightResults);
        this.flightResults = flightResults;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        Flight f = flightResults.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate
                    (R.layout.individual_flight, parent, false);
        }


        TextView flightOrigin = (TextView)convertView.findViewById(R.id.originView);
        TextView flightDestination = (TextView)convertView.findViewById(R.id.destinationView);
        TextView flightDepartureDateTime = (TextView)convertView.findViewById(R.id.departureDateTimeView);
        TextView flightArrivalDateTime = (TextView)convertView.findViewById(R.id.arrivalDateTimeView);
        TextView flightCost = (TextView)convertView.findViewById(R.id.costView);
        TextView flightTravelTime = (TextView)convertView.findViewById(R.id.travelTimeView);
        TextView flightFlightNumber = (TextView)convertView.findViewById(R.id.flightNumberView);
        TextView flightAirline = (TextView)convertView.findViewById(R.id.airlineView);

        flightOrigin.setText(f.getOrigin());
        flightDestination.setText(f.getDestination());
        flightAirline.setText(f.getAirline());
        flightFlightNumber.setText(f.getFlightNumber());

        String formattedCost = String.format("%.2f", f.getCost());
        flightCost.setText(formattedCost);
        String formattedTime = String.format("%02d", f.getTravelTime()[0]) +
                ":" + String.format("%02d", f.getTravelTime()[1]);
        flightTravelTime.setText(formattedTime);

        Date arrivalDate = f.getArrivalDateTime().getTime();
        Date departureDate = f.getDepartureDateTime().getTime();
        String pattern = "yyyy-MM-dd HH:mm";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        
        flightDepartureDateTime.setText(format.format(departureDate));
        flightArrivalDateTime.setText(format.format(arrivalDate));


        return convertView;
    }
}
