package com.cos.musicapp_ui;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.atomic.AtomicInteger;

public class MusicPlayerActivity extends AppCompatActivity {

    private ImageView ivBack;
    private ImageView ivPlayList;
    private Context mContext = MusicPlayerActivity.this;

    private ImageView ivMainPlayerFavorite;


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


        // ********* 좋아요 토글 이벤트 ****************

        ivMainPlayerFavorite = findViewById(R.id.iv_main_player_favorite);
        AtomicInteger isFavorite = new AtomicInteger(); // 1 favorite / 0 not favorite
        isFavorite.set(0);

        ivMainPlayerFavorite.setOnClickListener(v -> {
            if (isFavorite.get() == 1){
                ivMainPlayerFavorite.setImageDrawable(getDrawable(R.drawable.ic_favorite_border));
                isFavorite.set(0);
            } else if (isFavorite.get() == 0){
                ivMainPlayerFavorite.setImageDrawable(getDrawable(R.drawable.ic_favorite));
                isFavorite.set(1);
            }
        });

        // ********* 좋아요 토글 이벤트 ****************


    }
}