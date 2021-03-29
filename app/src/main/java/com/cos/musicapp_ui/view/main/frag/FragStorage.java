package com.cos.musicapp_ui.view.main.frag;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cos.musicapp_ui.MusicPlayListActivity;
import com.cos.musicapp_ui.MusicPlayerActivity;
import com.cos.musicapp_ui.ProfileActivity;
import com.cos.musicapp_ui.R;
import com.cos.musicapp_ui.StorageListFragment;
import com.cos.musicapp_ui.model.StorageRepository;
import com.cos.musicapp_ui.model.dto.Storage;
import com.cos.musicapp_ui.view.main.MainActivity;
import com.cos.musicapp_ui.view.main.MainActivityViewModel;
import com.cos.musicapp_ui.view.main.adapter.StorageAdapter;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class FragStorage extends Fragment {

    private static final String TAG = "StorageFragment";
    private TextView tvUsername;
    private ImageView ivPlayList;
    private ConstraintLayout layoutPlayerBtnArea;
    private ConstraintLayout layoutStorageBtnArea;

    private RecyclerView rvStorage;
    private StorageAdapter storageAdapter;
    private MainActivityViewModel mainViewModel;

    // 보관함에서 띄우는 다이얼 객체들
    private AppCompatImageView ivStorageAdd;
    private TextView tvDialogCancel, tvDialogComfirm;
    private TextInputEditText tiDialogTitle;

    // 레트로핏
    private StorageRepository storageRepository = new StorageRepository();





    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_storagepage, container, false);

        StorageListFragment storageListFragment = new StorageListFragment();
        FragmentManager fragmentManager;
        fragmentManager.findFragmentById()

        // 리스트를 만들면 프래그먼트가 새로고침을 할 수 있도록 객체를 생성했습니다.
        FragmentTransaction ft = getFragmentManager().beginTransaction();

        tvUsername = view.findViewById(R.id.tv_username);
        rvStorage = view.findViewById(R.id.rv_storage);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        rvStorage.setLayoutManager(manager);
        storageAdapter = new StorageAdapter();
        rvStorage.setAdapter(storageAdapter);

        // ************* 보관함 다이얼 띄우기 ******************//
        ivStorageAdd = view.findViewById(R.id.iv_storage_add);
        ivStorageAdd.setOnClickListener(v -> {
            Log.d(TAG, "클릭 이벤트 작동");
            storageRepository.Test();
            View dialog = v.inflate(v.getContext(), R.layout.item_dialog_storage, null);
            AlertDialog.Builder dlg = new AlertDialog.Builder(v.getContext());
            dlg.setView(dialog);

            final AlertDialog alertDialog = dlg.create();
            alertDialog.show();

            tiDialogTitle = dialog.findViewById(R.id.ti_dialog_title);
            tvDialogCancel = dialog.findViewById(R.id.tv_dialog_cancel);
            tvDialogCancel.setOnClickListener(v1 -> {
                Log.d(TAG, "다이얼 취소 버튼 클릭 됨.");

                alertDialog.dismiss();
            });

            tvDialogComfirm = dialog.findViewById(R.id.tv_dialog_confirm);
            tvDialogComfirm.setOnClickListener(v1 -> {
                Log.d(TAG, "다이얼 확인 버튼 클릭 됨.");
                String title = tiDialogTitle.getText().toString();
                Storage storage = new Storage();
                storage.setTitle(title);

                storageRepository.saveStorage(storage);
                // 어댑터한테 보관함이 추가 된 것을 알려주면
                // 뷰 모델에 알림이 발생해서 리스트를 ..?? 일단 어댑터로 바로 쐈습니다.
                storageAdapter.addStorage(storage);

                // 어댑터에서 UI는 그렸지만 DB에서 Id값이 동기화 되지 않아
                // NPE 발생 그래서 프레그먼트 자동 새로 고침
                ft.detach(this).attach(this).commit();



                alertDialog.dismiss();
            });


        });
        // ************* 보관함 다이얼 띄우기 ******************//

        //*********** 뷰 모델과 보관함 리스트 연결하기 *********//
        MainActivity mainActivity = (MainActivity) container.getContext();

        mainViewModel = mainActivity.mainViewModel;

        initData();
        dataObserver();








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

    // 이벤트 버스 등록

    // 초기 데이터 넣기
    private void initData(){
        mainViewModel.findAllStorage();
    }

    // 뷰 모델 구독
    public void dataObserver(){
        mainViewModel.subStorageData().observe(this, new Observer<List<Storage>>() {
            @Override
            public void onChanged(List<Storage> storages) {
                Log.d(TAG, "onChanged: 뷰 모델에서 변화 감지.");
                storageAdapter.setStorage(storages);
            }
        });
    }

}

