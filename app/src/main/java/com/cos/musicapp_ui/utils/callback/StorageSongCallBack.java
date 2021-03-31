package com.cos.musicapp_ui.utils.callback;

import com.cos.musicapp_ui.model.dto.StorageSong;

public interface StorageSongCallBack<StorageSong> {
   void onSuccess(StorageSong storageSong);
   void onFailure(Throwable t);
}
