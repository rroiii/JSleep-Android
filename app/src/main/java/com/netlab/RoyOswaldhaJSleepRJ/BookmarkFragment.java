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
import android.widget.ImageButton;
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

public class BookmarkFragment extends Fragment {
    private RecyclerView favoriteRoomList;
    private RoomAdapter roomAdapter;
    private SearchView searchView;
    BaseApiService mApiService;
    Context mContext;
    private ProgressBar progressBar;
    private TextView hotelNotFound;
    List<Room> favoriteRooms;
    private ImageButton filterButton;
    private int selectedFavoriteRoom;
    private ArrayList<String> filterCity = new ArrayList<>();
    private Dialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookmark, container, false);

        mApiService = UtilsApi.getApiService();
        mContext = getContext();
        searchView = view.findViewById(R.id.fragmentBookmark_searchBar);
        searchView.clearFocus();
        progressBar = view.findViewById(R.id.fragmentBookmark_progressBar);
        favoriteRoomList = view.findViewById(R.id.fragmentBookmark_roomListView);
        favoriteRoomList.setLayoutManager(new LinearLayoutManager(getContext()));
        favoriteRoomList.setHasFixedSize(true);
        hotelNotFound = view.findViewById(R.id.fragmentBookmark_roomNotFound);
        favoriteRoomList.setVisibility(View.VISIBLE);
        hotelNotFound.setVisibility(View.INVISIBLE);
        filterButton  = (ImageButton)view.findViewById(R.id.fragmentBookmark_filter);

        //Fillter button presed
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog();
            }
        });

        //Search bar
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String searchRoom) {
                filterListRoom(searchRoom, favoriteRooms);
                return true;
            }
        });

        //Get favorite room of an account
        getFavoriteRoom();
        return view;
    }

    //Filter of search bar
    private void filterListRoom(String searchRoom, List<Room> Room) {
        List<Room> searchedRoom = new ArrayList<>();
        for(Room room : Room){
            if(room.getName().toLowerCase().contains(searchRoom.toLowerCase())){
                searchedRoom.add(room);
                favoriteRoomList.setVisibility(View.VISIBLE);
                hotelNotFound.setVisibility(View.INVISIBLE);
            }
        }

        if(searchedRoom.isEmpty()){
            Toast.makeText(mContext, "No Data Found", Toast.LENGTH_SHORT).show();
            favoriteRoomList.setVisibility(View.INVISIBLE);
            hotelNotFound.setVisibility(View.VISIBLE);
        }else{
            roomAdapter.setFilteredList(searchedRoom);
            favoriteRoomList.setVisibility(View.VISIBLE);
            hotelNotFound.setVisibility(View.INVISIBLE);
        }
    }

    //Get favorite room of an account
    protected  List<Room> getFavoriteRoom() {
        mApiService.getFavoriteRoom(LoginActivity.accountLogin.id).enqueue(new Callback<List<Room>>() {
            @Override
            public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    favoriteRooms = response.body();
                    roomAdapter = new RoomAdapter(favoriteRooms, getContext(), (view, position) -> {
                        selectedFavoriteRoom = position;
                        RoomDetailsActivity.roomSelected = getFavoriteRoomSelected();
                        Intent intent = new Intent(getContext(), RoomDetailsActivity.class);
                        startActivity(intent);
                    });

                    favoriteRoomList.setAdapter(roomAdapter);
                    roomAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Room>> call, Throwable t) {

            }
        });
        return null;
    }

    //Filter room by city
    private void filterListRoomCity(List<String> Cities, List<Room> Room) {
        List<Room> searchedRoom = new ArrayList<>();
        for(Room room : Room){
            for(String city : Cities){
                if(room.city.toString().equalsIgnoreCase(city)){
                    searchedRoom.add(room);
                    favoriteRoomList.setVisibility(View.VISIBLE);
                    hotelNotFound.setVisibility(View.INVISIBLE);

                }
            }
        }

        if(searchedRoom.isEmpty()){
            Toast.makeText(mContext, "No Data Found", Toast.LENGTH_SHORT).show();
            favoriteRoomList.setVisibility(View.INVISIBLE);
            hotelNotFound.setVisibility(View.VISIBLE);

        }else{
            roomAdapter.setFilteredList(searchedRoom);
            favoriteRoomList.setVisibility(View.VISIBLE);
            hotelNotFound.setVisibility(View.INVISIBLE);

        }
    }

    //Alert dialog for filter room by city
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
                        filterListRoomCity(filterCity,favoriteRooms);
                        Log.d("list", itemsSelected.toString());
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        getFavoriteRoom();
                        roomAdapter.setFilteredList(favoriteRooms);

                    }
                });

        dialog = builder.create();

        dialog.show();
    }

    //Get room data that selected on recycler view
    private Room getFavoriteRoomSelected(){
        return favoriteRooms.get(selectedFavoriteRoom);
    }
}