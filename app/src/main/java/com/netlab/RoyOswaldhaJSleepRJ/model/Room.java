package com.netlab.RoyOswaldhaJSleepRJ.model;

import java.util.ArrayList;
import java.util.Date;

public class Room extends Serializable{
    public String address;
    public int size;
    public String name;
    public ArrayList<Facility> facility = new ArrayList<>();
    public float price;
    public BedType bedType;
    public City city;
    public ArrayList<Date> booked;
    public int accountId;
    public String image;
    public Rating rating;

    /**
     * Get rating of a room
     * @return rating value of room
     */
    public float getRating(){
        if(this.rating.getCount()  == 0){
            return 0;
        }else{
            return this.rating.getTotal()/this.rating.getCount();
        }
    }

    /**
     * Get name of a room
     * @return name of room
     */
    public String getName(){
        return name;
    }

    /**
     * Get image url of a room
     * @return image url of a room
     */
    public String getImageURL(){
        return  image;
    }

    /**
     * Get price of a room in a format
     * @return price of a room
     */
    public String getPrice(){
        String priceStr = String.valueOf(price);
        return "Rp " + priceStr.substring(0, priceStr.length()-2);
    }

    /**
     * Get bed type of a room in string
     * @return bed type of a room
     */
    public String getBedType(){
        String bedType = null;

        if(this.bedType == BedType.DOUBLE){
            bedType  = "Double";
        }
        if(this.bedType == BedType.KING){
            bedType  = "King";
        }
        if(this.bedType == BedType.QUEEN){
            bedType = "Queen";
        }
        if(this.bedType == BedType.SINGLE){
            bedType = "Single";
        }
        return bedType;
    }

    /**
     * Get city of a room in string
     * @return city of a room
     */
    public String getCity(){
        String city = null;

        if(this.city == City.BALI){
            city = "Bali";
        }
        if(this.city == City.BANDUNG){
            city = "Bandung";
        }
        if(this.city == City.SURABAYA){
            city = "Surabaya";
        }
        if(this.city == City.JAKARTA){
            city = "Jakarta";
        }
        if(this.city == City.SEMARANG){
            city = "Semarang";
        }
        if(this.city == City.MEDAN){
            city = "Medan";
        }
        if(this.city == City.DEPOK){
            city = "Depok";
        }
        if(this.city == City.BEKASI){
            city = "Bekasi";
        }
        if(this.city == City.LAMPUNG){
            city = "Lampung";
        }
        return city;
    }
}
