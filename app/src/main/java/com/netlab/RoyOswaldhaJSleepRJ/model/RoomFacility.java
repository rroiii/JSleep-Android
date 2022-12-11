package com.netlab.RoyOswaldhaJSleepRJ.model;

import androidx.annotation.DrawableRes;

/**
 * This class is made to display facilities into room details acitivty in a recycler view
 */
public class RoomFacility {
    public String name;
    public @DrawableRes int icon;

    /**
     * Icon of facility
     * @param name
     * @param icon
     */
    public RoomFacility(String name, @DrawableRes int icon)
    {
        this.name = name;
        this.icon = icon;
    }

    /**
     * Name of facility
     * @return
     */
    public String toString(){
        return "facility " + name;
    }
}
