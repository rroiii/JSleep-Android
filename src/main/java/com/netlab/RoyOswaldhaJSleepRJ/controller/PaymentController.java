package com.netlab.RoyOswaldhaJSleepRJ.controller;
import com.netlab.RoyOswaldhaJSleepRJ.Room;
import com.netlab.RoyOswaldhaJSleepRJ.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.netlab.RoyOswaldhaJSleepRJ.dbjson.*;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/payment")
public class PaymentController implements BasicGetController<Payment>{
    @JsonAutowired(filepath ="C:\\Users\\Roy\\OneDrive - UNIVERSITAS INDONESIA\\Semester 3\\Pemrograman Berorientasi Objek dan Praktikum\\Praktikum\\JSleep\\src\\main\\java\\com\\netlab\\RoyOswaldhaJSleepRJ\\json\\payment.json", value = Payment.class)
    public static JsonTable<Payment> paymentTable;
    public JsonTable<Payment> getJsonTable(){
        return paymentTable;
    }

    @PostMapping("/create")
    Payment create(@RequestParam int buyerId, @RequestParam int renterId, @RequestParam int roomId, @RequestParam String from, @RequestParam String to) throws ParseException {
        Account accountFound = Algorithm.<Account>find(AccountController.accountTable,prod -> prod.id == buyerId);
        Room roomFound = Algorithm.<Room>find(RoomController.roomTable,pred -> pred.id == roomId);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date fromDate = sdf.parse(from);
        Date toDate = sdf.parse(to);

        int duration = toDate.getDate()-fromDate.getDate();

        double balance = roomFound.price.price*duration;

        //If account and room found
        if (accountFound  != null && roomFound != null) {

            if(accountFound.balance >= roomFound.price.price && Payment.availability(fromDate, toDate, roomFound)) {
                Payment newPayment = new Payment(buyerId,renterId,roomId);
                accountFound.balance = accountFound.balance - roomFound.price.price;
                newPayment.status = Invoice.PaymentStatus.WAITING;
                newPayment.makeBooking(fromDate, toDate, roomFound);

                paymentTable.add(newPayment);
                return newPayment;
            }
        }
        return null;
    }
    @PostMapping("/{id}/accept")
    boolean accept(@PathVariable int id){
        Payment paymentFound = Algorithm.<Payment>find(paymentTable,payment -> payment.id == id);
        if (paymentFound != null && paymentFound.status == Invoice.PaymentStatus.WAITING) {
            paymentFound.status = Invoice.PaymentStatus.SUCCESS;

            return true;
        }
        return false;
    }

    @PostMapping("/{id}/cancel")
    boolean cancel(@PathVariable int id){
        Payment paymentFound = Algorithm.<Payment>find(paymentTable,payment -> payment.id == id);
        Account accountFound = Algorithm.<Account>find(AccountController.accountTable,prod -> prod.id == paymentFound.buyerId);
        Room roomFound = Algorithm.<Room>find(RoomController.roomTable,pred -> pred.id == paymentFound.getRoomId());

        if (paymentFound != null && paymentFound.status == Invoice.PaymentStatus .WAITING) {
            paymentFound.status = Invoice.PaymentStatus.FAILED;
            accountFound .balance += roomFound.price.price;

            return true;
        }
        return false;
    }

    @PostMapping("/{id}/submit")
    boolean submit(@PathVariable int id){
        return false;
    }
}



