package com.netlab.RoyOswaldhaJSleepRJ.model;

import java.util.ArrayList;
import java.util.List;

public class Account extends Serializable{
   public String name;
   public String password;
   public Renter renter;
   public String email;
   public double balance;
   public ArrayList<Integer> favoriteRoom = new ArrayList<>();

   /**
    * Print account data
    * @return account data
    */
   @Override
   public String toString(){
      return "Account{ +" +
              "balance = " + balance +
              ", email =' " + email + '\'' +
              ", name =' " + name + '\'' +
              ", password = '" + password + '\'' +
              ", renter = " + renter +
              '}';
   }

   /**
    * Get balance of an account
    * @return account's balance in a format
    */
   public String getBalance(){
      String balanceStr = String.valueOf(balance);
      return "Rp " + balanceStr.substring(0, balanceStr.length()-2);
   }
}

