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

public class StorageFragment extends Fragment {

    private static final String TAG = "StorageFragment";
    private TextView tvUsername;
    private ImageView ivPlayList;
    private ConstraintLayout layoutPlayerBtnArea;
    private ConstraintLayout layoutStorageBtnArea;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_storagepage, container, false);
        tvUsername = view.findViewById(R.id.tv_username);

        tvUsername.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ProfileActivity.class);
            startActivity(intent);
        });


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

        layoutStorageBtnArea = view.findViewById(R.id.layout_storage_btn_area);

        layoutStorageBtnArea.setOnClickListener(v -> {
            ((MainActivity)getActivity()).replace(StorageListFragment.newInstance());
        });

        return view;
    }
}

