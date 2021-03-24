package com.cos.musicapp_ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Tour_Flochart_Frag1 extends Fragment {

    private static final String TAG = "Tour_Flochart_Frag1";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tourpage_flochart1, container, false);

        Bundle bundle = getArguments();
       ;


        if(bundle == null){
            Log.d(TAG, "번들이 null입니다.");
        } else {
            Log.d(TAG, "Bundle : " + bundle);

        }
        return view;
    }
}
