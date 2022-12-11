package com.netlab.RoyOswaldhaJSleepRJ;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.netlab.RoyOswaldhaJSleepRJ.model.RoomFacility;

import java.util.List;

//Adapter to show room facilities into recycler view on room details activity
public class FacilityAdapter extends RecyclerView.Adapter<FacilityAdapter.FacilityViewHolder> {
    private List<RoomFacility> rooms;
    private Context context;

    public FacilityAdapter(List<RoomFacility> rooms, Context context) {
        this.rooms = rooms;
        this.context = context;
    }

    @NonNull
    @Override
    public FacilityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.facility_item, parent, false);
        return new FacilityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FacilityViewHolder holder, int position) {
        holder.name.setText(rooms.get(position).name);
        Glide.with(context).load(rooms.get(position).icon).override(500,500).into(holder.icon);
    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }

    public static class FacilityViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView icon;
        public FacilityViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.facilityList_name);
            icon = itemView.findViewById(R.id.facilityList_icon);

        }


    }


}