package com.cos.musicapp_ui;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.makeramen.roundedimageview.RoundedImageView;

public class MusicPlayListActivity extends AppCompatActivity {

    private ImageView ivBack;
    private RoundedImageView rivMusic;
    private Context mContext = MusicPlayListActivity.this;
    private static final String TAG = "MusicPlayListActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_playlist);


        ivBack = findViewById(R.id.iv_back);

        ivBack.setOnClickListener(v -> {
            onBackPressed();
        });

        rivMusic = findViewById(R.id.iv_view_art);

        rivMusic.setOnClickListener(v -> {
            Log.d(TAG, "riv_song 클릭됬습니다.");
            Intent intent = new Intent(mContext, MusicPlayerActivity.class);
            startActivity(intent);
        });



    }
}