package com.netlab.RoyOswaldhaJSleepRJ;

/**
 * To store information about price of the room
 * @author Roy Oswaldha
 */

public class Price
{
    public double price;
    public double discount;

    /**
     * Initialize price
     * @param price
     */
    public Price(double price){
        this.price = price;
        this.discount = 0;
    }

    /**
     * Initialize price and discount
     * @param price
     * @param discount
     */
    public Price(double price, double discount){
        this.price = price;
        this.discount = discount;
    }

    /**
     * Showing price information
     * @return room's price and discount
     */
    public String toString(){
        return "Price : " + price + "\nDiscount : " + discount;
    }

}
