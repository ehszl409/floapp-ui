package com.cos.musicapp_ui.view.login;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.widget.ImageView;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.cos.musicapp_ui.R;

public class LoginActivity extends AppCompatActivity {

    private TextView tvJoinForm;

    private Context mContext = LoginActivity.this;
    private ImageView ivBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);

        tvJoinForm = findViewById(R.id.tv_joinform);
        ivBack = findViewById(R.id.iv_back);



        // 뒤로가기
        ivBack.setOnClickListener(v -> {
            onBackPressed();
        });


        // 회원가입 페이지로 가기
        tvJoinForm.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, JoinActivity.class);
            startActivity(intent);
        });


    }
    // 액비티비에서 프래그먼트로 이동하기 함수 (미구현)
    public void replaceFrag(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }
}