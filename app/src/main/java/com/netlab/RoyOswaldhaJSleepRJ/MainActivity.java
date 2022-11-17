package com.netlab.RoyOswaldhaJSleepRJ;

import androidx.appcompat.app.AppCompatActivity;

import android.content.*;
import android.os.Bundle;
import android.view.*;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.reflect.TypeToken;
import com.netlab.RoyOswaldhaJSleepRJ.model.Room;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    ArrayList<String> nameRoom  = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.listView);

        String jsonFileString = loadJson();

        Gson gson = new Gson();
        Type listUserType = new TypeToken<List<Room>>() { }.getType();
        ArrayList<Room> room = gson.fromJson(jsonFileString, listUserType);

        //<Room> adapter = new ArrayAdapter<Room>(getApplicationContext(), R.layout.activity_main, room);
        //listView.setAdapter(adapter);


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

    public String loadJson() {
        String jsonString;
        try {
            InputStream is = this.getAssets().open("randomList.json");

            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            jsonString = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return jsonString;
    }
}