package com.cos.musicapp_ui.event;

import com.cos.musicapp_ui.model.dto.Storage;

public class Event1 {
   private Storage storage = new Storage();

   public Event1(Storage storage){
       this.storage = storage;
   }

    public Storage getStorage() {
        return storage;
    }
}