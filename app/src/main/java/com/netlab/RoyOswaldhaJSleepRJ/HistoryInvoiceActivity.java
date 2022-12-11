package com.netlab.RoyOswaldhaJSleepRJ;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.netlab.RoyOswaldhaJSleepRJ.model.Payment;
import com.netlab.RoyOswaldhaJSleepRJ.model.Room;
import com.netlab.RoyOswaldhaJSleepRJ.request.BaseApiService;
import com.netlab.RoyOswaldhaJSleepRJ.request.UtilsApi;

import java.util.Date;
import java.util.concurrent.TimeUnit;


public class HistoryInvoiceActivity extends AppCompatActivity {
    private Payment paymentHistorySelected = TransactionFragment.getPaymentSelectedHistory();
    private Room selectedRoomPayment;
    private Payment payment;
    private Date from, to;
    BaseApiService mApiService;
    Context mContext;
    private TextView roomName, roomCity, roomBedType, fromDate, toDate, roomPrice, price, discount, totalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_invoice);
        mApiService = UtilsApi.getApiService();
        mContext = this;

        //Invoice
        roomName = findViewById(R.id.HistoryInvoiceActivity_roomNameOutput);
        roomCity = findViewById(R.id.HistoryInvoiceActivity_roomCityOutput);
        roomBedType = findViewById(R.id.HistoryInvoiceActivity_roomBedTypeOutput);
        fromDate = findViewById(R.id.HistoryInvoiceActivity_fromDateOutput);
        toDate = findViewById(R.id.HistoryInvoiceActivity_toDateOutput);
        roomPrice = findViewById(R.id.HistoryInvoiceActivity_roomPriceOutput);
        price = findViewById(R.id.HistoryInvoiceActivity_roomPriceXdurationOutput);
        discount = findViewById(R.id.HistoryInvoiceActivity_roomDiscountOutput);
        totalPrice = findViewById(R.id.HistoryInvoiceActivity_totalPriceOutput);

        getRoom(paymentHistorySelected.getRoomId());

        roomName.setText(selectedRoomPayment.name);
        roomCity.setText(selectedRoomPayment.getCity());
        roomBedType.setText(selectedRoomPayment.getBedType());
        roomPrice.setText(selectedRoomPayment.getPrice() + " / Night");

        //Date
        from = paymentHistorySelected.from;
        to = paymentHistorySelected.to;
        fromDate.setText(from.getDate() + " - " +  (from.getMonth()+1) + " - " +  (from.getYear()+1900));
        toDate.setText(to.getDate() + " - " +  (to.getMonth()+1) + " - " + (to.getYear()+1900));

        //Total Price
        long duration = to.getTime() - from.getTime();
        long durationInDays = TimeUnit.MILLISECONDS.toDays(duration);
        double balance = selectedRoomPayment.price;
        double totalFee = balance*durationInDays;

        String priceStr = String.valueOf(totalFee);
        String totalPriceStr = String.valueOf(paymentHistorySelected.price.price);
        String discountPriceStr = String.valueOf(paymentHistorySelected.price.discount);
        price.setText("Rp " + priceStr.substring(0, priceStr.length()-2));
        discount.setText("Rp " + discountPriceStr.substring(0, discountPriceStr.length()-2));
        totalPrice.setText("Rp " + totalPriceStr.substring(0, totalPriceStr.length()-2));

    }

    protected Room getRoom(int roomId){
        for(Room findRoom : MainFragment.allRooms){
            if(findRoom.id == roomId){
                selectedRoomPayment = findRoom;
            }
        }
        return null;
    }
}