package com.cos.musicapp_ui.view.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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
import com.cos.musicapp_ui.utils.eventbus.GlobalBus;
import com.cos.musicapp_ui.utils.eventbus.SongIdPassenger;
import com.cos.musicapp_ui.utils.eventbus.SongPassenger;
import com.cos.musicapp_ui.utils.eventbus.StoragePassenger;
import com.cos.musicapp_ui.utils.eventbus.UrlPassenger;
import com.cos.musicapp_ui.view.common.Constants;
import com.cos.musicapp_ui.view.main.adapter.AllSongAdapter;
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

    // StorageListFrag
    public  TextView tvStorageListTitle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 초기화 함수
        initView();

        mainViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);


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
        tvStorageListTitle = findViewById(R.id.tv_storage_list_title);
        // ****** 보관함 리스트 ******
    }

    public void songPlay() {
        sbMiniPlayer.setMax(mp.getDuration());
        sbMainPlayer.setMax(mp.getDuration());

        Log.d(TAG, "songPlay: 노래 시작 합니다.");
        Constants.isPlaying = 1;
        setTotalDuration();
        ivMiniPlayerPlay.setImageResource(android.R.drawable.ic_media_pause);
        ivMainPlayerPlay.setImageResource(android.R.drawable.ic_media_pause);

        mp.start();
        seekBarUiHandle();
    }

    // mainSeekbar -> sbMiniPlayer
    // playViewSeekBar -> sbMainPlayer
    // ivBarPlay -> ivMiniPlayerPlay
    // ivPlayViewBar -> ivMainPlayerPlay
    public void songStop() {
        mp.reset();
        mp.seekTo(0);
        sbMiniPlayer.setProgress(0);
        Constants.threadStatus = true;
        ivMiniPlayerPlay.setImageResource(android.R.drawable.ic_media_play);
        ivMainPlayerPlay.setImageResource(android.R.drawable.ic_media_play);
        Constants.isPlaying = -1;
    }



    public void seekBarInit() {
        sbMiniPlayer.setMax(100000);
        sbMiniPlayer.setProgress(0);
        sbMainPlayer.setProgress(0);
    }


    public void setTotalDuration() {
        Integer totalTime = mp.getDuration();

        int m = totalTime / 60000;
        int s = (totalTime % 60000) / 1000;
        String strTime = String.format("%02d:%02d", m, s);

        tvMainPlayerTotalTime.setText(strTime);
    }

    public void seekBarUiHandle() {

        uiHandleThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (Constants.isPlaying == 1) {

                    handler.post(new Runnable() {// runOnUiThread랑 같음, 대신 이렇게 쓰면 uiHandleThread 쓰레드를 원하는데서 참조가능
                        @Override //UI 변경하는 애만 메인 스레드에게 메시지를 전달
                        public void run() {
                            sbMiniPlayer.setProgress(mp.getCurrentPosition());
                            //((MainActivity) getActivity()).playViewSeekBar.setProgress(mp.getCurrentPosition()); // 여기가 에러나는 부분
                            sbMainPlayer.setProgress(mp.getCurrentPosition());

                            if (mp.getCurrentPosition() >= mp.getDuration()) {
                                songStop();
                            }
                        }

                    });

                    try {
                        Thread.sleep(1000);
                        //Log.d(TAG, "run: 33333333");
                        if (Constants.threadStatus) {
                            //Log.d(TAG, "run: 222222222");
                            uiHandleThread.interrupt(); //그 즉시 스레드 종료시키기 위해(강제종료), sleep을 무조건 걸어야 된다. 스레드가 조금이라도 쉬어야 동작함
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        //Log.d(TAG, "run: adadsasdda");
                    }

                }
            }
        });
    }

    public void onPrepared(String songUrl) throws IOException { //이거 나중에 스레드로

        mp.reset();
        mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() { //하 씨바 미치것네
            @Override
            public void onPrepared(MediaPlayer mp) {
                //EventBus.getDefault().post(new SongEvent(songUrl, mainActivity.isPlaying));
                songPlay();
            }
        });
        mp.setDataSource(songUrl);
        mp.prepareAsync();
    }



    // ?????
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void songPrepare(UrlPassenger urlPassenger) throws IOException {
        seekBarInit();
        Log.d(TAG, "songPrepare: url 구독");

        Constants.isPlaying = Constants.isPlaying * -1;
        Log.d(TAG, "songPlay: Song 시작");
        onPrepared(urlPassenger.songUrl);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void nextSong(SongIdPassenger songIdPassenger) {  // 자꾸 private으로 주네. eventbus는 public method만!!
        Log.d(TAG, "nextSong: " + songIdPassenger.songId);
        Constants.prevNext = songIdPassenger.songId;
    }




     //mainSeekbar -> sbMiniPlayer
     //playViewSeekBar -> sbMainPlayer
     //ivBarPlay -> ivMiniPlayerPlay
     //ivPlayViewBar -> ivMainPlayerPlay
     @Subscribe(threadMode = ThreadMode.MAIN)
        public void playlistAdd(SongPassenger songPassenger){
           // Log.d(TAG, "playlistAdd: 내 재생목록에 song 추가"+songPassenger.song);
            //playListAdapter.addSong(songPassenger.song);
        }

        @Subscribe
        public void storagePassenger(StoragePassenger storagePassenger){
            Log.d(TAG, "storagePassenger: 데이터 = " + storagePassenger.getStorage());
            String storageListTitle = storagePassenger.getStorage().getTitle();
            Log.d(TAG, "storagePassenger: storageTtile = " + storageListTitle);
            Bundle bundle = new Bundle();
            bundle.putString("storageData", storageListTitle);
            StorageListFragment storageListFragment = new StorageListFragment();
            storageListFragment.setArguments(bundle);

        }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick: 클릭 확인됨.");
        switch (v.getId()) {
           /* case R.id.iv_next:
            case R.id.iv_playView_next:
                nextORPrevClick(1);
                break;

            case R.id.iv_playView_prev:
            case R.id.iv_prev:
                nextORPrevClick(-1);
                break;*/

            case R.id.iv_mini_player_play:
            case R.id.iv_main_player_play:
                playBtnListner();
                break;

        }
    }

    public void playBtnListner(){

        if (Constants.isPlaying == 1) {
            Log.d(TAG, "onCreate: 글로벌 버튼 클릭되고 노래멈춤" + Constants.isPlaying);
            //songPause();
        } else {
            Log.d(TAG, "onCreate: 노래시작" + Constants.isPlaying);
            songPlay();
        }
    }


    // ********이벤트를 받을 액티비티나 프래그먼트에 등록********
    @Override
    protected void onStart() {
        Log.d(TAG, "onStart: 이벤트 버스가 실행되었습니다.");
        super.onStart();
        EventBus.getDefault().register(this);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }

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