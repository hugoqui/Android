package com.example.hugo.firstapp;


import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class DisplayMessageActivity extends AppCompatActivity{
    private Context mContext;
    private Activity mActivity;

    private CoordinatorLayout mCLayout;
    private Button mButtonDo;
    private String mJSONURLString = "http://eztrip.azurewebsites.net/api/trips/getTripsByDriver/11";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        Intent intent = getIntent();
        String user = intent.getStringExtra(LoginActivity.Registered_UserName);
        setTitle(user);

        mContext = getApplicationContext();
        mActivity = DisplayMessageActivity.this;
        mCLayout = findViewById(R.id.coordinator_layout);
        loadTrips();
    }

    public void setListItems(ArrayList<JSONObject> trips){
        CustomAdapter adapter = new CustomAdapter(mContext, trips);
        final ListView lv = findViewById(R.id.listView);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //CODIGO AQUI
                int item = position;

                TextView itemVal = lv.getChildAt(position).findViewById(R.id.textTitle);

                //Object itemVal = lv.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(), "Position: "+ item +" - Valor: "+ itemVal.getText(), Toast.LENGTH_LONG).show();

            }
        });
    }

    public void loadTrips() {
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                mJSONURLString,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        ArrayList<JSONObject> trips = new ArrayList<>();
                            for(int i=0;i<response.length();i++){
                                try {
                                    JSONObject trip = response.getJSONObject(i);
                                    trips.add(trip);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                        int n = trips.size();
                        if (n>0) {
                            setListItems(trips);
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        // Do something when error occurred
                        Snackbar.make(
                                mCLayout,
                                "Error...",
                                Snackbar.LENGTH_LONG
                        ).show();
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }
}
