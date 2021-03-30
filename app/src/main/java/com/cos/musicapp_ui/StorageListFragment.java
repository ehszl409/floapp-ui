package com.cos.musicapp_ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.cos.musicapp_ui.event.Event1;
import com.cos.musicapp_ui.event.OnItemClick;
import com.cos.musicapp_ui.model.dto.Storage;

import com.cos.musicapp_ui.utils.eventbus.GlobalBus;
import com.cos.musicapp_ui.utils.eventbus.StoragePassenger;
import com.cos.musicapp_ui.view.main.MainActivity;
import com.cos.musicapp_ui.view.main.adapter.StorageAdapter;
import com.cos.musicapp_ui.view.main.frag.FragStorage;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public class StorageListFragment extends Fragment implements MainActivity.OnBackPressedListener{

    private static final String TAG = "StorageListFragment";
    private FragStorage storageFragment;
    private ImageView ivPlayList;
    private ConstraintLayout layoutPlayerBtnArea;
    private ImageView ivBack;

    private StorageAdapter storageAdapter;

    private TextView tvStorageListTitle;

    public String storageTitle;



    // 각각의 Fragment마다 Instance를 반환해 줄 메소드를 생성합니다.
    public static StorageListFragment newInstance() {
        return new StorageListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_storage_list, container, false);

        ivPlayList = view.findViewById(R.id.iv_playlist);



        tvStorageListTitle = view.findViewById(R.id.tv_storage_list_title);






        // 재생목록으로 이동 하는 로직
        ivPlayList.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), MusicPlayListActivity.class);
            startActivity(intent);
        });


        layoutPlayerBtnArea = view.findViewById(R.id.layout_player_btn_area);

        // 뮤직 플레이어로 이동 하는 로직
        layoutPlayerBtnArea.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), MusicPlayerActivity.class);
            startActivity(intent);
        });

        ivBack = view.findViewById(R.id.iv_back);

        storageFragment = new FragStorage();

        // 뒤로가기
        ivBack.setOnClickListener(v -> {
            onBack();
        });



        return view;
    } // end of onCreateView





    @Override
    public void onBack() {
        Log.e("Other", "onBack()");
        // 리스너를 설정하기 위해 Activity 를 받아옵니다.
        MainActivity mainActivity = (MainActivity)getActivity();

        // 한번 뒤로가기 버튼을 눌렀다면 Listener 를 null 로 해제해줍니다
        mainActivity.setOnBackPressedListener(null);

        // searchFragment = 뒤로가기 후 목적지 fragment
        // 변경점 : getFragmentManager(X) => getSupportFragmentManager(최신)
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, storageFragment).commit();
        // Activity 에서도 뭔가 처리하고 싶은 내용이 있다면 하단 문장처럼 호출해주면 됩니다.
        // activity.onBackPressed();
    }




  /* /// Fragment 호출 시 반드시 호출되는 오버라이드 메소드입니다. => (현재 테스트에는 문제없음)
    @Override
    //혹시 Context 로 안되시는분은 Activity 로 바꿔보시기 바랍니다.
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e("Other", "onAttach()");
        ((MainActivity) context).setOnBackPressedListener(this);
    }*/



}


