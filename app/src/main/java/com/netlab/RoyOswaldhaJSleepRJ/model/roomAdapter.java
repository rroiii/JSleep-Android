package com.netlab.RoyOswaldhaJSleepRJ.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.netlab.RoyOswaldhaJSleepRJ.R;
import com.netlab.RoyOswaldhaJSleepRJ.model.Room;



import java.util.ArrayList;
public class roomAdapter extends ArrayAdapter<Room>{
    public roomAdapter(Context context, ArrayList<Room> rooms) {
        super(context, 0, rooms);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Room room = getItem(position);

        if(convertView == null) convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_room_list, parent, false);
        TextView room_name = convertView.findViewById(R.id.profileName);
        room_name.setText(room.name);
        return convertView;
    }
}
