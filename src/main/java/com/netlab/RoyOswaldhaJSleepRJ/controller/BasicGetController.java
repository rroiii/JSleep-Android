package com.netlab.RoyOswaldhaJSleepRJ.controller;

import java.util.List;

import com.netlab.RoyOswaldhaJSleepRJ.Algorithm;
import com.netlab.RoyOswaldhaJSleepRJ.Predicate;
import org.springframework.web.bind.annotation.*;

import com.netlab.RoyOswaldhaJSleepRJ.dbjson.*;

public interface BasicGetController<T extends Serializable>{
    @GetMapping("/page")
    default List<T> getPage (@RequestParam int page, @RequestParam int pageSize){
        getJsonTable();
        return null;
    }

    @GetMapping("/{id}")
    default T getbyId(@PathVariable int id) {
        Predicate<T> predicate = obj -> obj.id == id;
        return Algorithm.find(getJsonTable(), predicate);
    }
    abstract JsonTable<T> getJsonTable();

}
