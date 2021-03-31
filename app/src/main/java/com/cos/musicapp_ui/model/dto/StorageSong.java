package com.cos.musicapp_ui.model.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StorageSong {
    private Integer id;
    private Storage storage;
    private Song song;
}
