package com.netlab.RoyOswaldhaJSleepRJ;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.netlab.RoyOswaldhaJSleepRJ.model.Facility;
import com.netlab.RoyOswaldhaJSleepRJ.model.Renter;
import com.netlab.RoyOswaldhaJSleepRJ.model.Room;
import com.netlab.RoyOswaldhaJSleepRJ.model.RoomFacility;
import com.netlab.RoyOswaldhaJSleepRJ.request.BaseApiService;
import com.netlab.RoyOswaldhaJSleepRJ.request.UtilsApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoomDetailsActivity extends AppCompatActivity {
    public static Room roomSelected;
    BaseApiService mApiService;
    Context mContext;
    private TextView roomName, roomAddress, ratingValue, renterName, renterAddress, renterPhoneNumber, roomPrice, roomPriceCenter;
    private RatingBar roomRating;
    private Button bookRoom, manageRoom;
    private ImageView roomImage;
    public static CheckBox bookmarkRoom;
    private Renter selectedRenter;
    private List<RoomFacility> facilities;
    private RecyclerView facilityList;
    private FacilityAdapter facilityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_details);
        mApiService = UtilsApi.getApiService();
        mContext = this;

        bookmarkRoom = (CheckBox)findViewById(R.id.RoomDetailsActivity_bookmarkRoom);

        if(LoginActivity.accountLogin.favoriteRoom.contains(Integer.valueOf(roomSelected.id))){
            bookmarkRoom.setChecked(true);
        }else{
            bookmarkRoom.setChecked(false);
        }

        roomName = findViewById(R.id.RoomDetailsActivity_roomName);
        roomImage = findViewById(R.id.RoomDetailsActivity_roomImage);
        roomAddress = findViewById(R.id.RoomDetailsActivity_roomAddress);
        roomRating = findViewById(R.id.RoomDetailsActivity_roomRating);
        roomPrice = findViewById(R.id.RoomDetailsActivity_roomPrice);
        ratingValue = findViewById(R.id.RoomDetailsActivity_roomRatingValue);
        bookRoom = (Button) findViewById(R.id.roomDetailsActivity_bookButton);
        manageRoom = (Button) findViewById(R.id.RoomDetailsActivity_ManageButton);
        renterName = findViewById(R.id.RoomDetailsActivity_renterNameOutput);
        renterAddress = findViewById(R.id.RoomDetailsActivity_renterAddressOutput);
        renterPhoneNumber = findViewById(R.id.RoomDetailsActivity_renterPhoneNumberOutput);
        roomPriceCenter = findViewById(R.id.roomDetailsActivity_roomPriceCenter);

        roomName.setText(roomSelected.name);
        Glide.with(mContext).load(roomSelected.image).into(roomImage);
        roomAddress.setText(roomSelected.address);
        roomRating.setRating(roomSelected.getRating());
        ratingValue.setText(roomRating.getRating()+"/5"+" ("+ roomSelected.rating.getCount() + " Reviews)");
        roomPrice.setText(roomSelected.getPrice());
        roomPriceCenter.setText(roomSelected.getPrice());
        getRenterOfRoom();

        //Bookmark
        bookmarkRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bookmarkRoom.isChecked()){
                    addToFavorite(roomSelected.id);

                    Toast.makeText(mContext, "Added", Toast.LENGTH_SHORT).show();
                }else{
                    removeFromFavorite();

                    Toast.makeText(mContext, "Removed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        if(LoginActivity.accountLogin.renter == null){
            bookRoom.setVisibility(View.VISIBLE);
            manageRoom.setVisibility(View.INVISIBLE);
            roomPriceCenter.setVisibility(View.INVISIBLE);
        }
        if(LoginActivity.accountLogin.renter != null && roomSelected.accountId ==  LoginActivity.accountLogin.id){
            bookRoom.setVisibility(View.INVISIBLE);
            manageRoom.setVisibility(View.VISIBLE);
            roomPriceCenter.setVisibility(View.INVISIBLE);
        }
        if(LoginActivity.accountLogin.renter != null && roomSelected.accountId !=  LoginActivity.accountLogin.id){
            bookRoom.setVisibility(View.INVISIBLE);
            manageRoom.setVisibility(View.INVISIBLE);
            roomPriceCenter.setVisibility(View.VISIBLE);
            roomPrice.setVisibility(View.INVISIBLE);
        }
        bookRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, BookRoomActivity.class);
                startActivity(intent);
            }
        });

        //Room Facilities
        facilities = new ArrayList<>();
        facility();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        facilityList = findViewById(R.id.RoomDetailsActivity_facilityList);
        facilityList.setLayoutManager(layoutManager);
        facilityList.setHasFixedSize(true);

        facilityAdapter = new FacilityAdapter(facilities, this);

        facilityList.setAdapter(facilityAdapter);
        facilityAdapter.notifyDataSetChanged();

        if(LoginActivity.accountLogin.renter!=null){

        }
        if(LoginActivity.accountLogin.renter == null){

        }

    }

    protected Boolean addToFavorite(int roomId){
        mApiService.addToFavorite(LoginActivity.accountLogin.id, roomId).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                bookmarkRoom.setChecked(true);
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                bookmarkRoom.setChecked(false);
            }
        });
        return null;
    }

    protected Renter getRenterOfRoom(){
        mApiService.getRenterOfRoom(roomSelected.accountId).enqueue(new Callback<Renter>() {
            @Override
            public void onResponse(Call<Renter> call, Response<Renter> response) {
                selectedRenter = response.body();
                renterName.setText(selectedRenter.username);
                renterAddress.setText(selectedRenter.address);
                renterPhoneNumber.setText(selectedRenter.phoneNumber);
            }

            @Override
            public void onFailure(Call<Renter> call, Throwable t) {
            }
        });
        return null;
    }

    protected Boolean removeFromFavorite(){
        mApiService.removeFromFavorite(LoginActivity.accountLogin.id,roomSelected.id).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                bookmarkRoom.setChecked(false);
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                bookmarkRoom.setChecked(true);
            }
        });
        return null;
    }

    void facility() {
        roomSelected.facility.forEach(roomFacility -> {
            if(roomFacility.equals(Facility.AC)){
                facilities.add(new RoomFacility("AC", R.drawable.ic_ac));
            }
            if(roomFacility.equals(Facility.WiFi)){
                facilities.add(new RoomFacility("WiFi", R.drawable.ic_baseline_wifi_24));
            }
            if(roomFacility.equals(Facility.Refrigerator)){
                facilities.add(new RoomFacility("Refrigerator", R.drawable.ic_refrigerator));
            }
            if(roomFacility.equals(Facility.Bathtub)){
                facilities.add(new RoomFacility("Bathtub", R.drawable.ic_bathub));
            }
            if(roomFacility.equals(Facility.Balcony)){
                facilities.add(new RoomFacility("Balcony", R.drawable.ic_balcony));
            }
            if(roomFacility.equals(Facility.Restaurant)){
                facilities.add(new RoomFacility("Restaurant", R.drawable.ic_restaurant));
            }
            if(roomFacility.equals(Facility.SwimmingPool)){
                facilities.add(new RoomFacility("Swimming Pool", R.drawable.ic_baseline_pool_24));
            }
            if(roomFacility.equals(Facility.FitnessCenter)){
                facilities.add(new RoomFacility("Fitnes Center", R.drawable.ic_fitness_center));
            }
        });
    }
}