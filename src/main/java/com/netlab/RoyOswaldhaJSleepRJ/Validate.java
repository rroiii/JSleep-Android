package com.netlab.RoyOswaldhaJSleepRJ;

/**
 *
 */

import java.util.ArrayList;

public class Validate {
    /**
     * @param list list of price
     * @param value value for checking
     * @param less less or not
     * @return array list
     */

    public static ArrayList filter (Price[] list, int value, boolean less){
        ArrayList temporary = new ArrayList();

        if(less){
            for(Price tempList : list){
                if(tempList.price <= value){
                    temporary.add(tempList.price);
                }
            }
        }else{
            for(Price tempList : list){
                if(tempList.price > value){
                    temporary.add(tempList.price);
                }
            }
        }
        return temporary;
    }
}