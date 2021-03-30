package com.cos.musicapp_ui.view.main.adapter;


import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cos.musicapp_ui.DialogActivity;
import com.cos.musicapp_ui.R;
import com.cos.musicapp_ui.model.dto.Song;
import com.cos.musicapp_ui.utils.eventbus.SongIdPassenger;
import com.cos.musicapp_ui.utils.eventbus.SongPassenger;
import com.cos.musicapp_ui.view.common.Constants;
import com.cos.musicapp_ui.view.main.MainActivity;


import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class AllSongAdapter extends RecyclerView.Adapter<AllSongAdapter.MyViewHolder> {

    private static final String TAG = "AllSongAdapter";
    private MainActivity mainActivity;
    public List<Song> songList = new ArrayList<>();
    public int songPosition;


    public AllSongAdapter() { }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }


    public void setMusics(List<Song> songList) {
        this.songList = songList;
        notifyDataSetChanged();
    }


    public String getSongUrl(int position) {
        String songUrl = Constants.BASEURL + Constants.FILEPATH + songList.get(position).getFile();
        return songUrl;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_tour_song, parent, false);


        return new MyViewHolder(view); //view가 리스트뷰에 하나 그려짐.
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        holder.setItem(songList.get(position));

    }

    @Override
    public int getItemCount() {
        return songList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tvSongArtist;
        private TextView tvSongTitle;
        private TextView tvSongId;
        private ImageView ivSongPlay;
        private ImageView ivSongArt;
        private ImageView ivSongStorageAdd;
        private DialogActivity dialogActivity;


        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            tvSongArtist = itemView.findViewById(R.id.tv_song_artist);
            tvSongTitle = itemView.findViewById(R.id.tv_song_title);
            tvSongId = itemView.findViewById(R.id.tv_song_id);
            ivSongPlay = itemView.findViewById(R.id.iv_song_play);
            ivSongArt = itemView.findViewById(R.id.iv_song_art);

            ivSongStorageAdd = itemView.findViewById(R.id.iv_song_storage_add);

            ivSongStorageAdd.setOnClickListener(v -> {
                Log.d(TAG, "보관함 선택 버튼 클릭됨.");
                dialogActivity = new DialogActivity();
                View dialog = v.inflate(v.getContext(), R.layout.item_dialog_storage_list, null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(v.getContext());
                dlg.setView(dialog);

                final AlertDialog alertDialog = dlg.create();
                alertDialog.show();
            });

            ivSongPlay.setOnClickListener(v -> {

                String songUrl = getSongUrl(getAdapterPosition());

                songPosition = getAdapterPosition();
                EventBus.getDefault().post(new SongIdPassenger(songPosition));

                Log.d(TAG, "MyViewHolder: " + songPosition);

//
//                Glide //내가 아무것도 안 했는데 스레드로 동작(편안)
//                        .with(mainActivity)
//                        .load(songList.get(getAdapterPosition()).getImg())
//                        .centerCrop()
//                        .placeholder(R.drawable.ic_launcher_background)
//                        .into(mainActivity.ivMainPlayerViewArt);

                    EventBus.getDefault().post(new SongPassenger(songList.get(getAdapterPosition())));

            });

        }

        public void setItem(Song song) {

            if (song != null) {
                tvSongTitle.setText(song.getTitle());
                tvSongArtist.setText(song.getArtist());
                tvSongId.setText(song.getId().toString());


                Glide //내가 아무것도 안 했는데 스레드로 동작(편안)
                        .with(itemView)
                        .load(song.getImg())
                        .centerCrop()
                        .placeholder(R.drawable.ic_launcher_background)
                        .into(ivSongArt);
            }

        }


    }
}
