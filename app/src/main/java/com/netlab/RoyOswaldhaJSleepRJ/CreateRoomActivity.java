
package com.netlab.RoyOswaldhaJSleepRJ;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.netlab.RoyOswaldhaJSleepRJ.model.*;
import com.netlab.RoyOswaldhaJSleepRJ.model.City;
import com.netlab.RoyOswaldhaJSleepRJ.model.Facility;
import com.netlab.RoyOswaldhaJSleepRJ.request.BaseApiService;
import com.netlab.RoyOswaldhaJSleepRJ.request.UtilsApi;

import java.util.ArrayList;

import retrofit2.*;
public class CreateRoomActivity extends AppCompatActivity {
    BaseApiService mApiService;
    Context mContext;
    EditText roomName, roomPrice, roomAddress, roomSize;
    CheckBox AC,Refrigerator, WiFi, Bathtub, Balcony, Restaurant, SwimPool, FitnessCenter;
    City city;
    BedType bedType;
    ArrayList<Facility> facility = new ArrayList<Facility>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);

        mApiService = UtilsApi.getApiService();
        mContext = this;

        roomName = findViewById(R.id.createRoomName);
        roomPrice = findViewById(R.id.createRoomPrice);
        roomAddress = findViewById(R.id.createRoomAddress);
        roomSize = findViewById(R.id.createRoomSize);

        AC = findViewById(R.id.facilityAC);
        Refrigerator = findViewById(R.id.facilityRefrigerator);
        WiFi = findViewById(R.id.facilityWifi);
        Bathtub = findViewById(R.id.facilityBathub);
        Balcony = findViewById(R.id.facilityBalcony);
        Restaurant = findViewById(R.id.facilityRestaurant);
        SwimPool = findViewById(R.id.facilitySwimmingPool);
        FitnessCenter = findViewById(R.id.facilityFitnessCenter);

        Button createRoomButton = findViewById(R.id.createRoomButton);
        Button cancelCreateRoomButton = findViewById(R.id.createRoomCancelButton);

        Spinner spinnerCity = (Spinner) findViewById(R.id.spinnerCity);
        ArrayAdapter<City> cityList = new ArrayAdapter<City>(
                this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                City.values()
        );
        Spinner spinnerBedType = (Spinner) findViewById(R.id.spinnerBedType);
        ArrayAdapter<BedType> bedTypeList = new ArrayAdapter<BedType>(
                this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                BedType.values()
        );

        spinnerCity.setAdapter(cityList);
        spinnerBedType.setAdapter(bedTypeList);

        createRoomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(AC.isChecked())
                    facility.add(Facility.AC);
                if(Refrigerator.isChecked())
                    facility.add(Facility.Refrigerator);
                if(WiFi.isChecked())
                    facility.add(Facility.WiFi);
                if(Bathtub.isChecked())
                    facility.add(Facility.Bathtub);
                if(Balcony.isChecked())
                    facility.add(Facility.Balcony);
                if(Restaurant.isChecked())
                    facility.add(Facility.Restaurant);
                if(SwimPool.isChecked())
                    facility.add(Facility.SwimmingPool);
                if(FitnessCenter.isChecked())
                    facility.add(Facility.FitnessCenter);
                city = (City) spinnerCity.getSelectedItem();
                bedType = (BedType) spinnerBedType.getSelectedItem();

                Room room = requestCreateRoom();
            }
        });

        cancelCreateRoomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent move = new Intent(CreateRoomActivity.this, MainActivity.class);
                startActivity(move);
            }
        });
    }

    protected Room requestCreateRoom(){
        mApiService.requestRoom(LoginActivity.accountLogin.id, roomName.getText().toString(), Integer.parseInt(roomSize.getText().toString()), Integer.parseInt(roomPrice.getText().toString()), facility, this.city, roomAddress.getText().toString(), this.bedType).enqueue(new Callback<com.netlab.RoyOswaldhaJSleepRJ.model.Room>() {
            @Override
            public void onResponse(Call<com.netlab.RoyOswaldhaJSleepRJ.model.Room> call, Response<com.netlab.RoyOswaldhaJSleepRJ.model.Room> response) {
                com.netlab.RoyOswaldhaJSleepRJ.model.Room room = response.body();
                System.out.println(room);
            }

            @Override
            public void onFailure(Call<com.netlab.RoyOswaldhaJSleepRJ.model.Room> call, Throwable t) {
                t.printStackTrace();
            }
        });
        return null;
    }
}
