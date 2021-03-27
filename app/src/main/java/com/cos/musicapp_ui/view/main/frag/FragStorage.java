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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cos.musicapp_ui.MusicPlayListActivity;
import com.cos.musicapp_ui.MusicPlayerActivity;
import com.cos.musicapp_ui.ProfileActivity;
import com.cos.musicapp_ui.R;
import com.cos.musicapp_ui.StorageListFragment;
import com.cos.musicapp_ui.model.dto.Storage;
import com.cos.musicapp_ui.view.main.MainActivity;
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

    // 보관함에서 띄우는 다이얼 객체들
    private AppCompatImageView ivStorageAdd;
    private TextView tvDialogCancel, tvDialogComfirm;
    private TextInputEditText tiDialogTitle;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_storagepage, container, false);
        tvUsername = view.findViewById(R.id.tv_username);

        rvStorage = view.findViewById(R.id.rv_storage);

        List<Storage> storages = new ArrayList<>();
        storages.add(new Storage(1, "testStorga", 2, null
        ));

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        rvStorage.setLayoutManager(manager);
        storageAdapter = new StorageAdapter(storages);
        rvStorage.setAdapter(storageAdapter);

        // ************* 보관함 다이얼 띄우기 ******************//
        ivStorageAdd = view.findViewById(R.id.iv_storage_add);
        ivStorageAdd.setOnClickListener(v -> {
            Log.d(TAG, "클릭 이벤트 작동");
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

                storages.add(new Storage(2,title,2,null));
                alertDialog.dismiss();
            });


        });
        // ************* 보관함 다이얼 띄우기 ******************//





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

