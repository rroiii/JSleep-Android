package com.netlab.RoyOswaldhaJSleepRJ;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import com.netlab.RoyOswaldhaJSleepRJ.model.Payment;
import com.netlab.RoyOswaldhaJSleepRJ.model.Room;
import com.netlab.RoyOswaldhaJSleepRJ.request.BaseApiService;
import com.netlab.RoyOswaldhaJSleepRJ.request.UtilsApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RatingActivity extends AppCompatActivity {
    private Payment paymentWaitingSelected = TransactionFragment.getPaymentSelectedOnGoing();
    private Room selectedRoomPayment;

    private RatingBar ratingBar;
    private Button submitButton;
    private int rate;

    BaseApiService mApiService;
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        mApiService = UtilsApi.getApiService();
        mContext = this;

        ratingBar = findViewById(R.id.RatingActivity_ratingBar);
        submitButton = findViewById(R.id.RatingActivity_submitButton);
        getRoom(paymentWaitingSelected.getRoomId());

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rateRoom();
                Intent intent = new Intent(mContext,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    protected Boolean rateRoom(){
        mApiService.roomRating(selectedRoomPayment.id, (int)ratingBar.getRating()).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.isSuccessful()){
                    Toast.makeText(mContext, "Thank You!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });
        return null;
    }

    protected Room getRoom(int roomId){
        List<Room> findRoomById = new ArrayList<>();
        int i = 0;
        for(Room findRoom : MainFragment.allRooms){
            if(findRoom.id == roomId){
                selectedRoomPayment = MainFragment.allRooms.get(i);
            }
            i++;
        }
        return null;
    }
}