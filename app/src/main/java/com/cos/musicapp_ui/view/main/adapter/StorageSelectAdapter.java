package com.cos.musicapp_ui.view.main.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cos.musicapp_ui.R;
import com.cos.musicapp_ui.model.dto.Storage;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

public class StorageSelectAdapter extends RecyclerView.Adapter<StorageSelectAdapter.MyViewHolder> {

    private static final String TAG = "StorageSelectAdapter";
    public List<Storage> storageList = new ArrayList<>();

    public StorageSelectAdapter(List<Storage> storages) {
        this.storageList = storages;
        notifyDataSetChanged();
    };

    public void setStorage(List<Storage> storageList){
        this.storageList = storageList;
        Log.d(TAG, "setStorage: " + this.storageList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_dialog_select_storage_list, parent, false);


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

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private RoundedImageView ivStorageViewArt;
        private TextView tvStorageTitle;
        private TextView tvStorageSongCount;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivStorageViewArt = itemView.findViewById(R.id.iv_storage_view_art);
            tvStorageTitle = itemView.findViewById(R.id.tv_storage_title);
            tvStorageSongCount = itemView.findViewById(R.id.tv_storage_song_count);
        }

        public void setItem(Storage storage){
            tvStorageTitle.setText(storage.getTitle());
            //  tvStorageSongCount.setText(storage.getSongCount() + "곡");
        }
    }
}
