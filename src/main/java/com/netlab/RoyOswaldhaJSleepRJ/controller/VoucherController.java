package com.netlab.RoyOswaldhaJSleepRJ.controller;

import java.util.*;

import com.netlab.RoyOswaldhaJSleepRJ.*;
import com.netlab.RoyOswaldhaJSleepRJ.dbjson.*;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/voucher")
public class VoucherController implements BasicGetController<Voucher>{
    @JsonAutowired(filepath = "C:\\Users\\Roy\\OneDrive - UNIVERSITAS INDONESIA\\Semester 3\\Pemrograman Berorientasi Objek dan Praktikum\\Praktikum\\JSleep\\src\\main\\java\\com\\netlab\\RoyOswaldhaJSleepRJ\\voucher.json", value = Voucher.class)
    public static JsonTable<Voucher> voucherTable;

    @Override
    public JsonTable<Voucher> getJsonTable(){ return voucherTable;}

    @GetMapping("/{id}/isUsed")
    boolean isUsed (@PathVariable int id) {
        Voucher voucher = Algorithm.<Voucher>find(voucherTable, prod -> prod.id == id);
        if(voucher != null) {
            return voucher.isUsed();
        }else {
            return false;
        }
    }

    @GetMapping("/{id}/canApply")
    boolean canApply (@PathVariable int id, @RequestParam double price) {
        Voucher voucher = Algorithm.<Voucher>find(voucherTable, prod -> prod.id == id);
        if (voucher != null) {
            return voucher.canApply(new Price(price));
        }else {
            return false;
        }
    }

    @GetMapping("/getAvailable")
    List<Voucher> getAvailable (@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "0") int pageSize){
        List<Voucher> list = Algorithm.<Voucher>collect(voucherTable,prod -> prod.isUsed() == false);
        return Algorithm.<Voucher>paginate(list,page,pageSize,pred -> true);
    }
}
