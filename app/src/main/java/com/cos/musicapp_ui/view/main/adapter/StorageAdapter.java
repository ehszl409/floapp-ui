package com.cos.musicapp_ui.view.main.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cos.musicapp_ui.R;
import com.cos.musicapp_ui.SearchResultFragment;
import com.cos.musicapp_ui.StorageListFragment;
import com.cos.musicapp_ui.event.Event1;
import com.cos.musicapp_ui.model.StorageRepository;
import com.cos.musicapp_ui.model.dto.Storage;
import com.cos.musicapp_ui.utils.eventbus.GlobalBus;
import com.cos.musicapp_ui.view.login.LoginActivity;
import com.cos.musicapp_ui.view.main.MainActivity;
import com.cos.musicapp_ui.view.main.frag.FragStorage;
import com.makeramen.roundedimageview.RoundedImageView;



import java.util.ArrayList;
import java.util.List;

public class StorageAdapter extends RecyclerView.Adapter<StorageAdapter.MyViewHolder> {

    private static final String TAG = "StorageAdapter";
    public List<Storage> storageList = new ArrayList<>();
    private StorageRepository storageRepository = new StorageRepository();


    public StorageAdapter() {};

    public StorageAdapter(List<Storage> storageList) {
        this.storageList = storageList;
        notifyDataSetChanged();
    }

    public void removeStorage(int id){
        this.storageList.remove(id);
        notifyDataSetChanged();
    }

    public void addStorage(Storage storage){
        this.storageList.add(storage);
        notifyDataSetChanged();
    }

    public void setStorage(List<Storage> storageList){
        this.storageList = storageList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_storage, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.setItem(storageList.get(position));
    }

    @Override
    public int getItemCount() {
        return storageList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


     private RoundedImageView ivStorageViewArt;
     private TextView tvStorageTitle;
     private TextView tvStorageSongCount;
     private ImageView ivStorageDelete;
     private ImageView ivStoragePlay;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            ivStorageViewArt = itemView.findViewById(R.id.iv_storage_view_art);
            tvStorageTitle = itemView.findViewById(R.id.tv_storage_title);
            tvStorageSongCount = itemView.findViewById(R.id.tv_storage_song_count);
            ivStoragePlay = itemView.findViewById(R.id.iv_song_play);
            ivStorageDelete = itemView.findViewById(R.id.iv_storage_delete);

            ivStorageViewArt.setOnClickListener(v -> {
                // 리스트 아이템 ID 찾기
                int itemPos = getAdapterPosition();
                Log.d(TAG, "itemPos : " + itemPos);
                Storage storage = storageList.get(itemPos);
                Log.d(TAG, "storage : " + storage);
                int itemId = storage.getId();

                GlobalBus.getInstance().post(new Event1(storage));
                ((MainActivity)v.getContext()).replace(StorageListFragment.newInstance());

            });



            ivStorageDelete.setOnClickListener(v -> {
                // 리스트 아이템 ID 찾기
                int itemPos = getAdapterPosition();
                Log.d(TAG, "itemPos : " + itemPos);
                Storage storage = storageList.get(itemPos);
                Log.d(TAG, "storage : " + storage);
                int itemId = storage.getId();

                storageRepository.deleteStorage(itemId);
                removeStorage(itemPos);
            });


        }

        public void setItem(Storage storage){
            tvStorageTitle.setText(storage.getTitle());
          //  tvStorageSongCount.setText(storage.getSongCount() + "곡");
        }


    }



}
