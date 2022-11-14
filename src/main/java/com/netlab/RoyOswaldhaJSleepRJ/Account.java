package com.netlab.RoyOswaldhaJSleepRJ;

/**
 * Account class contains informations about an account
 * Subclass of Serializable
 * @author Roy Oswaldha
 */

import com.netlab.RoyOswaldhaJSleepRJ.dbjson.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Account extends Serializable
{
    public String name;
    public String email;
    public String password;
    public double balance;
    public Renter renter;
    public static final String REGEX_EMAIL = "^[A-Za-z0-9]+@[A-Za-z]+\\.[A-Za-z.]+[^.]$";
    public static final String REGEX_PASSWORD =  "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$";

    /**
     *
     * @param name username of the account
     * @param email email of the account
     * @param password password of the account
     */
    public Account(String name, String email, String password){
        this.name       = name;
        this.email      = email;
        this.password   = password;
    }

    /**
     * Validate pattern of password and email
     * @return true if password and email match the pattern
     */
    public boolean validate(){
        Pattern patternEmail = Pattern.compile(REGEX_EMAIL);
        Matcher matcherEmail = patternEmail.matcher(email);
        Pattern patternPassword = Pattern.compile(REGEX_PASSWORD);
        Matcher matcherPassword = patternPassword.matcher(password);
        return matcherEmail.find() && matcherPassword.find();
    }

    /**
     * Showing account information
     * @return username, email, and passwword of the account
     */
    public String toString(){
        return "Name : "+ name + "\nEmail : " + email + "\nPassword : " + password;
    }
}
