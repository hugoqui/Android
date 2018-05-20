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
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.PublicKey;

public class LoginActivity extends AppCompatActivity {
    public static final String Registered_UserName = "com.example.myfirstapp.MESSAGE";
    public static final String UserId = "";
    TextView errorMessage;
    String url;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        errorMessage = findViewById(R.id.errorMessageText);
        queue = Volley.newRequestQueue(this);

    }

    /** Called when the user taps the Send button */
    public void loginCheck(View view) {
        EditText name = findViewById(R.id.userNameText);
        EditText pass = findViewById(R.id.passwordText);
        String token = FirebaseInstanceId.getInstance().getToken();
        token = token.replace(":", "!");
        url = "http://eztrip.azurewebsites.net/api/trips/getusername/" + name.getText().toString() + "," + pass.getText().toString() + "," + token;
        //url = "http://localhost:51234/api/trips/getusername/" + name.getText().toString() + "," + pass.getText().toString() + "," + token;

        Log.d("HQ ", "token: " + token);
        Log.d("HQ ", "Mi ULR: " + url);

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
                Log.d("HQ", error.getMessage());
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



}

