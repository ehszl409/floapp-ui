package com.cos.musicapp_ui.view.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.cos.musicapp_ui.StorageListFragment;
import com.cos.musicapp_ui.event.Event1;
import com.cos.musicapp_ui.model.dto.Storage;
import com.cos.musicapp_ui.utils.eventbus.GlobalBus;
import com.cos.musicapp_ui.utils.eventbus.SongIdPassenger;
import com.cos.musicapp_ui.utils.eventbus.SongPassenger;
import com.cos.musicapp_ui.utils.eventbus.StoragePassenger;
import com.cos.musicapp_ui.utils.eventbus.UrlPassenger;
import com.cos.musicapp_ui.view.common.Constants;
import com.cos.musicapp_ui.view.main.adapter.AllSongAdapter;
import com.cos.musicapp_ui.view.main.adapter.StorageAdapter;
import com.cos.musicapp_ui.view.main.adapter.StorageSelectAdapter;
import com.cos.musicapp_ui.view.main.frag.FragHome;
import com.cos.musicapp_ui.R;
import com.cos.musicapp_ui.SearchFragment;
import com.cos.musicapp_ui.view.main.frag.FragStorage;
import com.cos.musicapp_ui.view.main.frag.FragTour;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.makeramen.roundedimageview.RoundedImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private BottomNavigationView bottomNavigationView;
    private Context mContext = MainActivity.this;
    private static final String TAG = "MainActivity2";


    // tour에 리스트 뿌리기
    // 여기서 쓸지, 프래그먼트에서 쓸지 생각중
    public AllSongAdapter allSongAdapter;
    public MainActivityViewModel mainViewModel;
    public RoundedImageView ivMainPlayerViewArt, ivSongArt;
    public TextView tvSongTitle, tvSongArtist, tvSongId;
    public ImageView ivSongPlay;

    // 음악 재생을 위한 타입.
    public MediaPlayer mp;

    // 미니 플레이어 seekbar
    public SeekBar sbMiniPlayer;

    // 미니 플레이어 플레이 이미지 버튼
    public ImageView ivMiniPlayerPlay;

    // 메인 뮤직 플레이어 seekbar
    public SeekBar sbMainPlayer;

    // 메인 뮤직 플레이어 노래 시간.
    public TextView tvMainPlayerTotalTime;

    // 메인 뮤직 플레이어 플레이 이미지 버튼
    public ImageView ivMainPlayerPlay;

    public Thread uiHandleThread;
    public Handler handler = new Handler();

    // 보관함 리사이클러뷰 어댑터를 메인 액티비에서 띄우줍니다.
    // 목적 : 프래그먼트를 직접 띄우지 않아도 프래그먼트속 리사이클러뷰 데이터를 사용하기 위해서
    public StorageAdapter storageAdapter;
    public StorageSelectAdapter storageSelectAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        // 초기화 함수
        initView();
        dataObserver();
        initData();

        //storageSelectAdapter = new StorageSelectAdapter(storageAdapter.initStorageData());



        bottomNavigationView = findViewById(R.id.bottom_navigation);

        Log.d(TAG, "onCreate: 첫 프레그먼트 화면이 FragHome으로 설정되었습니다.");
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
                    selectedFragment = new FragStorage();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            return true;
        });

        //mainSeekbar -> sbMiniPlayer
        //playViewSeekBar -> sbMainPlayer
        //ivBarPlay -> ivMiniPlayerPlay
        //ivPlayViewBar -> ivMainPlayerPlay

//        ivMiniPlayerPlay.setOnClickListener(this::onClick);
//        ivMainPlayerPlay.setOnClickListener(this::onClick);

        // RuntimeException: 발생.
        // seekBarInit();

    }

    public void initView(){
        Log.d(TAG, "initView: 객체를 생성합니다.");
        //******* tour 음악 리스트 선언 *********
        allSongAdapter = new AllSongAdapter();
        ivMainPlayerViewArt = findViewById(R.id.iv_main_player_view_art);
        ivSongArt = findViewById(R.id.iv_song_art);
        ivSongPlay = findViewById(R.id.iv_song_play);
        tvSongArtist = findViewById(R.id.tv_song_artist);
        tvSongId = findViewById(R.id.tv_song_id);
        tvSongTitle = findViewById(R.id.tv_song_title);
        //******* tour 음악 리스트 선언 *********

        // ******* 음악 재생하기(미구현) *******
        sbMiniPlayer = findViewById(R.id.sb_mini_player);
        sbMainPlayer = findViewById(R.id.sb_main_player);
        ivMiniPlayerPlay = findViewById(R.id.iv_mini_player_play);
        ivMainPlayerPlay = findViewById(R.id.iv_main_player_play);
        tvMainPlayerTotalTime = findViewById(R.id.tv_main_player_total_time);
        // ******* 음악 재생하기 *******

        // ****** 보관함 리스트 ******
        storageAdapter = new StorageAdapter();
        storageSelectAdapter = new StorageSelectAdapter();
        // ****** 보관함 리스트 ******
    }






    //*********** 보관함 데이터 가져오기 **************
    public void initData(){
        mainViewModel.findAllStorage();
    }

    // 뷰 모델 구독
    public void dataObserver(){
        mainViewModel.subStorageData().observe(this, new Observer<List<Storage>>() {
            @Override
            public void onChanged(List<Storage> storages) {
                Log.d(TAG, "onChanged: 뷰 모델에서 변화 감지.");
                storageAdapter.setStorage(storages);
            }
        });
    }
    //*********** 보관함 데이터 가져오기 **************



    // 액비비티가 멈췄을 때에도 이벤트 버스를 해제하지 않으면 로그인이나 플레이어 액티비티에서 메인 액티비티로 올때
    // 버스 등록이 중복되었다는 오류가 발생합니다.
    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    //**********************************************************

    // 뒤로가기 버튼 입력시간이 담길 long 객체
    private long pressedTime = 0;

    @Override
    public void onClick(View v) {

    }

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
























    public void replace(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment).commit();
    }


}