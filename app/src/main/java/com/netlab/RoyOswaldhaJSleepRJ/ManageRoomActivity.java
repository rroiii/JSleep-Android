package com.netlab.RoyOswaldhaJSleepRJ;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.netlab.RoyOswaldhaJSleepRJ.model.Room;
import com.netlab.RoyOswaldhaJSleepRJ.request.BaseApiService;
import com.netlab.RoyOswaldhaJSleepRJ.request.UtilsApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageRoomActivity extends AppCompatActivity {
    private RecyclerView yourRoomList;
    private RoomAdapter roomAdapter;
    private SearchView searchView;
    BaseApiService mApiService;
    Context mContext;
    private ProgressBar progressBar;
    private TextView hotelNotFound;
    private List<Room> yourRooms;
    private int selectedManageRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_room);

        mApiService = UtilsApi.getApiService();
        mContext = this;
        searchView = findViewById(R.id.ManageRoomActivity_searchBar);
        searchView.clearFocus();
        progressBar = findViewById(R.id.ManageRoomActivity_progressBar);
        yourRoomList = findViewById(R.id.ManageRoomActivity_roomListView);
        yourRoomList.setLayoutManager(new LinearLayoutManager(this));
        yourRoomList.setHasFixedSize(true);
        hotelNotFound = findViewById(R.id.ManageRoomActivity_roomNotFound);
        yourRoomList.setVisibility(View.VISIBLE);
        hotelNotFound.setVisibility(View.INVISIBLE);

        //Search bar
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String searchRoom) {
                filterListRoom(searchRoom, yourRooms);
                return true;
            }
        });

        getYourRoom();
    }

    //Filter room by name
    private void filterListRoom(String searchRoom, List<Room> Room) {
        List<Room> searchedRoom = new ArrayList<>();
        for(Room room : Room){
            if(room.getName().toLowerCase().contains(searchRoom.toLowerCase())){
                searchedRoom.add(room);
                yourRoomList.setVisibility(View.VISIBLE);
                hotelNotFound.setVisibility(View.INVISIBLE);
            }
        }

        if(searchedRoom.isEmpty()){
            Toast.makeText(mContext, "No Data Found", Toast.LENGTH_SHORT).show();
            yourRoomList.setVisibility(View.INVISIBLE);
            hotelNotFound.setVisibility(View.VISIBLE);
        }else{
            roomAdapter.setFilteredList(searchedRoom);
            yourRoomList.setVisibility(View.VISIBLE);
            hotelNotFound.setVisibility(View.INVISIBLE);
        }
    }

    //Get your room
    protected  List<Room> getYourRoom() {
        mApiService.getRoomByRenter(LoginActivity.accountLogin.id).enqueue(new Callback<List<Room>>() {
            @Override
            public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    yourRooms = response.body();
                    roomAdapter = new RoomAdapter(yourRooms, mContext, (view, position) -> {
                        selectedManageRoom = position;

                        RoomDetailsActivity.roomSelected = getmanageRoomSelected();
                        Intent intent = new Intent(mContext, RoomDetailsActivity.class);
                        startActivity(intent);
                    });

                    yourRoomList.setAdapter(roomAdapter);
                    roomAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Room>> call, Throwable t) {

            }
        });
        return null;
    }

    //Selected managed room
    Room getmanageRoomSelected(){
        return yourRooms.get(selectedManageRoom);
    }
}