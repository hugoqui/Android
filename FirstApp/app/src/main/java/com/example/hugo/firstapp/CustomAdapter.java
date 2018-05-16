package com.example.hugo.firstapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {
    Context context;
    String titles[];
    String subTitles[];
    LayoutInflater inflter;

    public CustomAdapter(Context applicationContext, String[] _titles, String[] _subTitles) {
        this.context = applicationContext;
        this.titles = _titles;
        this.subTitles = _subTitles;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return titles.length;
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
        title.setText(titles[i]);
        subTitle.setText(subTitles[i]);
        return view;

    }
}
