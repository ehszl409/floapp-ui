package com.cos.musicapp_ui.view.main.frag;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cos.musicapp_ui.R;
import com.cos.musicapp_ui.SearchFragment;
import com.cos.musicapp_ui.view.main.MainActivity;
import com.cos.musicapp_ui.view.main.adapter.StorageAdapter;
import com.cos.musicapp_ui.view.main.adapter.StorageSelectAdapter;

public class FragSelectStorage extends Fragment implements MainActivity.OnBackPressedListener{

    private FragTour fragTour;
    private ImageView ivBack;
    private RecyclerView rvSelectStorage;
    private StorageSelectAdapter storageSelectAdapter;
    private MainActivity mainActivity;
    private StorageAdapter storageAdapter;


    // 각각의 Fragment마다 Instance를 반환해 줄 메소드를 생성합니다.
    public static FragSelectStorage newInstance() {
        return new FragSelectStorage();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_storage, container, false);

        rvSelectStorage = view.findViewById(R.id.rv_select_storage);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        rvSelectStorage.setLayoutManager(manager);
        mainActivity = (MainActivity) container.getContext();
        storageAdapter = mainActivity.storageAdapter;
        rvSelectStorage.setAdapter(storageAdapter);



        ivBack = view.findViewById(R.id.iv_back);

        fragTour = new FragTour();

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
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragTour).commit();
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
