package com.cos.musicapp_ui.view.main.frag;

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
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.cos.musicapp_ui.MusicPlayListActivity;
import com.cos.musicapp_ui.MusicPlayerActivity;
import com.cos.musicapp_ui.MyFragmentPagerAdapter;
import com.cos.musicapp_ui.R;
import com.cos.musicapp_ui.Tour_Flochart_Frag1;
import com.cos.musicapp_ui.Tour_Flochart_Frag2;
import com.cos.musicapp_ui.Tour_Flochart_Frag3;
import com.cos.musicapp_ui.Tour_Flochart_Frag4;
import com.cos.musicapp_ui.model.dto.Song;
import com.cos.musicapp_ui.view.main.MainActivity;
import com.cos.musicapp_ui.view.main.MainActivityViewModel;
import com.cos.musicapp_ui.view.main.adapter.AllSongAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class FragTour extends Fragment {

    private ViewPager vpFloChart;
    private TabLayout tabs;
    private MyFragmentPagerAdapter myFragmentPagerAdapter;
    private ImageView ivPlayList;
    private ConstraintLayout layoutPlayerBtnArea;


    private RecyclerView rvSongList;
    private AllSongAdapter allSongAdapter;
    private MainActivityViewModel mainViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tourpage, container, false);

//        vpFloChart = view.findViewById(R.id.vp_floChart);
//        tabs = view.findViewById(R.id.floChart_tabs);
//
//        myFragmentPagerAdapter = new MyFragmentPagerAdapter(getChildFragmentManager(), 1);
//
//        myFragmentPagerAdapter.addFragment(new Tour_Flochart_Frag1());
//        myFragmentPagerAdapter.addFragment(new Tour_Flochart_Frag2());
//        myFragmentPagerAdapter.addFragment(new Tour_Flochart_Frag3());
//        myFragmentPagerAdapter.addFragment(new Tour_Flochart_Frag4());
//
//        vpFloChart.setAdapter(myFragmentPagerAdapter);
//
//        tabs.setupWithViewPager(vpFloChart);
//
//        tabs.getTabAt(0);
//        tabs.getTabAt(1);
//        tabs.getTabAt(2);
//        tabs.getTabAt(3);
//
//        ivPlayList = view.findViewById(R.id.iv_playlist);

        // 컨택스트 객체 만들기.
        MainActivity mainActivity = (MainActivity) container.getContext();

        mainViewModel = mainActivity.mainViewModel;

        rvSongList = view.findViewById(R.id.rv_song_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvSongList.setLayoutManager(layoutManager);
        allSongAdapter = mainActivity.allSongAdapter;
        allSongAdapter.setMainActivity((MainActivity)getActivity());
        rvSongList.setAdapter(allSongAdapter);

        dataObserver();
        initData();


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

    //???
    @Override
    public void onStart() {
        super.onStart();
    }


    // 구독 지원 함수
    public void dataObserver(){
        mainViewModel.subscribe().observe(this, new Observer<List<Song>>() {
            @Override
            public void onChanged(List<Song> songs) {
                allSongAdapter.setMusics(songs);
            }
        });
    }

    // 초기 데이터 넣는 함수.
    private void initData() {
        mainViewModel.findAll();
    }
}
