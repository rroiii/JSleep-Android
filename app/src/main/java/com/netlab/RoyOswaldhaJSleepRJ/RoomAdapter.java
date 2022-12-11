package com.netlab.RoyOswaldhaJSleepRJ;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.netlab.RoyOswaldhaJSleepRJ.model.Room;


import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder> {

    private List<Room> rooms;
    private Context context;
    private static RoomClickListener roomClickListener;

    public void setFilteredList(List<Room> searchRoom){
        this.rooms = searchRoom;
        notifyDataSetChanged();
    }

    public RoomAdapter(List<Room> rooms, Context context, RoomClickListener roomClickListener) {
        this.rooms = rooms;
        this.context = context;
        this.roomClickListener = roomClickListener;
    }
    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.room_item, parent, false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RoomViewHolder holder, int position) {
        holder.name.setText(rooms.get(position).getName());
        holder.city.setText(rooms.get(position).getCity());
        holder.price.setText(rooms.get(position).getPrice());
        Glide.with(context).load(rooms.get(position).image).override(500,500).into(holder.image);
        holder.ratingBar.setRating(rooms.get(position).getRating());
    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }

    public interface RoomClickListener{
        void onItemClick(View view, int position);
    }

    public static class RoomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView name,city,price;
        ImageView image;
        RatingBar ratingBar;

        public RoomViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.roomList_roomName);
            city = itemView.findViewById(R.id.roomList_roomCity);
            price = itemView.findViewById(R.id.roomList_roomPrice);
            image = (ImageView)itemView.findViewById(R.id.roomList_roomImage);
            ratingBar = (RatingBar)itemView.findViewById(R.id.roomList_ratingBar);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view){
            roomClickListener.onItemClick(view, this.getLayoutPosition());
        }

    }


}