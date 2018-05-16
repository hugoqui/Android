package com.example.hugo.firstapp;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.w3c.dom.Text;

import java.util.ArrayList;

import android.widget.ArrayAdapter;
import android.widget.ListView;


public class DisplayMessageActivity extends AppCompatActivity {

    ListView simpleList;
    String countryList[] = {"India", "China", "australia", "Portugle", "America", "NewZealand"};
    String otherList[] = {"Subtitle", "Otromas", "Abita", "Chubby", "Gugu", "Yupi"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);
        Intent intent = getIntent();
        String message = intent.getStringExtra(LoginActivity.Registered_UserName);
        setTitle(message);
        ShowList();
        // Capture the layout's TextView and set the string as its text
    }

    public void ShowList(){

        simpleList = findViewById(R.id.listView1);
        CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), countryList, otherList);
        simpleList.setAdapter(customAdapter);


    }

}
