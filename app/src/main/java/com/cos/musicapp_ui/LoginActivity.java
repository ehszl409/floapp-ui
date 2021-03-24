package com.cos.musicapp_ui;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class LoginActivity extends AppCompatActivity {

    private TextView tvJoinForm;
    private TextView tvMainPage;
    private FrameLayout fragmentContainer;
    private Context mContext = LoginActivity.this;
    private HomeFragment homeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);

        tvJoinForm = findViewById(R.id.tv_joinform);
        tvMainPage = findViewById(R.id.tv_mainpage);
        fragmentContainer = findViewById(R.id.fragment_container);
        homeFragment = new HomeFragment();

        tvMainPage.setOnClickListener(v -> {
            replaceFrag(homeFragment);
        });

        tvJoinForm.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, JoinActivity.class);
            startActivity(intent);
        });


    }

    public void replaceFrag(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }
}