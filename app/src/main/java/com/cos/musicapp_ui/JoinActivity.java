package com.cos.musicapp_ui;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class JoinActivity extends AppCompatActivity {

    private ImageView ivBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joinpage);


        ivBack = findViewById(R.id.iv_back);

        ivBack.setOnClickListener(v -> {
            onBackPressed();
        });



    }
}