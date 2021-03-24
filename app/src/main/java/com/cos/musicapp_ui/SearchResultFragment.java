package com.cos.musicapp_ui;

import android.content.Context;
import android.content.Intent;
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
import androidx.fragment.app.FragmentTransaction;

public class SearchResultFragment extends Fragment implements MainActivity.OnBackPressedListener {

    public SearchFragment searchFragment;
    private ImageView ivPlayList;
    private ConstraintLayout layoutPlayerBtnArea;
    private ImageView ivBack;

    // 각각의 Fragment마다 Instance를 반환해 줄 메소드를 생성합니다.
    public static SearchResultFragment newInstance() {
        return new SearchResultFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_result, container, false);


        ivPlayList = view.findViewById(R.id.iv_playlist);

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

        searchFragment = new SearchFragment();

        // 뒤로가기
        ivBack.setOnClickListener(v -> {
            onBack();
        });


        return view;
    }

    @Override
    public void onBack() {
        Log.e("Other", "onBack()");
        // 리스너를 설정하기 위해 Activity 를 받아옵니다.
        MainActivity mainActivity = (MainActivity)getActivity();

        // 한번 뒤로가기 버튼을 눌렀다면 Listener 를 null 로 해제해줍니다
        mainActivity.setOnBackPressedListener(null);

        // searchFragment = 뒤로가기 후 목적지 fragment
        // 변경점 : getFragmentManager(X) => getSupportFragmentManager(최신)
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, searchFragment).commit();
        // Activity 에서도 뭔가 처리하고 싶은 내용이 있다면 하단 문장처럼 호출해주면 됩니다.
        // activity.onBackPressed();
    }

    // Fragment 호출 시 반드시 호출되는 오버라이드 메소드입니다.
    @Override
    //혹시 Context 로 안되시는분은 Activity 로 바꿔보시기 바랍니다.
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e("Other", "onAttach()");
        ((MainActivity) context).setOnBackPressedListener(this);
    }


}


