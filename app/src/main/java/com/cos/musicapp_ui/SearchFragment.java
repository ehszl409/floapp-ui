package com.cos.musicapp_ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.cos.musicapp_ui.view.main.MainActivity;

public class SearchFragment extends Fragment {
        private ImageView ivPlayList;
        private ConstraintLayout layoutPlayerBtnArea;
        private TextView tvSoaring;

    // 각각의 Fragment마다 Instance를 반환해 줄 메소드를 생성합니다.
    public static SearchFragment newInstance() {
        return new SearchFragment();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_searchpage, container, false);


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

//        tvSoaring = view.findViewById(R.id.tv_soaring);
//        tvSoaring.setOnClickListener(v -> {
//            ((MainActivity)getActivity()).replace(SearchResultFragment.newInstance());
//        });
        return view;
    }
}
