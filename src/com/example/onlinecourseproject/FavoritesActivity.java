package com.example.onlinecourseproject;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class FavoritesActivity extends Activity {

     ListView favoritesListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        ListView favoritesListView =(ListView) findViewById(R.id.favorites_list);
    }
}
