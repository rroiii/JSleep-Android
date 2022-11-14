package com.netlab.RoyOswaldhaJSleepRJ;

import com.netlab.RoyOswaldhaJSleepRJ.dbjson.Serializable;
import java.util.regex.*;

/**
 * Subclass Serailizable
 * @author Roy Oswaldha
 * @version Modul 3 - 27 September 2022
 */
public class Renter extends Serializable {
    public String phoneNumber;
    public String address;
    public String username;
    public static final String REGEX_NAME = "^[A-Z][a-zA-Z0-9_]{4,20}$";
    public static final String REGEX_PHONE = "^[0-9]{9,12}$";

    /**
     * Initialize renter information
     * @param username username of the renter
     * @param phoneNumber phonenumber of the renter
     * @param address address of the renter
     */
    public Renter(String username, String phoneNumber, String address){
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    /**
     * Validate pattern of username and phone number
     * @return true if username and phone number match the pattern
     */
    public boolean validate(){
        Pattern patternUsername = Pattern.compile(REGEX_NAME);
        Matcher matcherUsername = patternUsername.matcher(username);
        Pattern patternPhoneNumber = Pattern.compile(REGEX_PHONE);
        Matcher matcherPhoneNumber = patternPhoneNumber.matcher(phoneNumber);
        return matcherUsername.find() && matcherPhoneNumber.find();
    }
}
