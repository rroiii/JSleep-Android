package com.netlab.RoyOswaldhaJSleepRJ;

/**
 * Store room information
 * Subclass of Serializable
 * @author Roy Oswaldha
 */

import com.netlab.RoyOswaldhaJSleepRJ.dbjson.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Room extends Serializable {
    public String address;
    public int size;
    public String name;
    public Facility facility;
    public Price price;
    public BedType bedType;
    public City city;
    public ArrayList<Date> booked;
    public int accountId;

    /**
     * Initialize room to make new room
     * @param name name of the room
     * @param size size of the room
     * @param price price of the room
     * @param facility facility of the room
     * @param city city of the room
     * @param address address of the room
     */
    public Room(int accountId, String name, int size, Price price, Facility facility, City city, String address){
        this.accountId = accountId;
        this.name       = name;
        this.size       = size;
        this.price      = price;
        this. facility  = facility;
        this.city       = city;
        this.address    = address;
        this.booked  = new ArrayList<Date>();
    }

    /**
     * Showing room's information
     * @return room's name, size, facility, city, address, and ID
     */
    public String toString(){
        return "Rome Name : " + name + "\nRoom Size : " + size + "\nRoom " + price + "\nRoom Facility : " + facility + "\nCity : " + city + "\nAddress : " + address + "\nID : " + id +"\n";
    }
}
