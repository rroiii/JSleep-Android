package com.netlab.RoyOswaldhaJSleepRJ;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
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

public class MainFragment extends Fragment implements RoomAdapter.RoomClickListener {
    private RecyclerView roomListView;
    public static List<Room> allRooms;
    private RoomAdapter roomAdapter;
    private SearchView searchView;
    private Button prevButton, nextButton, goButton;
    private  ImageButton filterButton;
    private EditText page;
    private int currentPage = 0, pageSize = 10;
    public static int selectedRoom;
    private ImageView imgNotFound;
    private ArrayList<String> filterCity = new ArrayList<>();
    private   Dialog dialog;
    BaseApiService mApiService;
    Context mContext;
    private ProgressBar progressBar;

    private TextView hotelNotFound;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        mApiService = UtilsApi.getApiService();
        mContext = getContext();
        searchView = view.findViewById(R.id.main_searchBar);
        searchView.clearFocus();
        progressBar = view.findViewById(R.id.main_progressBar);

        roomListView = view.findViewById(R.id.main_roomListView);
        roomListView.setLayoutManager(new LinearLayoutManager(getContext()));
        roomListView.setHasFixedSize(true);

        hotelNotFound = view.findViewById(R.id.main_roomNotFound);
        imgNotFound  = (ImageView)view.findViewById(R.id.fragmentMain_imageNotFound);
        roomListView.setVisibility(View.VISIBLE);
        hotelNotFound.setVisibility(View.INVISIBLE);
        imgNotFound.setVisibility(View.INVISIBLE);

        //Search bar
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String searchRoom) {
                filterListRoom(searchRoom, allRooms);
                return true;
            }
        });

        //Filter by city
        filterButton  = (ImageButton)view.findViewById(R.id.fragmentMain_filter);
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog();
            }
        });

        //Button Paginate
        page= (EditText)view.findViewById(R.id.fragmentMain_page);
        getRoomList(currentPage,pageSize);

        prevButton = (Button)view.findViewById(R.id.fragmentMain_PrevButton);
        nextButton = (Button)view.findViewById(R.id.fragmentMain_NextButton);
        goButton = (Button) view.findViewById(R.id.fragmentMain_GoButton);

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentPage > 0){
                    currentPage--;
                }else{
                    currentPage = 0;
                    page.setText(String.valueOf(currentPage+1));
                }
                getRoomList(currentPage,pageSize);
                page.setText(String.valueOf(currentPage+1));
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pageTotal = (allRooms.size()/pageSize);
                if(allRooms.size() % pageSize != 0){
                    pageTotal = pageTotal + 1;
                }
                if(allRooms.size()<pageSize){
                    pageTotal = 0;
                }
                if(currentPage > pageTotal){
                    currentPage = pageTotal;
                }
                if(currentPage <= pageTotal){
                    currentPage++;
                }


                getRoomList(currentPage,pageSize);
                page.setText(String.valueOf(currentPage+1));
            }
        });

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPage = Integer.parseInt(page.getText().toString())-1;
                getRoomList(currentPage,pageSize);
                page.setText(String.valueOf(currentPage+1));
            }
        });

        return view;
    }

    //Filter room by city
    private void filterListRoomCity(List<String> Cities, List<Room> Room) {
        List<Room> searchedRoom = new ArrayList<>();
        for(Room room : Room){
            for(String city : Cities){
                if(room.city.toString().equalsIgnoreCase(city)){
                    searchedRoom.add(room);
                    roomListView.setVisibility(View.VISIBLE);
                    hotelNotFound.setVisibility(View.INVISIBLE);
                    imgNotFound.setVisibility(View.INVISIBLE);
                }
            }
        }

        if(searchedRoom.isEmpty()){
            Toast.makeText(mContext, "No Data Found", Toast.LENGTH_SHORT).show();
            roomListView.setVisibility(View.INVISIBLE);
            hotelNotFound.setVisibility(View.VISIBLE);
            imgNotFound.setVisibility(View.VISIBLE);
        }else{
            roomAdapter.setFilteredList(searchedRoom);
            roomListView.setVisibility(View.VISIBLE);
            hotelNotFound.setVisibility(View.INVISIBLE);
            imgNotFound.setVisibility(View.INVISIBLE);
        }
    }

    //Filter room by room name
    private void filterListRoom(String searchRoom, List<Room> Room) {
        List<Room> searchedRoom = new ArrayList<>();
        for(Room room : Room){
            if(room.getName().toLowerCase().contains(searchRoom.toLowerCase())){
                searchedRoom.add(room);
                roomListView.setVisibility(View.VISIBLE);
                hotelNotFound.setVisibility(View.INVISIBLE);
                imgNotFound.setVisibility(View.INVISIBLE);
            }
        }

        if(searchedRoom.isEmpty()){
            Toast.makeText(mContext, "No Data Found", Toast.LENGTH_SHORT).show();
            roomListView.setVisibility(View.INVISIBLE);
            hotelNotFound.setVisibility(View.VISIBLE);
            imgNotFound.setVisibility(View.VISIBLE);
        }else{
            roomAdapter.setFilteredList(searchedRoom);
            roomListView.setVisibility(View.VISIBLE);
            hotelNotFound.setVisibility(View.INVISIBLE);
            imgNotFound.setVisibility(View.INVISIBLE);
        }
    }

    //Get all room data from json
    protected List<Room> getRoomList(int page, int pageSize) {
        mApiService.getAllRoom(page, pageSize).enqueue(new Callback<List<Room>>() {
            @Override
            public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    allRooms = response.body();
                    roomAdapter = new RoomAdapter(allRooms, getContext(), (view, position) -> {
                        selectedRoom = position;

                        RoomDetailsActivity.roomSelected = getRoomSelected();
                        Intent intent = new Intent(getContext(), RoomDetailsActivity.class);
                        startActivity(intent);
                    });

                    roomListView.setAdapter(roomAdapter);
                    roomAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<List<Room>> call, Throwable t) {
                t.printStackTrace();
                progressBar.setVisibility(View.GONE);
                Toast.makeText(mContext, "Failed", Toast.LENGTH_SHORT).show();
            }

        });
        return null;
    }

    //If recycler view clicked
    @Override
    public void onItemClick(View view, int position){
    }

    //Alert dialog for filter
    private void showAlertDialog() {
        final String[] cities = {"Bali", "Bandung", "Surabaya", "Jakarta", "Semarang", "Medan", "Depok", "Bekasi", "Lampung"};


        final ArrayList itemsSelected = new ArrayList();

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle("Filter");

        builder.setMultiChoiceItems(cities, null,
                        new DialogInterface.OnMultiChoiceClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int selectedItemId,
                                                boolean isSelected) {
                                if (isSelected) {
                                    itemsSelected.add(cities[selectedItemId]);
                                } else if (itemsSelected.contains(cities[selectedItemId])) {
                                    itemsSelected.remove(Integer.valueOf(selectedItemId));
                                }
                            }

                        })
                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        filterCity = itemsSelected;
                        filterListRoomCity(filterCity,allRooms);
                        Log.d("list", itemsSelected.toString());
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        getRoomList(0,pageSize);
                        roomAdapter.setFilteredList(allRooms);

                    }
                });

        dialog = builder.create();

        dialog.show();
    }

    //Get room data on click recycler view
    public static Room getRoomSelected(){
        return allRooms.get(selectedRoom);
    }
}