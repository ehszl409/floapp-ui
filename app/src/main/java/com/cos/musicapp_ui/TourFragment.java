package com.cos.musicapp_ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class TourFragment extends Fragment {

    private ViewPager vpFloChart;
    private TabLayout tabs;
    private MyFragmentPagerAdapter myFragmentPagerAdapter;
    private ImageView ivPlayList;
    private ConstraintLayout layoutPlayerBtnArea;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tourpage, container, false);

        vpFloChart = view.findViewById(R.id.vp_floChart);
        tabs = view.findViewById(R.id.floChart_tabs);

        myFragmentPagerAdapter = new MyFragmentPagerAdapter(getChildFragmentManager(), 1);

        myFragmentPagerAdapter.addFragment(new Tour_Flochart_Frag1());
        myFragmentPagerAdapter.addFragment(new Tour_Flochart_Frag2());
        myFragmentPagerAdapter.addFragment(new Tour_Flochart_Frag3());
        myFragmentPagerAdapter.addFragment(new Tour_Flochart_Frag4());

        vpFloChart.setAdapter(myFragmentPagerAdapter);

        tabs.setupWithViewPager(vpFloChart);

        tabs.getTabAt(0);
        tabs.getTabAt(1);
        tabs.getTabAt(2);
        tabs.getTabAt(3);

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
