package com.netlab.RoyOswaldhaJSleepRJ.model;

public class Invoice extends Serializable{
    public enum RoomRating
    {
        NONE, BAD, NEUTRAL, GOOD
    }

    public enum PaymentStatus
    {
        FAILED, WAITING, SUCCESS
    }

    public PaymentStatus status;
    public RoomRating rating;
    public int buyerId;
    public int renterId;
}
