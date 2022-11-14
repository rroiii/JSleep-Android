package com.netlab.RoyOswaldhaJSleepRJ.controller;
import java.util.*;

import com.netlab.RoyOswaldhaJSleepRJ.*;
import com.netlab.RoyOswaldhaJSleepRJ.dbjson.*;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/room")
public class RoomController implements BasicGetController<Room>{
    @JsonAutowired(filepath = "C:\\Users\\Roy\\OneDrive - UNIVERSITAS INDONESIA\\Semester 3\\Pemrograman Berorientasi Objek dan Praktikum\\Praktikum\\JSleep\\src\\main\\java\\com\\netlab\\RoyOswaldhaJSleepRJ\\json\\room.json", value = Room.class)
    public static JsonTable<Room> roomTable;
    @Override
    public JsonTable<Room> getJsonTable(){
        return roomTable;
    }

    @PostMapping("/create")
    public Room  create(@RequestParam int accountId, @RequestParam String name, @RequestParam int size, @RequestParam int price, @RequestParam Facility facility, @RequestParam City city, @RequestParam String address){
        Account accountFound = Algorithm.<Account>find(AccountController.accountTable,prod -> prod.id == accountId);
        if (accountFound != null) {
            if(accountFound.renter != null){
                Room newRoom = new Room(accountId, name,size, new Price(price), facility, city, address);
                roomTable.add(newRoom);
                return newRoom;
            }else{
                return null;
            }
        }else {
            return null;
        }
    }
    @GetMapping("/{id}/renter")
    List<Room> getRoomByRenter(@PathVariable int id, @RequestParam int page, @RequestParam int pageSize){
        try
        {
            List<Room> list = Algorithm.<Room>collect(roomTable, prod -> prod.accountId == id);
            return Algorithm.<Room>paginate(list,page,pageSize, pred -> true);
        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
