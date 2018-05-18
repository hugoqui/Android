package com.example.hugo.firstapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.PublicKey;

public class LoginActivity extends AppCompatActivity {
    public static final String Registered_UserName = "com.example.myfirstapp.MESSAGE";
    public static final String UserId = "";
    TextView errorMessage;
    String url;
    RequestQueue queue;

    private final String CHANNEL_ID = "personal_notifications";
    private final int NOTIFICATION_ID = 001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        errorMessage = findViewById(R.id.errorMessageText);
        queue = Volley.newRequestQueue(this);

        createNotificationChannel();
    }

    /** Called when the user taps the Send button */
    public void loginCheck(View view) {
        EditText name = findViewById(R.id.userNameText);
        EditText pass = findViewById(R.id.passwordText);
        url = "http://eztrip.azurewebsites.net/api/trips/getusername/" + name.getText().toString() + "," + pass.getText().toString();
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Display the first 500 characters of the response string.
                        try {
                            letAccess(response.getString("Name"), response.getString("Id"));
                        } catch (JSONException e) {
                            errorMessage.setText("Error en datos.");
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                errorMessage.setText("Problema en conexión.");
            }
        });

        queue.add(jsonRequest);
    }

    private void letAccess (String msg, String Id){
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        String message = msg;
        intent.putExtra(Registered_UserName, message);
        intent.putExtra(UserId, Id);
        startActivity(intent);
    }

    public void displayNotification(View view){

        Intent intent = new Intent(this, DisplayMessageActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_car_24dp)
                .setContentTitle("My notification")
                .setContentText("Much longer text that cannot fit one line...")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(NOTIFICATION_ID, mBuilder.build());
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "main_channel_name";
            String description = "channel description";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}

