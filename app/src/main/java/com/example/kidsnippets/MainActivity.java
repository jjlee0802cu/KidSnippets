package com.example.kidsnippets;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RelativeLayout;

import com.uber.sdk.android.core.UberSdk;
import com.uber.sdk.android.rides.RideParameters;
import com.uber.sdk.android.rides.RideRequestButton;
import com.uber.sdk.core.auth.Scope;
import com.uber.sdk.rides.client.ServerTokenSession;
import com.uber.sdk.rides.client.SessionConfiguration;
import com.uber.sdk.rides.client.UberRidesApi;
import com.uber.sdk.rides.client.model.PriceEstimatesResponse;
import com.uber.sdk.rides.client.model.RideEstimate;
import com.uber.sdk.rides.client.model.RideRequestParameters;
import com.uber.sdk.rides.client.services.RidesService;

import java.io.IOException;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        SessionConfiguration config = new SessionConfiguration.Builder()
                .setClientId("9Ln6QM-cVdH-1KqUAEJbh7zPOQs_0d_C")
                //.setRedirectUri("<REDIRECT_URI>")
                .setScopes(Arrays.asList(Scope.RIDE_WIDGETS))
                .setEnvironment(SessionConfiguration.Environment.PRODUCTION)
                .build();

        UberSdk.initialize(config);

        RideRequestButton requestButton = new RideRequestButton(MainActivity.this);
        RelativeLayout layout = findViewById(R.id.relative_layout);//new RelativeLayout(this);
        layout.addView(requestButton);

        RideParameters rideParams = new RideParameters.Builder()
                .setProductId("a1111c8c-c720-46c3-8534-2fcdd730040d")
                .setDropoffLocation(
                        37.775304, -122.417522, "Uber HQ", "1455 Market Street, San Francisco")
                .setPickupLocation(37.775340, -122.417522, "Uber HQ", "1455 Market Street, San Francisco")
                .build();
        requestButton.setRideParameters(rideParams);




        ServerTokenSession session = new ServerTokenSession(config);
        RidesService service = UberRidesApi.with(session).build().createService();
        //PriceEstimatesResponse price = (PriceEstimatesResponse) service.getPriceEstimates(new Float(rideParams.getPickupLatitude()), new Float(rideParams.getPickupLongitude()), new Float(rideParams.getDropoffLatitude()), new Float(rideParams.getDropoffLongitude()));
        RideRequestParameters rideRequestParameters = new RideRequestParameters.Builder().setPickupCoordinates(37.77f, -122.41f)
                .setProductId("a1111c8c-c720-46c3-8534-2fcdd730040d")
                .setDropoffCoordinates(37.49f, -122.41f)
                .build();
        /*try{
            RideEstimate rideEstimate = service.estimateRide(rideRequestParameters).execute().body();
            int pickupEstimate = rideEstimate.getPickupEstimate();
            RideEstimate.Trip trip = rideEstimate.getTrip();
            float distEstimate = trip.getDistanceEstimate();
            float durationEstimate = trip.getDurationEstimate();
            RideEstimate.Price price1 = rideEstimate.getPrice();
            String fareId = price1.getFareId();
            System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
            System.out.println(fareId);
            //System.out.println(price.getPrices());
            //hio.setText("Fare: " + fareId);
        } catch (IOException e) {

        }*/



        requestButton.setSession(session);
        requestButton.loadRideInformation();
    }
}
