package com.netlab.RoyOswaldhaJSleepRJ;

/**
 * Subclass Serailizable
 * @author Roy Oswaldha
 * @version PT Modul 3 - 30 September 2022
 */

import com.netlab.RoyOswaldhaJSleepRJ.dbjson.Serializable;

public class Invoice extends Serializable {

     //Enum Class
    public enum RoomRating
    {
        NONE, BAD, NEUTRAL, GOOD
    }

    public enum PaymentStatus
    {
        FAILED, WAITING, SUCCESS
    }

    //Field
    //Instance Variable

    public PaymentStatus status;
    public RoomRating rating;
    public int buyerId;
    public int renterId;
    
    //Constructor

    /**
     *
     * @param buyerId
     * @param renterId
     */
    protected Invoice(int buyerId, int renterId){
        this.buyerId    = buyerId;
        this.renterId   = renterId;
        this.status = PaymentStatus.WAITING;
        this.rating = RoomRating.NONE;
    }

    /**
     *
     * @param buyer
     * @param renter
     */
    public Invoice(Account buyer, Renter renter){
        this.buyerId = buyer.id;
        this.renterId = renter.id;

        this.status = PaymentStatus.WAITING;
        this.rating = RoomRating.NONE;
    }

    /**
     *
     * @return
     */
    public String print(){
        return "Buyer ID : " + buyerId + "\nRenter ID : " + renterId ;
    }
}


