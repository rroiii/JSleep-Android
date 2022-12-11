package com.netlab.RoyOswaldhaJSleepRJ.model;

import java.util.Date;

public class Payment extends Invoice {
    public Date  to;
    public Date from;
    private int roomId;
    public Price price;

    /**
     * Get room id of an payment
     * @return
     */
    public int getRoomId(){
        return this.roomId;
    }
}
