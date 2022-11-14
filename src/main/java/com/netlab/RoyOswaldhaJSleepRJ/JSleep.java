package com.netlab.RoyOswaldhaJSleepRJ;

/**
 * Main program
 * @author Roy Oswaldha
 */

import com.netlab.RoyOswaldhaJSleepRJ.dbjson.JsonDBEngine;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication

public class JSleep {
    /**
     * Filter room by price
     * @param list The data
     * @param minPrice Price lower limit
     * @param maxPrice Price limit
     * @return
     */
    public static List<Room> filterByPrice (List<Room> list, double minPrice, double maxPrice) {
        List<Room> filteredList = new ArrayList<Room>();
        if (maxPrice == 0.0) {
            filteredList = Algorithm.<Room>collect(list, room -> room.price.price >= minPrice);
        } else if (minPrice == 0.0) {
            filteredList = Algorithm.<Room>collect(list, room -> room.price.price <= maxPrice);
        } else {
            filteredList = Algorithm.<Room>collect(list, room -> room.price.price <= maxPrice && room.price.price >= minPrice);
        }
        return filteredList;
    }

    /**
     * Filter room by account id
     * @param list The data
     * @param accountId owned room(s) by that accountId
     * @param page how many page to show the information
     * @param pageSize how many room(s) per page to show
     * @return
     */
    public static List<Room> filterByAccountId (List<Room> list, int accountId, int page, int pageSize){
        List<Room> filteredList = new ArrayList<Room>();
        for(Room check : list) {
            if (check.accountId == accountId) {
                filteredList.add(check);
            }
        }
        return Algorithm.<Room>paginate(filteredList,page,pageSize,pred -> true);
    }

    /**
     *Filter room by city
     * @param list The data
     * @param search City's name
     * @param page how many page to show the information
     * @param pageSize how many room(s) per page to show
     * @return
     */
    public static List<Room> filterByCity (List<Room> list, String search, int page, int pageSize){
        List<Room> filteredList = new ArrayList<Room>();
        for (Room check : list) {
            String convertedToString = String.valueOf(check.city);
            if (convertedToString.toLowerCase().contains(search.toLowerCase())) {
                filteredList.add(check);
            }
        }
        return Algorithm.<Room>paginate(filteredList,page,pageSize,pred -> true);
    }

    /**
     * Main function
     * Running Spring Boot
     */
    public static void main (String[] args){
        JsonDBEngine.Run(JSleep.class);
        SpringApplication.run(JSleep.class, args);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> JsonDBEngine.join()));
    }
}