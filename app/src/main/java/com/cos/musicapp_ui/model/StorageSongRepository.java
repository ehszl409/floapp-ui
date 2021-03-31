package com.cos.musicapp_ui.model;

import android.util.Log;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.cos.musicapp_ui.model.dto.ResponseDto;
import com.cos.musicapp_ui.model.dto.StorageSong;
import com.cos.musicapp_ui.model.dto.StorageSongSaveReqDto;
import com.cos.musicapp_ui.model.network.SongAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StorageSongRepository {
    private static final String TAG = "StorageSongRepository";

    private MutableLiveData<List<StorageSong>> mtStorageSongList;

    public StorageSongRepository() {
        mtStorageSongList = new MutableLiveData<>();
    }

    public MutableLiveData<List<StorageSong>> initMtStorageSong(){
        return mtStorageSongList;
    }
    
    public void fetchFindAll(int id){
        Call<ResponseDto<List<StorageSong>>> call = SongAPI.retrofit.create(SongAPI.class).storageSongFindAll(id);
        
        call.enqueue(new Callback<ResponseDto<List<StorageSong>>>() {
            @Override
            public void onResponse(Call<ResponseDto<List<StorageSong>>> call, Response<ResponseDto<List<StorageSong>>> response) {
                Log.d(TAG, "onResponse: 보관함 노래 리스트 받기 성공." + response.body().getData());
                ResponseDto<List<StorageSong>> result = response.body();
                mtStorageSongList.setValue(result.getData());
            }

            @Override
            public void onFailure(Call<ResponseDto<List<StorageSong>>> call, Throwable t) {
                Log.d(TAG, "onFailure: 보관함 노래 리스트 받기 실패.");
            }
        });
    }

    public void saveStorageSong(StorageSongSaveReqDto storageSongSaveReqDto){
        Call<Void> call = SongAPI.retrofit.create(SongAPI.class).storageSongSave(storageSongSaveReqDto);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d(TAG, "onResponse: 보관함에 노래가 저장 되었습니다.");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d(TAG, "onFailure: 보관함에 노래 저장을 실패했습니다.");
            }
        });
    }
}
