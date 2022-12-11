package com.netlab.RoyOswaldhaJSleepRJ;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.netlab.RoyOswaldhaJSleepRJ.model.BedType;
import com.netlab.RoyOswaldhaJSleepRJ.model.City;
import com.netlab.RoyOswaldhaJSleepRJ.model.Facility;
import com.netlab.RoyOswaldhaJSleepRJ.model.Room;
import com.netlab.RoyOswaldhaJSleepRJ.request.BaseApiService;
import com.netlab.RoyOswaldhaJSleepRJ.request.UtilsApi;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateRoomActivity extends AppCompatActivity {
    BaseApiService mApiService;
    Context mContext;
    private EditText roomName, roomPrice, roomAddress, roomSize, roomImage;
    private CheckBox AC,Refrigerator, WiFi, Bathtub, Balcony, Restaurant, SwimPool, FitnessCenter;
    private City city;
    private BedType bedType;
    ArrayList<Facility> facility = new ArrayList<Facility>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);
        mApiService = UtilsApi.getApiService();
        mContext = this;

        //Input
        roomName = findViewById(R.id.CreateRoomActivity_roomNameInput);
        roomPrice = findViewById(R.id.CreateRoomActivity_roomPriceInput);
        roomAddress = findViewById(R.id.CreateRoomActivity_roomAddressInput);
        roomSize = findViewById(R.id.CreateRoomActivity_roomSizeInput);
        roomImage = findViewById(R.id.CreateRoomActivity_roomImageInput);

        //Check Box
        AC = findViewById(R.id.CreateRoomActivity_checkBox_facilityAC);
        Refrigerator = findViewById(R.id.CreateRoomActivity_checkBox_facilityRefrigerator);
        WiFi = findViewById(R.id.CreateRoomActivity_checkBox_facilityWifi);
        Bathtub = findViewById(R.id.CreateRoomActivity_checkBox_facilityBathub);
        Balcony = findViewById(R.id.CreateRoomActivity_checkBox_facilityBalcony);
        Restaurant = findViewById(R.id.CreateRoomActivity_checkBox_facilityRestaurant);
        SwimPool = findViewById(R.id.CreateRoomActivity_checkBox_facilitySwimmingPool);
        FitnessCenter = findViewById(R.id.CreateRoomActivity_checkBox_facilityFitnessCenter);

        //Facility input
        AC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(AC.isChecked()){
                    facility.add(Facility.AC);
                }else{
                    facility.remove(Facility.AC);
                }
            }
        });

        Refrigerator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Refrigerator.isChecked()){
                    facility.add(Facility.Refrigerator);
                }else{
                    facility.remove(Facility.Refrigerator);
                }
            }
        });

        WiFi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(WiFi.isChecked()){
                    facility.add(Facility.WiFi);
                }else{
                    facility.remove(Facility.WiFi);
                }
            }
        });

        Bathtub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Bathtub.isChecked()){
                    facility.add(Facility.Bathtub);
                }else{
                    facility.remove(Facility.Bathtub);
                }
            }
        });

        Balcony.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Balcony.isChecked()){
                    facility.add(Facility.Balcony);
                }else{
                    facility.remove(Facility.Balcony);
                }
            }
        });

        Restaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Restaurant.isChecked()){
                    facility.add(Facility.Restaurant);
                }else{
                    facility.remove(Facility.Restaurant);
                }
            }
        });

        SwimPool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(SwimPool.isChecked()){
                    facility.add(Facility.SwimmingPool);
                }else{
                    facility.remove(Facility.SwimmingPool);
                }
            }
        });

        FitnessCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(FitnessCenter.isChecked()){
                    facility.add(Facility.FitnessCenter);
                }else{
                    facility.remove(Facility.FitnessCenter);
                }
            }
        });

        //Create Room Button
        Button createRoomButton = findViewById(R.id.createRoomButton);
        Button cancelCreateRoomButton = findViewById(R.id.createRoomCancelButton);

        //Spinner
        Spinner spinnerCity = (Spinner) findViewById(R.id.CreateRoomActivity_spinnerCity);

        ArrayAdapter<City> cityList = new ArrayAdapter<City>(
                mContext,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                City.values()
        );
        Spinner spinnerBedType = (Spinner) findViewById(R.id.CreateRoomActivity_spinnerBedType);
        ArrayAdapter<BedType> bedTypeList = new ArrayAdapter<BedType>(
                mContext,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                BedType.values()
        );

        spinnerCity.setAdapter(cityList);
        spinnerBedType.setAdapter(bedTypeList);

        //Create room button pressed
        createRoomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                city = (City) spinnerCity.getSelectedItem();
                bedType = (BedType) spinnerBedType.getSelectedItem();

                Room room = requestCreateRoom();
                Intent intent = new Intent(mContext, MainActivity.class);
                startActivity(intent);
            }
        });

        //Cancel button pressed
        cancelCreateRoomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    //Make new room
    protected Room requestCreateRoom(){
        mApiService.requestRoom(LoginActivity.accountLogin.id,
                roomName.getText().toString(),
                Integer.parseInt(roomSize.getText().toString()),
                Integer.parseInt(roomPrice.getText().toString()),
                facility,
                this.city,
                roomAddress.getText().toString(),
                this.bedType,
                roomImage.getText().toString()).enqueue(new Callback<Room>() {
            @Override
            public void onResponse(Call<Room> call, Response<Room> response) {
                com.netlab.RoyOswaldhaJSleepRJ.model.Room room = response.body();
                Toast.makeText(mContext, "Room created!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<com.netlab.RoyOswaldhaJSleepRJ.model.Room> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(mContext, "Room didn't created!", Toast.LENGTH_LONG).show();
            }
        });
        return null;
    }
}