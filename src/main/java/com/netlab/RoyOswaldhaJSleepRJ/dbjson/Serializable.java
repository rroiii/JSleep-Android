package com.netlab.RoyOswaldhaJSleepRJ.dbjson;

/**
 * Superclass Account, Room, Renter, Invoice
 * Generate ID
 * @author Roy Oswaldha
 */

import java.util.HashMap;
import java.lang.Integer;

public class Serializable implements Comparable<Serializable>{

    public final int id;
    private static HashMap<Class<?>, Integer> mapCounter = new HashMap<Class<?>, Integer>();

    protected Serializable() {
        Integer temporary;

        temporary = mapCounter.get(this.getClass());
        if (temporary == null) {
            temporary = 0;
        } else {
            temporary++;
        }
        mapCounter.put(this.getClass(), temporary);
        this.id = temporary;
    }

    public int compareTo(Serializable other) {
        return Integer.compare(this.id,other.id);
    }

    public boolean equals(Object object) {
        if (object instanceof Serializable) {
            Serializable objectCheck = (Serializable)object;
            if(objectCheck.id == this.id){
                return true;
            }
        } else {
            return false;
        }
        return false;
    }

    public boolean equals(Serializable serializable){
        if(serializable.id == this.id){
            return true;
        }else{
            return false;
        }
    }

    public static <T> Integer getClosingId(Class<T> map) {
        return mapCounter.get(map);
    }

    public static <T> Integer setClosingId(Class<T> map, int id) {
        return mapCounter.put(map, id);
    }
}


