package com.cos.musicapp_ui.view.main.frag.storage;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cos.musicapp_ui.R;
import com.cos.musicapp_ui.model.StorageRepository;
import com.cos.musicapp_ui.model.dto.Storage;
import com.cos.musicapp_ui.view.main.MainActivity;
import com.cos.musicapp_ui.view.main.MainActivityViewModel;
import com.cos.musicapp_ui.view.main.adapter.StorageAdapter;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class FragStorageList extends Fragment {

    private static final String TAG = "StorageFragment";


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
        View view = inflater.inflate(R.layout.fragment_storage_list, container, false);

        MainActivity mainActivity = (MainActivity) container.getContext();
        mainViewModel = mainActivity.mainViewModel;

        // 리스트를 만들면 프래그먼트가 새로고침을 할 수 있도록 객체를 생성했습니다.
        FragmentTransaction ft = getFragmentManager().beginTransaction();


        rvStorage = view.findViewById(R.id.rv_storage);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        rvStorage.setLayoutManager(manager);
        storageAdapter = mainActivity.storageAdapter;
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
                Storage storage = new Storage();
                storage.setTitle(title);

                storageRepository.saveStorage(storage);
                // 어댑터한테 보관함이 추가 된 것을 알려주면
                // 뷰 모델에 알림이 발생해서 리스트를 ..?? 일단 어댑터로 바로 쐈습니다.
                storageAdapter.addStorage(storage);

                // 어댑터에서 UI는 그렸지만 DB에서 Id값이 동기화 되지 않아
                // NPE 발생
                // initData()와 dataObserver()를 통해
                // 다시 전체 찾기를 해주면 해결됩니다.
                dataObserver();
                initData();


                alertDialog.dismiss();
            });


        });




        return view;
    }

    //*********** 보관함 데이터 가져오기 **************
    public void initData(){
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
    //*********** 보관함 데이터 가져오기 **************




}

