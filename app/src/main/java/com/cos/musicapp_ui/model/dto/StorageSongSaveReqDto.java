package com.cos.musicapp_ui.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class StorageSongSaveReqDto {
    private Storage storage;
    private Song song;
}
