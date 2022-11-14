package com.netlab.RoyOswaldhaJSleepRJ;

/**
 * To store information about an payment
 * @author Roy Oswaldha
 */

import java.util.ArrayList;
import java.util.Date;

public class Payment extends Invoice {
    public Date  to;
    public Date from;
    private int roomId;

    /**
     *
     * @param buyer buyer's account
     * @param renter renter's account
     * @param roomId
     * @param from
     * @param to
     */
    public Payment(Account buyer, Renter renter, int roomId, Date from, Date to){
        super(buyer, renter);
        this.from = from;
        this.to = to;
        this.roomId = roomId;
    }
    /**
     * Make new payment
     * Store information about an payment
     * @param buyerId
     * @param renterId
     * @param roomId
     */
    public Payment(int buyerId, int renterId, int roomId, Date from, Date to){
        super(buyerId, renterId);
        this.roomId = roomId;
        
        this.from = from;
        this.to = to;
    }

    /**
     * To check wether the room on that date is available or not
     * @param from Booking start date
     * @param to Booking completion date
     * @param room Room that the buyer wants to book
     * @return true if the room is available
     */
    public static boolean availability(Date from, Date to, Room room){
        ArrayList<Date> booked = new ArrayList<Date>();
        int duration = to.getDate()-from.getDate();

        for(int i=0; i < duration; i++){
            Date tomorrow = new Date(from.getTime() + i*(1000 * 60 * 60 * 24));
            booked.add(tomorrow);
        }
        if(from.after(to)){
            return false;
        }
        if(room.booked.containsAll(booked)){
            return false;
        }
        return true;
    }

    /**
     * To book the room
     * @param from Booking start date
     * @param to Booking completion date
     * @param room Room that the buyer wants to book
     * @return true if the room is available
     */
    public static boolean makeBooking(Date from,Date to,Room room){
        if(Payment.availability(from, to, room)){
            int duration = to.getDate()-from.getDate();
            for(int i=0; i < duration; i++){
                Date tomorrow = new Date(from.getTime() + i*(1000 * 60 * 60 * 24));
                room.booked.add(tomorrow);
            }
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Showing payment information
     * @return payment date and room ID
     */
    public String print(){
        return "To : " + this.to + "\nFrom : " + this.from + "\nRoom ID : " + this.roomId;
    }

    /**
     * To get Room's id
     * @return room's Id
     */
    public int getRoomId(){
        return roomId;
    }
}
