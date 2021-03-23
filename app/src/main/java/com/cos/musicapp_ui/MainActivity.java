package com.cos.musicapp_ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private Context mContext = MainActivity.this;
    private static final String TAG = "MainActivity2";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            switch (item.getItemId()) {
                case R.id.bottom_homepage:
                    selectedFragment = new HomeFragment();
                    break;
                case R.id.bottom_tourpage:
                    selectedFragment = new TourFragment();
                    break;
                case R.id.bottom_searchpage:
                    selectedFragment = new SearchFragment();
                    break;
                case R.id.bottom_storagepage:
                    selectedFragment = new StorageFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            return true;
        });

//        Tour_Flochart_Frag1 tour_flochart_frag1 = new Tour_Flochart_Frag1();
//
//
//        Bundle bundle = new Bundle(1);
//        ArrayList<Song> songs = new ArrayList<>();
//        songs.add(new Song(1, "멜로디","애쉬아일랜드"));
//        songs.add(new Song(2, "멜로디1","애쉬아일랜드"));
//        songs.add(new Song(3, "멜로디2","애쉬아일랜드"));
//        songs.add(new Song(4, "멜로디3","애쉬아일랜드"));
//        songs.add(new Song(5, "멜로디4","애쉬아일랜드"));
//        songs.add(new Song(6, "멜로디5","애쉬아일랜드"));
//        songs.add(new Song(7, "멜로디6","애쉬아일랜드"));
//        songs.add(new Song(8, "멜로디7","애쉬아일랜드"));
//        songs.add(new Song(9, "멜로디8","애쉬아일랜드"));
//        songs.add(new Song(10, "멜로디9","애쉬아일랜드"));
//        bundle.putParcelableArrayList("songs", songs);
//        Log.d(TAG, "Bundle : " + bundle);
//
//        tour_flochart_frag1.setArguments(bundle);

        Bundle bundle = new Bundle();
        bundle.putString("editText", "From Activity");

        StorageFragment storageFragment = new StorageFragment();
        storageFragment.setArguments(bundle);





    }
}