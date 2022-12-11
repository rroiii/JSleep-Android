package com.netlab.RoyOswaldhaJSleepRJ;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.netlab.RoyOswaldhaJSleepRJ.model.Payment;
import com.netlab.RoyOswaldhaJSleepRJ.model.Room;
import com.netlab.RoyOswaldhaJSleepRJ.request.BaseApiService;
import com.netlab.RoyOswaldhaJSleepRJ.request.UtilsApi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookRoomActivity extends AppCompatActivity {
    BaseApiService mApiService;
    Context mContext;

    private Room roomSelected = RoomDetailsActivity.roomSelected;
    private TextView roomName, roomAddress, roomCity, roomSize, roomBedType, roomPrice, night, priceXduration;
    static TextView priceDiscount, totalPrice;
    private Button bookRoom, applyVoucher;
    private EditText dateFromInput, dateToInput;
    private String from, to;
    private Date fromDate, toDate;
    public static Payment newPayment;
    private long duration,durationInDays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_room);
        mApiService = UtilsApi.getApiService();
        mContext = this;

        //Room Summary
        roomName = findViewById(R.id.BookRoomActivity_roomNameOutput);
        roomAddress = findViewById(R.id.BookRoomActivity_roomAddressOutput);
        roomCity = findViewById(R.id.BookRoomActivity_roomCityOutput);
        roomSize = findViewById(R.id.BookRoomActivity_roomSizeOutput);
        roomBedType = findViewById(R.id.BookRoomActivity_roomBedTypeOutput);
        roomPrice = findViewById(R.id.BookRoomActivity_roomPriceOutput);

        roomName.setText(roomSelected.name);
        roomAddress.setText(roomSelected.address);
        roomCity.setText(roomSelected.getCity());
        roomSize.setText(String.valueOf(roomSelected.size));
        roomBedType.setText(roomSelected.getBedType());
        roomPrice.setText(roomSelected.getPrice() + " / Night");

        //Price Section
        priceXduration = findViewById(R.id.BookRoomActivity_roomPriceXdurationOutput);
        priceDiscount = findViewById(R.id.BookRoomActivity_roomDiscountOutput);
        totalPrice = findViewById(R.id.BookRoomActivity_totalPriceOutput);

        //Rent Section
        dateFromInput = findViewById(R.id.BookRoomActivity_dateFromInput);
        dateToInput = findViewById(R.id.BookRoomActivity_dateToInput);
        night = findViewById(R.id.BookRoomActivity_nightOutput);
        bookRoom = (Button) findViewById(R.id.BookRoomActivity_bookButton);

        night.setText("0");
        priceXduration.setText("Rp 0");
        priceDiscount.setText("Rp 0");
        totalPrice.setText("Rp 0");
        dateFromInput.setText("");
        dateToInput.setText("");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        //Input date and make new payment object
        //Start date
        dateFromInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar newCalendar = Calendar.getInstance();

                DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, month, day);
                        dateFromInput.setText(day + "/" + (month + 1) + "/" + year);
                        from = year + "-" + (month + 1) + "-" + day;
                        try {
                            fromDate = sdf.parse(from);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
                datePickerDialog.getDatePicker().setMinDate(newCalendar.getTimeInMillis());

            }
        });

        //End date input
        dateToInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar newCalendar = Calendar.getInstance();

                DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, month, day);
                        dateToInput.setText(day + "/" + (month + 1) + "/" + year);
                        to = year + "-" + (month + 1) + "-" + day;
                        try {
                            toDate = sdf.parse(to);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        if (toDate != null && fromDate != null) {

                            duration = toDate.getTime() - fromDate.getTime();
                            durationInDays = TimeUnit.MILLISECONDS.toDays(duration);
                            night.setText(String.valueOf(durationInDays));
                            night.setVisibility(View.VISIBLE);

                            requestPayment(from, to);

                            priceXduration.setText(String.valueOf(roomSelected.price * durationInDays));
                            priceDiscount.setText("Rp 0");
                            totalPrice.setText(String.valueOf(roomSelected.price * durationInDays));
                            bookRoom.setEnabled(true);
                        }

                    }
                }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
                long dateAfterFrom = fromDate.getTime();
                long dateAfterFromDays = TimeUnit.MILLISECONDS.toDays(dateAfterFrom) + 2;
                long dateAfterFromMillis = TimeUnit.DAYS.toMillis(dateAfterFromDays);
                datePickerDialog.getDatePicker().setMinDate(dateAfterFromMillis);
            }
        });


        //Apply Voucher
        applyVoucher = (Button)findViewById(R.id.BookRoomActivity_voucher);

        applyVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,VoucherCanApplyActivity.class);
                startActivity(intent);
                priceXduration.setText(String.valueOf(roomSelected.price * durationInDays));
            }
        });


        //Book Room Button
        bookRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    //Make new payment
    protected Payment requestPayment(String from, String to) {
        mApiService.requestPayment(LoginActivity.accountLogin.id, roomSelected.accountId, roomSelected.id, from, to).enqueue(new Callback<Payment>() {
            @Override
            public void onResponse(Call<Payment> call, Response<Payment> response) {
                newPayment = response.body();
                Toast.makeText(mContext, "Booked!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Payment> call, Throwable t) {
                Toast.makeText(mContext, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }

}


