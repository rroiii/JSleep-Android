package com.netlab.RoyOswaldhaJSleepRJ;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.content.*;
import android.os.Bundle;
import android.view.*;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.reflect.TypeToken;
import com.netlab.RoyOswaldhaJSleepRJ.model.Account;
import com.netlab.RoyOswaldhaJSleepRJ.model.Room;
import com.google.gson.Gson;
import com.netlab.RoyOswaldhaJSleepRJ.model.roomAdapter;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.listView);


        Gson gson = new Gson();

        try {
            InputStream filepath = getAssets().open("randomRoomList.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(filepath));
            ArrayList<Room> roomList = new ArrayList<Room>();
            Room[] acc = gson.fromJson(reader, Room[].class);
            Collections.addAll(roomList, acc);
            roomAdapter roomListView = new roomAdapter(this, roomList);
            listView.setAdapter(roomListView);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    public static ArrayList<String> extractName(ArrayList<Room> list) {
        Gson gson = new Gson();
        ArrayList<String> ret = null;
        int i;
        for (i = 0; i < list.size(); i++) {
            ret.add(list.get(i).name);
        }
        return ret;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.search_button:
                return true;
            case R.id.profile_button:
                Intent intent = new Intent(this, AccountDetailsActivity.class);
                startActivity(intent);
                return true;
            case R.id.add_button:
                return true;
            default :
                return super.onOptionsItemSelected(item);
        }
    }

    public String loadJsonFromAsset() {
        try {
            InputStream is = MainActivity.this.getAssets().open("randomRoomList.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");
            return json;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }


}