package com.cos.musicapp_ui.utils.eventbus;

import com.cos.musicapp_ui.model.dto.Song;

public class SongPassenger {
    public Song song;

    public SongPassenger(Song song) {
        this.song = song;
    }
}
