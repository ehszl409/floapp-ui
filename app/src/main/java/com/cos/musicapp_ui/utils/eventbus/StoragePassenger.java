package com.cos.musicapp_ui.utils.eventbus;

import com.cos.musicapp_ui.model.dto.Storage;

public class StoragePassenger {

    private Storage storage;

    public StoragePassenger(Storage storage) {
        this.storage = storage;
    }

    public Storage getStorage() {
        return storage;
    }
}
