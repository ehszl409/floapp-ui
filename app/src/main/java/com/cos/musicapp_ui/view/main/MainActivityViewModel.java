package com.cos.musicapp_ui.view.main;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.cos.musicapp_ui.model.SongRepository;
import com.cos.musicapp_ui.model.StorageRepository;
import com.cos.musicapp_ui.model.StorageSongRepository;
import com.cos.musicapp_ui.model.dto.Song;
import com.cos.musicapp_ui.model.dto.Storage;
import com.cos.musicapp_ui.model.dto.StorageSong;
import com.cos.musicapp_ui.utils.PlayService;

import java.util.List;

public class MainActivityViewModel extends ViewModel {

    private static final String TAG = "HomeActivityViewModel";

    private SongRepository songRepository;


    private MutableLiveData<List<Song>> mtSongList;
    public MutableLiveData<List<Song>> mtPlayList;

    // 보관함 관련 코드
    private MutableLiveData<List<Storage>> mtStorageList;
    private StorageRepository storageRepository;

    // 보관함 노래 관련 코드
    private MutableLiveData<List<StorageSong>> mtStorageSongList;
    private StorageSongRepository storageSongRepository;

    private MutableLiveData<PlayService.LocalBinder> serviceBinder = new MutableLiveData<>(); //서비스는 걍 귀찮으니 여기서 만들겠음.
    private PlayService playService;


    // 서비스 관련
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
        storageRepository = new StorageRepository();
        storageSongRepository = new StorageSongRepository();

        mtSongList = songRepository.initMtSong();
        mtPlayList = songRepository.initPlaylist();

        // 보관함 관련 코드
        mtStorageList = storageRepository.initMtStorage();

        // 보관함 노래 관련 코드
        mtStorageSongList = storageSongRepository.initMtStorageSong();
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

    // 보관함 관련 코드
    public MutableLiveData<List<Storage>> subStorageData() {return mtStorageList;}

    // 보관함 노래 관련 코드
    public MutableLiveData<List<StorageSong>> subStorageSongData() {return mtStorageSongList;}

    public MutableLiveData<List<Song>> PlayListSubscribe() {
        return mtPlayList;
    }

    // 음악 데이터를 모두 가져오는 함수.
    public void findAll() {
        songRepository.fetchAllSong();
    }

    // *********** 보관함 관련 코드 **********
    public void findAllStorage() {storageRepository.fetchAllStorage();}

    public ServiceConnection getServiceConnection() {
        return connection;
    }

    public LiveData<PlayService.LocalBinder> getBinder() {
        return serviceBinder;
    }


}
