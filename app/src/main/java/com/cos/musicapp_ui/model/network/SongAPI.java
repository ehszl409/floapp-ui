package com.cos.musicapp_ui.model.network;

import com.cos.musicapp_ui.model.dto.ResponseDto;
import com.cos.musicapp_ui.model.dto.Song;
import com.cos.musicapp_ui.model.dto.Storage;
import com.cos.musicapp_ui.view.common.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface SongAPI {

    @GET("api/song")
    Call<ResponseDto<List<Song>>> findAll();

    //put, delete, get, 은 확실히 더 추가해야되고, post는 없어도 됨.

    @GET("api/pop")
    Call<ResponseDto<List<Song>>> findPop();

    @PUT("song")
    Call<ResponseDto<List<Song>>> update();

    // 보관함 연결 테스트
    @GET("storage/test")
    Call<ResponseDto<String>> test();

    // 보관함 전체 데이터 불러오기
    @GET("storage")
    Call<ResponseDto<List<Storage>>> storageFindAll();

    // 보관함 추가하기
    @POST("storage")
    Call<ResponseDto<Storage>> storageSave(@Body Storage storage);

    // 보관함 삭제하기
    @DELETE("storage/{id}")
    Call<Void> storageDelete(@Path("id") Integer id);


    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Constants.BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
