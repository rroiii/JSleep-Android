package com.netlab.RoyOswaldhaJSleepRJ.model;

public class Voucher {
    public Type type;
    public double cut;
    public String name;
    public int code;
    public double minimum;
    private boolean used;

    /**
     * Get name of a voucher
     * @return name of a voucher
     */
    public String getName(){
        return name;
    }

    /**
     * Get cut of a voucher
     * @return value of discount or rebate
     */
    public String getCut(){
        String cutStr = String.valueOf(cut);
        return cutStr;
    }

    /**
     * Get code of a voucher
     * @return code of a voucher
     */
    public String getCode(){
        return String.valueOf(code);
    }

    /**
     * Minimum value of a voucher
     * @return minimum value of a voucher
     */
    public String getMinimum(){
        String minimumStr = String.valueOf(minimum);
        return minimumStr;
    }

    /**
     * Type of voucher
     * @return type of voucher
     */
    public String getType(){
        return type.toString();
    }
}
