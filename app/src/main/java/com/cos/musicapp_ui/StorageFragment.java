package com.cos.musicapp_ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class StorageFragment extends Fragment {

    private static final String TAG = "StorageFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        String str = getArguments().getString("editText");
        Log.d(TAG, "번들 확인 : " + str);
        View view = inflater.inflate(R.layout.storagepage_fragment, container, false);
        return view;
    }
}

