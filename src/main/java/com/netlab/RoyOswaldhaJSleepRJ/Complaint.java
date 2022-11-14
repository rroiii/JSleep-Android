package com.netlab.RoyOswaldhaJSleepRJ;

/**
 * Stores complaint data
 * Subclass of Serializable
 * @author Roy Oswaldha
 */

import com.netlab.RoyOswaldhaJSleepRJ.dbjson.Serializable;

public class Complaint extends Serializable{

    public String desc;
    public String date;

    /**
     * @param date the date of complaint
     * @param desc description of the complaint
     */

    public Complaint(String date, String desc){
        this.date = date;
        this.desc = desc;
    }

    /**
     * Showing complaint information
     * @return complaint date and desc
     */
    public String toString(){
        return "Date : " + date + "\nDesc : " + desc;
    }
}
