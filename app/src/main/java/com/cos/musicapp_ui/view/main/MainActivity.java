package com.cos.musicapp_ui.view.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.cos.musicapp_ui.utils.eventbus.UrlPassenger;
import com.cos.musicapp_ui.view.common.Constants;
import com.cos.musicapp_ui.view.main.adapter.AllSongAdapter;
import com.cos.musicapp_ui.view.main.frag.FragHome;
import com.cos.musicapp_ui.R;
import com.cos.musicapp_ui.SearchFragment;
import com.cos.musicapp_ui.StorageFragment;
import com.cos.musicapp_ui.view.main.frag.FragTour;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.makeramen.roundedimageview.RoundedImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private Context mContext = MainActivity.this;
    private static final String TAG = "MainActivity2";



    // tour에 리스트 뿌리기
    // 여기서 쓸지, 프래그먼트에서 쓸지 생각중
    public AllSongAdapter allSongAdapter;
    public MainActivityViewModel mainViewModel;
    public RoundedImageView ivPlayerViewArt, ivSongArt;
    public TextView tvSongTitle, tvSongArtist, tvSongId;
    public ImageView ivSongPlay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 초기화 함수
        initView();

        mainViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);


        bottomNavigationView = findViewById(R.id.bottom_navigation);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragHome()).commit(); //최초 화면(프레그먼트)
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = new FragHome();


            switch (item.getItemId()) {
                case R.id.bottom_homepage:
                    selectedFragment = new FragHome();
                    break;
                case R.id.bottom_tourpage:
                    selectedFragment = new FragTour();
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




    }

    public void initView(){
        //******* tour 음악 리스트 선언 *********
        allSongAdapter = new AllSongAdapter();
        ivPlayerViewArt = findViewById(R.id.iv_player_view_art);
        ivSongArt = findViewById(R.id.iv_song_art);
        ivSongPlay = findViewById(R.id.iv_song_play);
        tvSongArtist = findViewById(R.id.tv_song_artist);
        tvSongId = findViewById(R.id.tv_song_id);
        tvSongTitle = findViewById(R.id.tv_song_title);
        //******* tour 음악 리스트 선언 *********
    }



    @Subscribe(threadMode = ThreadMode.MAIN)
    public void songPrepare(UrlPassenger urlPassenger) throws IOException {
       // seekBarInit();
        Log.d(TAG, "songPrepare: url 구독");

        Constants.isPlaying = Constants.isPlaying * -1;
        Log.d(TAG, "songPlay: Song 시작");
      //  onPrepared(urlPassenger.songUrl);

    }



    // ********이벤트를 받을 액티비티나 프래그먼트에 등록********
    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    //**********************************************************

    // 뒤로가기 버튼 입력시간이 담길 long 객체
    private long pressedTime = 0;

    // 리스너 생성
    public interface OnBackPressedListener {
        public void onBack();
    }

    // 리스너 객체 생성
    private OnBackPressedListener mBackListener;

    // 리스너 설정 메소드
    public void setOnBackPressedListener(OnBackPressedListener listener) {
        mBackListener = listener;
    }

    // 뒤로가기 버튼을 눌렀을 때의 오버라이드 메소드
    @Override
    public void onBackPressed() {

        // 다른 Fragment 에서 리스너를 설정했을 때 처리됩니다.
        if(mBackListener != null) {
            mBackListener.onBack();
            Log.e("!!!", "Listener is not null");
            // 리스너가 설정되지 않은 상태(예를들어 메인Fragment)라면
            // 뒤로가기 버튼을 연속적으로 두번 눌렀을 때 앱이 종료됩니다.
        } else {
            Log.e("!!!", "Listener is null");
            if ( pressedTime == 0 ) {
                Snackbar.make(findViewById(R.id.fragment_container),
                        " 한 번 더 누르면 종료됩니다." , Snackbar.LENGTH_LONG).show();
                pressedTime = System.currentTimeMillis();
            }
            else {
                int seconds = (int) (System.currentTimeMillis() - pressedTime);

                if ( seconds > 2000 ) {
                    Snackbar.make(findViewById(R.id.fragment_container),
                            " 한 번 더 누르면 종료됩니다." , Snackbar.LENGTH_LONG).show();
                    pressedTime = 0 ;
                }
                else {
                    super.onBackPressed();
                    Log.e("!!!", "onBackPressed : finish, killProcess");
                    finish();
                    android.os.Process.killProcess(android.os.Process.myPid());
                }
            }
        }
    }






















    /*//뒤로가기 버튼을 뺏어올 리스너 등록
    public interface onKeyBackPressedListener {
        void onBackKey();
    }

    private onKeyBackPressedListener mOnKeyBackPressedListener;

    public void setOnKeyBackPressedListener(onKeyBackPressedListener listener) {
        mOnKeyBackPressedListener = listener;
    }

    //메인에서 토스트를 띄우며 종료확인을 하기 위해 필드선언
    //EndToast endToast = new EndToast(this);

    @Override
    public void onBackPressed() {
        if (mOnKeyBackPressedListener != null) {
            mOnKeyBackPressedListener.onBackKey();
        } else {
            //쌓인 BackStack 여부에 따라 Toast를 띄울지, 뒤로갈지
            if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                //* 종료 EndToast Bean 사용
               // endToast.showEndToast("종료하려면 한번 더 누르세요.");
            } else {
                super.onBackPressed();
            }
        }
    }*/

    public void replace(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment).commit();
    }


}