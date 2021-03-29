package com.cos.musicapp_ui.utils.eventbus;


import com.squareup.otto.Bus;

public class GlobalBus {
    private static final Bus bus = new Bus();

   public static Bus getInstance(){
       return bus;
   }

   private GlobalBus(){}

}
