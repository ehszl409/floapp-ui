package com.cos.musicapp_ui.view.main.frag.storage;

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
import androidx.viewpager.widget.ViewPager;

import com.cos.musicapp_ui.MusicPlayListActivity;
import com.cos.musicapp_ui.MusicPlayerActivity;
import com.cos.musicapp_ui.ProfileActivity;
import com.cos.musicapp_ui.R;
import com.cos.musicapp_ui.view.main.adapter.MyFragPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class FragStorage extends Fragment {

    private ViewPager vpStorage;
    private TabLayout tabs;
    private MyFragPagerAdapter myFragPagerAdapter;

    private TextView tvUsername;
    private ImageView ivPlayList;
    private ConstraintLayout layoutPlayerBtnArea;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_storagepage, container, false);

        vpStorage = view.findViewById(R.id.vp_storage);
        tabs = view.findViewById(R.id.tl_storage);
        tvUsername = view.findViewById(R.id.tv_username);

        myFragPagerAdapter = new MyFragPagerAdapter(getChildFragmentManager(), 1);

        myFragPagerAdapter.addFrag(new FragStorageList());
        myFragPagerAdapter.addFrag(new FragFavorite());

        vpStorage.setAdapter(myFragPagerAdapter);

        tabs.setupWithViewPager(vpStorage);

        tabs.getTabAt(0).setText("내 리스트");
        tabs.getTabAt(1).setText("좋아요");

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

        return view;
    }
}
