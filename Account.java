package com.netlab.RoyOswaldhaJSleepRJ;

import com.netlab.RoyOswaldhaJSleepRJ.dbjson.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Account extends Serializable
{
    //Field
    public String name;
    public String email;
    public String password;
    public double balance;
    public Renter renter;
    public static final String REGEX_EMAIL = "^[A-Za-z0-9]+@[A-Za-z]+\\.[A-Za-z.]+[^.]$";
    public static final String REGEX_PASSWORD =  "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$";

    /**
     *
     * @param name
     * @param email
     * @param password
     */
    public Account(String name, String email, String password){
        this.name       = name;
        this.email      = email;
        this.password   = password;
    }

    /**
     *
     * @return
     */
    public boolean validate(){
        Pattern patternEmail = Pattern.compile(REGEX_EMAIL);
        Matcher matcherEmail = patternEmail.matcher(email);
        Pattern patternPassword = Pattern.compile(REGEX_PASSWORD);
        Matcher matcherPassword = patternPassword.matcher(password);
        return matcherEmail.find() && matcherPassword.find();
    }

    /**
     *
     * @return
     */
    public String toString(){
        return "Name : "+ name + "\nEmail : " + email + "\nPassword : " + password;
    }
}
