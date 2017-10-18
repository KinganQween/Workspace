package com.edu.nbl.demo.event;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Administrator on 17-9-22.
 */

public class EventType {
    private String str ;

    public EventType(String str) {
        this.str = str;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMessage(EventType type){

    }
}
