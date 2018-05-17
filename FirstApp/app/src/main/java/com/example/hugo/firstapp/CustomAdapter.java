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

        String destination = "";
        String origin = "";

        try {
             origin = trips.get(i).getString("Title");
             destination = trips.get(i).getString("Destination");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        title.setText(destination);
        subTitle.setText(origin);
        return view;

    }
}
