package com.cos.musicapp_ui;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MusicPlayerActivity extends AppCompatActivity {

    private ImageView ivBack;
    private ImageView ivPlayList;
    private Context mContext = MusicPlayerActivity.this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);

        ivPlayList = findViewById(R.id.iv_playlist);
        ivBack = findViewById(R.id.iv_back);

        ivBack.setOnClickListener(v -> {
            onBackPressed();
        });

        ivPlayList.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, MusicPlayListActivity.class);
            startActivity(intent);
        });


    }
}