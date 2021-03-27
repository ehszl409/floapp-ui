package com.cos.musicapp_ui.view.main.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cos.musicapp_ui.R;
import com.cos.musicapp_ui.model.dto.Storage;
import com.makeramen.roundedimageview.RoundedImageView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class StorageAdapter extends RecyclerView.Adapter<StorageAdapter.MyViewHolder> {



    private static final String TAG = "StorageAdapter";
    public List<Storage> storageList = new ArrayList<>();

    public StorageAdapter(List<Storage> storageList) {
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

     private AppCompatImageView ivStorageAdd;
     private RoundedImageView ivStorageViewArt;
     private TextView tvStorageTitle;
     private TextView tvStorageSongCount;
     private ImageView ivStoragePlay;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivStorageAdd = itemView.findViewById(R.id.iv_storage_add);
            ivStorageViewArt = itemView.findViewById(R.id.iv_storage_view_art);
            tvStorageTitle = itemView.findViewById(R.id.tv_storage_title);
            tvStorageSongCount = itemView.findViewById(R.id.tv_storage_song_count);
            ivStoragePlay = itemView.findViewById(R.id.iv_song_play);


        }

        public void setItem(Storage storage){
            tvStorageTitle.setText(storage.getTitle());
            tvStorageSongCount.setText(storage.getSongCount() + "곡");
        }


    }



}
