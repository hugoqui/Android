package com.example.hugo.firstapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class TripDetailsActivity extends AppCompatActivity {

    public String tripId;

    TextView destination;
    TextView origin;
    TextView pickupDate;
    TextView pickupTime;
    TextView contact;
    TextView phoneNumber;
    TextView passengers;
    TextView price;
    TextView car;
    RequestQueue queue;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_details);

        Intent intent = getIntent();
        tripId = intent.getStringExtra(DisplayMessageActivity.tripId);
        url = "http://eztrip.azurewebsites.net/api/trips/getTrip/" + tripId;
        setTitle(tripId);

        destination = findViewById(R.id.textDestination);
        origin = findViewById(R.id.textOrigin);
        pickupDate = findViewById(R.id.textDate);
        pickupTime = findViewById(R.id.textTime);
        contact = findViewById(R.id.textContact);
        phoneNumber = findViewById(R.id.textPhone);
        passengers = findViewById(R.id.textPassenger);
        price = findViewById(R.id.textPrice);
        car = findViewById(R.id.textCar);

        queue = Volley.newRequestQueue(this);
        showDetails();
    }

    public void showDetails() {
        JsonObjectRequest jsonTripRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            destination.setText(response.getString("Destination"));
                            origin.setText(response.getString("Title"));
                            pickupDate.setText(response.getString("PickupDate"));
                            pickupTime.setText(response.getString("PickupTime"));
                            contact.setText(response.getString("MainPax"));
                            phoneNumber.setText(response.getString("MainPaxPhone"));
                            passengers.setText(response.getString("PaxNumber"));
                            price.setText(response.getString("Price"));
                            car.setText(response.getString("CarPlate"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error en conexión ", Toast.LENGTH_LONG).show();
            }
        });
        queue.add(jsonTripRequest);
    }

    public void setTripCompleted(View view) {
        String completeUrl = "http://eztrip.azurewebsites.net/api/trips/tripCompleted/" + tripId;
        StringRequest tripCompleteRequest = new StringRequest(Request.Method.GET, completeUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.contains("ple")) {
                            GoBack();
                        } else {
                            Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error en conexión en String Request", Toast.LENGTH_LONG).show();
            }
        });
        queue.add(tripCompleteRequest);
    }

    @SuppressLint("MissingPermission")
    public void callPassenger(View view) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        String phoneToDial = phoneNumber.getText().toString().trim();
        phoneToDial = phoneToDial.replace("-","");
        phoneToDial = "tel:" + phoneToDial;
        callIntent.setData(Uri.parse(phoneToDial));
        startActivity(callIntent);

    }

    public void GoBack(){
        this.finish();
    }
}
