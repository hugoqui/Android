package com.example.hugo.firstapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

public class CustomAdapter extends BaseAdapter {
    Context context;
    ArrayList<JSONObject> trips;
    public LayoutInflater inflter;

    public CustomAdapter(Context applicationContext, ArrayList<JSONObject> _trips) {
        this.context = applicationContext;
        this.trips = _trips;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return trips.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.activity_listview, null);
        TextView title = view.findViewById(R.id.textTitle);
        TextView subTitle = view.findViewById(R.id.textDescription);
        TextView idTrip = view.findViewById(R.id.idText);
        TextView tvDate = view.findViewById(R.id.textDate);

        int[] androidColors = view.getResources().getIntArray(R.array.androidcolors);
        int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];
        TextView tvInicial = view.findViewById(R.id.textInicial);
        tvInicial.setBackgroundColor(randomAndroidColor);


        String destination = "";
        String origin = "";
        String id = "";
        String inicial ="";
        String  _date ="";
        try {
             origin = trips.get(i).getString("Title");
             destination = trips.get(i).getString("Destination");
             id = trips.get(i).getString("Id");
             inicial = trips.get(i).getString("ClientId").substring(0,2);
             _date = trips.get(i).getString("PickupDate").substring(0,10);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        tvInicial.setText(inicial);
        title.setText(destination);
        subTitle.setText(origin);
        idTrip.setText(id);
        tvDate.setText(_date);
        return view;

    }
}
