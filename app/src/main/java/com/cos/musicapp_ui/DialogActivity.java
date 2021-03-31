package com.cos.musicapp_ui;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cos.musicapp_ui.model.dto.Storage;
import com.cos.musicapp_ui.view.main.MainActivity;
import com.cos.musicapp_ui.view.main.MainActivityViewModel;
import com.cos.musicapp_ui.view.main.adapter.StorageSelectAdapter;

import java.util.ArrayList;
import java.util.List;

public class DialogActivity extends AppCompatActivity {

    private static final String TAG = "dialogActivity";
    private RecyclerView rvStorageSelectList;
    private StorageSelectAdapter storageSelectAdapter;
    private MainActivityViewModel mainActivityViewModel;
    private MainActivity mainActivity;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_dialog_storage_list);
        Log.d(TAG, "onCreate: 다이얼 액비티비 시작.");

        rvStorageSelectList = findViewById(R.id.rv_storage_select_list);



        mContext = DialogActivity.this;
        LinearLayoutManager manager = new LinearLayoutManager(mContext,RecyclerView.VERTICAL,false);

        rvStorageSelectList.setLayoutManager(manager);
        storageSelectAdapter = new StorageSelectAdapter();
        rvStorageSelectList.setAdapter(storageSelectAdapter);

        mainActivity = new MainActivity();
        mainActivityViewModel = mainActivity.mainViewModel;

        initData();
        dataObserver();
    }

    private void initData() {
        Log.d(TAG, "initData: 데이터 초기화 실행.");
        mainActivityViewModel.findAllStorage();
    }

    // 뷰 모델 구독
    public void dataObserver() {
        mainActivityViewModel.subStorageData().observe(this, new Observer<List<Storage>>() {
            @Override
            public void onChanged(List<Storage> storages) {
                Log.d(TAG, "onChanged: 뷰 모델에서 변화 감지.");
                storageSelectAdapter.setStorage(storages);
            }
        });
    }
}