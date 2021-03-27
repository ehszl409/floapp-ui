package com.cos.musicapp_ui.model;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;


import com.cos.musicapp_ui.model.dto.ResponseDto;
import com.cos.musicapp_ui.model.dto.Song;
import com.cos.musicapp_ui.model.network.SongAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SongRepository {

    private static final String TAG = "SongRepository";
    private MutableLiveData<List<Song>> mtSongList;
    private MutableLiveData<List<Song>> mtPlayList;


//    public SongRepository(MutableLiveData<List<Song>> mtSongList, MutableLiveData<List<Song>> mtPlayList) {
//        this.mtSongList = mtSongList;
//        this.mtPlayList = mtPlayList;
//    }


    public SongRepository() {
        mtSongList = new MutableLiveData<>();
        mtPlayList = new MutableLiveData<>();
    }

    //라이브데이터 넘기기
    public MutableLiveData<List<Song>> initMtSong(){
        return mtSongList;
    }

    public MutableLiveData<List<Song>> initPlaylist(){
        List<Song> playList= new ArrayList<>(); //서버에서 리스트를 받을지(즉, 동기화할지는 나중에 생각하고)
        mtPlayList.setValue(playList); //여기서 리스트
        return mtPlayList;
    }


    // 레트로핏으로 서버에서 데이터를 받아옵니다.
    public void fetchAllSong(){
        Call<ResponseDto<List<Song>>> call = SongAPI.retrofit.create(SongAPI.class).findAll();

        call.enqueue(new Callback<ResponseDto<List<Song>>>() {
            @Override
            public void onResponse(Call<ResponseDto<List<Song>>> call, Response<ResponseDto<List<Song>>> response) {
                Log.d(TAG, "onResponse: 성공");
                ResponseDto<List<Song>> result = response.body();
                mtSongList.setValue(result.getData());
            }

            @Override
            public void onFailure(Call<ResponseDto<List<Song>>> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });
    }



    public void playlistAdd(){


    }




}
