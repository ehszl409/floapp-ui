package com.cos.musicapp_ui.model;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.cos.musicapp_ui.model.dto.ResponseDto;
import com.cos.musicapp_ui.model.dto.Song;
import com.cos.musicapp_ui.model.dto.Storage;
import com.cos.musicapp_ui.model.network.SongAPI;
import com.cos.musicapp_ui.view.main.adapter.StorageAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StorageRepository {

    private static final String TAG = "StorageRepository";

    // 라이브 데이터 생성.
    private MutableLiveData<List<Storage>> mtStorageList;

    // 객체 생성시 라이브 데이터 메모리 띄워준다.
    public StorageRepository(){
        mtStorageList = new MutableLiveData<>();
    }

    // StorageRepository의 라이브 데이터 연결해주기 위해 함수를 만들어 준다.
    public MutableLiveData<List<Storage>> initMtStorage(){
        return mtStorageList;
    }



    public void fetchAllStorage(){
        Call<ResponseDto<List<Storage>>> call = SongAPI.retrofit.create(SongAPI.class).storageFindAll();

        call.enqueue(new Callback<ResponseDto<List<Storage>>>() {
            @Override
            public void onResponse(Call<ResponseDto<List<Storage>>> call, Response<ResponseDto<List<Storage>>> response) {
                Log.d(TAG, "onResponse: fetchAllStorage 진입 확인 = " + response.body().getData());
                ResponseDto<List<Storage>> result = response.body();
                mtStorageList.setValue(result.getData());
            }

            @Override
            public void onFailure(Call<ResponseDto<List<Storage>>> call, Throwable t) {
                Log.d(TAG, "onFailure: 스토리지 데이터 받아오기 실패 : " + t.getMessage());
            }
        });
    }

    public void saveStorage(Storage storage){
        Call<ResponseDto<Storage>> call = SongAPI.retrofit.create(SongAPI.class).storageSave(storage);

        call.enqueue(new Callback<ResponseDto<Storage>>() {
            @Override
            public void onResponse(Call<ResponseDto<Storage>> call, Response<ResponseDto<Storage>> response) {
                Log.d(TAG, "onResponse: 보관함 리스트 추가하기 성공 : " + response.body().getData());
            }

            @Override
            public void onFailure(Call<ResponseDto<Storage>> call, Throwable t) {
                Log.d(TAG, "onFailure: 보관함 추가하기 실패 = " + t.getMessage());
            }
        });

    }
    
    public void deleteStorage(int id){
        Call<Void> call = SongAPI.retrofit.create(SongAPI.class).storageDelete(id);
        
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d(TAG, "onResponse: 삭제하기 성공");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d(TAG, "onFailure: 삭제하기 실패");
            }
        });
    }
}
