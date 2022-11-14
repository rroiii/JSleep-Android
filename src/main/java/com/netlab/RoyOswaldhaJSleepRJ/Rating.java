package com.netlab.RoyOswaldhaJSleepRJ;

/**
 * To store information about price of the room
 * @author Roy Oswaldha
 */

public class Rating
{
    private long total;
    private long count;

    /**
     *
     */
    public Rating(){
        this.total = 0;
        this.count = 0;
    }

    /**
     *
     * @param rating
     */
    public void insert(int rating){
    this.total = this.total + rating;
    this.count = this.count + 1;
    }

    public long getCount(){
        return this.count;
    }

    public long getTotal(){
        return this.total;
    }

    /**
     *
     * @return
     */
    public double getAverage(){
        if(this.count == 0){
            return 0;
        }else{
            return this.total/this.count;
        }
    }

    /**
     *
     * @return
     */
    public String toString(){
        return "Total : " + total + "\nCount : " + count;
    }
}
