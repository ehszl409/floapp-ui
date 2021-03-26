package com.cos.musicapp_ui.model.network;

import com.cos.musicapp_ui.model.dto.ResponseDto;
import com.cos.musicapp_ui.model.dto.Song;
import com.cos.musicapp_ui.view.common.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.PUT;

public interface SongAPI {

    @GET("api/song")
    Call<ResponseDto<List<Song>>> findAll();

    //put, delete, get, 은 확실히 더 추가해야되고, post는 없어도 됨.

    @GET("api/pop")
    Call<ResponseDto<List<Song>>> findPop();

    @PUT("song")
    Call<ResponseDto<List<Song>>> update();

    

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Constants.BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
