package com.example.abhijeetmule.playstorelikeview;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class ViewerPager extends AppCompatActivity {

    ViewPager viewPager;
    MyCustomPagerAdapter myCustomPagerAdapter;
    String strResult;
    String[] imagenameArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewer_pager);

        viewPager = (ViewPager)findViewById(R.id.viewPager);

        SharedPreferences preferences = getApplicationContext().getSharedPreferences("gallery", Context.MODE_PRIVATE);
        strResult = preferences.getString("imagenameArray", "empty");



/*
        Intent intent = getIntent();
        Bundle bd = intent.getExtras();

        String items = (String) bd.get("imageNames");
        Log.d("items",items);
*/
        Intent intent = getIntent();
        String data=  intent.getStringExtra("data");
        Log.d("dataviewer", data);

        imagenameArray = data.split(",");

        myCustomPagerAdapter = new MyCustomPagerAdapter(ViewerPager.this, imagenameArray);
        viewPager.setAdapter(myCustomPagerAdapter);


    }
}
