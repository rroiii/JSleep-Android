package com.netlab.RoyOswaldhaJSleepRJ;

import com.netlab.RoyOswaldhaJSleepRJ.dbjson.Serializable;

/**
 * Subclass Serailizable
 * @author Roy Oswaldha
 * @version Modul 3 - 27 September 2022
 */

public class Voucher extends Serializable
{
    //Instance variables 
    public Type type;
    public double cut;
    public String name;
    public int code;
    public double minimum;
    private boolean used;


    //Constructor

    /**
     *
     * @param name
     * @param code
     * @param type
     * @param used
     * @param minimum
     * @param cut
     */
    public Voucher(String name, int code, Type type, boolean used, double minimum, double cut){
        this.name = name;
        this.code = code;
        this.type = type;
        this.used = used;
        this.minimum = minimum;
        this.cut = cut;
    }
    /**
     *
     * @param price
     * @return
     */
    public boolean canApply(Price price){
        if((price.price > this.minimum) && (!this.used)){
            return true;
        }else{
            return false;
        }
    }
    
    public boolean isUsed(){
        return this.used;
    }

    /**
     *
     * @param price
     * @return
     */
    public double apply(Price price){
        this.used = true;
        
        if(this.type == Type.DISCOUNT){
            if(this.cut > 100){
                this.cut = 100;
            }
            return price.price-(price.price*(this.cut/100));
        }else if(this.type == Type.REBATE){
            if(this.cut > price.price){
                this.cut = price.price;
            }
            return price.price-this.cut;
        }else{
            return 0;
        }
    }
    
}

