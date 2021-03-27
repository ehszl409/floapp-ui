package com.cos.musicapp_ui.view.main;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.cos.musicapp_ui.model.SongRepository;
import com.cos.musicapp_ui.model.dto.Song;
import com.cos.musicapp_ui.utils.PlayService;

import java.util.List;

public class MainActivityViewModel extends ViewModel {

    private static final String TAG = "HomeActivityViewModel";

    private SongRepository songRepository;

    private MutableLiveData<List<Song>> mtSongList;
    public MutableLiveData<List<Song>> mtPlayList;

    private MutableLiveData<PlayService.LocalBinder> serviceBinder = new MutableLiveData<>(); //서비스는 걍 귀찮으니 여기서 만들겠음.
    private PlayService playService;


    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "ServiceConnection: connected to service.");
            PlayService.LocalBinder binder = (PlayService.LocalBinder) service;
            serviceBinder.postValue(binder);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "ServiceConnection: disconnected from service.");
            serviceBinder.postValue(null);

        }
    };


    public MainActivityViewModel() {
        songRepository = new SongRepository();
        mtSongList = songRepository.initMtSong();
        mtPlayList = songRepository.initPlaylist();
    }

 //   private SongRepository sr = new SongRepository(mtSongList, mtPlayList);

//    public MainActivityViewModel() {
//        List<Song> musics = new ArrayList<>();
//        List<Song> playList= new ArrayList<>();
//        mtSongList.setValue(musics);
//        mtPlayList.setValue(playList);
//    }

    // 구독을 위한 함수.
    public MutableLiveData<List<Song>> subscribe() {
        return mtSongList;
    }

    public MutableLiveData<List<Song>> PlayListSubscribe() {
        return mtPlayList;
    }

    // 음악 데이터를 모두 가져오는 함수.
    public void findAll() {
        songRepository.fetchAllSong();
    }


    public ServiceConnection getServiceConnection() {
        return connection;
    }

    public LiveData<PlayService.LocalBinder> getBinder() {
        return serviceBinder;
    }


}
