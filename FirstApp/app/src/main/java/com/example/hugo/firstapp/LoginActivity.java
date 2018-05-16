package com.example.hugo.firstapp;

import android.content.Intent;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    public static final String  Registered_UserName = "com.example.myfirstapp.MESSAGE";
    TextView errorMessage;
    String url ="http://eztrip.azurewebsites.net/api/trips/getusername/";
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
        // Do something in response to button

        EditText name = findViewById(R.id.userNameText);
        EditText pass = findViewById(R.id.passwordText);
        url = url + name.getText().toString() + "," + pass.getText().toString();

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Display the first 500 characters of the response string.
                        try {
                            letAccess(response.getString("Name"));
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

    private void letAccess (String msg){
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        String message = msg;
        intent.putExtra(Registered_UserName, message);
        startActivity(intent);
    }

}

