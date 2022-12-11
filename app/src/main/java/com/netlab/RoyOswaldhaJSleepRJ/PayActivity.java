package com.netlab.RoyOswaldhaJSleepRJ;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.netlab.RoyOswaldhaJSleepRJ.model.Payment;
import com.netlab.RoyOswaldhaJSleepRJ.model.Room;
import com.netlab.RoyOswaldhaJSleepRJ.request.BaseApiService;
import com.netlab.RoyOswaldhaJSleepRJ.request.UtilsApi;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PayActivity extends AppCompatActivity {
    private Payment paymentWaitingSelected = TransactionFragment.getPaymentSelectedOnGoing();
    private Room selectedRoomPayment;
    private Payment payment;
    private Date from, to;
    private Button acceptButton, cancelButtonBuyer, cancelButtonRenter;
    BaseApiService mApiService;
    Context mContext;
    private TextView roomName, roomCity, roomBedType, fromDate, toDate, roomPrice, price, discount, totalPrice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        mApiService = UtilsApi.getApiService();
        mContext = this;
        //Invoice
        roomName = findViewById(R.id.PayActivity_roomNameOutput);
        roomCity = findViewById(R.id.PayActivity_roomCityOutput);
        roomBedType = findViewById(R.id.PayActivity_roomBedTypeOutput);
        fromDate = findViewById(R.id.PayActivity_fromDateOutput);
        toDate = findViewById(R.id.PayActivity_toDateOutput);
        roomPrice = findViewById(R.id.PayActivity_roomPriceOutput);
        price = findViewById(R.id.PayActivity_roomPriceXdurationOutput);
        discount = findViewById(R.id.PayActivity_roomDiscountOutput);
        totalPrice = findViewById(R.id.PayActivity_totalPriceOutput);

        getRoom(paymentWaitingSelected.getRoomId());

        roomName.setText(selectedRoomPayment.name);
        roomCity.setText(selectedRoomPayment.getCity());
        roomBedType.setText(selectedRoomPayment.getBedType());
        roomPrice.setText(selectedRoomPayment.getPrice() + " / Night");

        //Date
        from = paymentWaitingSelected.from;
        to = paymentWaitingSelected.to;
        fromDate.setText(from.getDate() + " - " +  (from.getMonth()+1) + " - " +  (from.getYear()+1900));
        toDate.setText(to.getDate() + " - " +  (to.getMonth()+1) + " - " + (to.getYear()+1900));

        //Total Price
        long duration = to.getTime() - from.getTime();
        long durationInDays = TimeUnit.MILLISECONDS.toDays(duration);
        double balance = selectedRoomPayment.price;
        double totalFee = balance*durationInDays;

        String priceStr = String.valueOf(totalFee);
        String totalPriceStr = String.valueOf(paymentWaitingSelected.price.price);
        String discountPriceStr = String.valueOf(paymentWaitingSelected.price.discount);
        price.setText("Rp " + priceStr.substring(0, priceStr.length()-2));
        discount.setText("Rp " + discountPriceStr.substring(0, discountPriceStr.length()-2));
        totalPrice.setText("Rp " + totalPriceStr.substring(0, totalPriceStr.length()-2));

        //Button
        acceptButton = (Button)findViewById(R.id.PayActivity_acceptButton);
        cancelButtonBuyer = (Button) findViewById(R.id.PayActivity_cancelButton);
        cancelButtonRenter = (Button) findViewById(R.id.PayActivity_cancelButtonRenter);

        //If account is renter, only can cancel
        //If account is not renter, can cancel and accept
        if(LoginActivity.accountLogin.renter!=null){
            acceptButton.setVisibility(View.INVISIBLE);
            cancelButtonBuyer.setVisibility(View.INVISIBLE);
            cancelButtonRenter.setVisibility(View.VISIBLE);
        }
        if(LoginActivity.accountLogin.renter == null){
            acceptButton.setVisibility(View.VISIBLE);
            cancelButtonBuyer.setVisibility(View.VISIBLE);
            cancelButtonRenter.setVisibility(View.INVISIBLE);
        }

        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acceptPayment();
                Intent intent = new Intent(mContext,RatingActivity.class);
                startActivity(intent);
            }
        });

        cancelButtonBuyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelPayment();
                Intent intent = new Intent(mContext,MainActivity.class);
                startActivity(intent);
            }
        });

        cancelButtonRenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelPayment();
                Intent intent = new Intent(mContext,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    //payment accepted
    protected Boolean acceptPayment(){
        mApiService.acceptPayment(paymentWaitingSelected.id,paymentWaitingSelected.buyerId,paymentWaitingSelected.renterId).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.isSuccessful()){
                    Toast.makeText(mContext, "Payment Success!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });
        return null;
    }

    //payment canceled
    protected Boolean cancelPayment(){
        mApiService.cancelPayment(paymentWaitingSelected.id).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.isSuccessful()){
                    Toast.makeText(mContext, "Payment Canceled!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });
        return null;
    }

    //Get room data
    protected Room getRoom(int roomId){
        for(Room findRoom : MainFragment.allRooms){
            if(findRoom.id == roomId){
                selectedRoomPayment = findRoom;
            }
        }
        return null;
    }
}