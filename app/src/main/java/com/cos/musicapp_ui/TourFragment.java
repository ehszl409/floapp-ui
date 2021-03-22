package com.cos.musicapp_ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class TourFragment extends Fragment {

    private ViewPager vpFloChart;
    private TabLayout tabs;
    private MyFragmentPagerAdapter myFragmentPagerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tourpage_fragment, container, false);

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


        return view;
    }
}
