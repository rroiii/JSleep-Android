package com.netlab.RoyOswaldhaJSleepRJ;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.content.*;
import android.os.Bundle;
import android.view.*;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.reflect.TypeToken;
import com.netlab.RoyOswaldhaJSleepRJ.model.Account;
import com.netlab.RoyOswaldhaJSleepRJ.model.Room;
import com.google.gson.Gson;

import com.netlab.RoyOswaldhaJSleepRJ.request.BaseApiService;
import com.netlab.RoyOswaldhaJSleepRJ.request.RetrofitClient;
import com.netlab.RoyOswaldhaJSleepRJ.request.UtilsApi;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

import retrofit2.*;


public class MainActivity extends AppCompatActivity {
    BaseApiService mApiService;
    Context mContext;
    Button prevButton, nextButton, goButton;
    EditText page;

    List<String> nameStr;
    List<Room> temp ;
    List<Room> rooms ;

    private String pageString;
    public int pageInt, pageSize=10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mApiService = UtilsApi.getApiService();
        mContext = this;
        page = findViewById(R.id.pageText);
        String pageString = String.valueOf(pageInt);
        page.setText(pageString);

        rooms = getRoomList(1, pageSize);

        prevButton = (Button) findViewById(R.id.prev_button);
        nextButton = (Button) findViewById(R.id.next_button);
        goButton = (Button) findViewById(R.id.go_button);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pageInt++;
                int pageAmmount = (temp.size()/pageSize);

                if((temp.size()%pageSize) != 0){
                    pageAmmount = pageAmmount + 1;
                }
                if(pageInt>temp.size()){
                    pageInt=pageAmmount;
                    return;
                }
                String pageString = String.valueOf(pageInt);
                page.setText(pageString);
                try {
                    rooms = getRoomList(pageInt-1, pageSize);  //return null
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pageInt--;
                if(pageInt < 1){
                    pageInt = 1;
                    return;
                }
                String pageString = String.valueOf(pageInt);
                page.setText(pageString);
                try {
                    rooms = getRoomList(pageInt-1, pageSize);  //return null
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pageInt = 0;

                if (!"".equals(page.getText().toString())) {
                    pageInt = Integer.parseInt(page.getText().toString());
                }
                try {
                    rooms = getRoomList(pageInt-1, pageSize);  //return null
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    protected List<Room> getRoomList(int page, int pageSize) {
        //System.out.println(pageSize);
        mApiService.getAllRoom(page, pageSize).enqueue(new Callback<List<Room>>() {
            @Override
            public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
                if (response.isSuccessful()) {
                    temp = response.body();
                    nameStr = extractName(temp);
                    System.out.println("name extracted"+temp.toString());
                    ArrayAdapter<String> itemAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1,nameStr);
                    ListView listView = (ListView) findViewById(R.id.listView);
                    listView.setAdapter(itemAdapter);
                    Toast.makeText(mContext, "Success", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Room>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(mContext, "Failed", Toast.LENGTH_SHORT).show();
            }

        });
        return null;
    }

    public static ArrayList<String> extractName(List<Room> list) {
        ArrayList<String> ret = new ArrayList<String>();
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
        MenuItem add_button = menu.findItem(R.id.add_button);

        if(LoginActivity.accountLogin.renter==null){
            add_button.setVisible(false);
        }
        if(LoginActivity.accountLogin.renter!=null){
            add_button.setVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.search_button:
                return true;
            case R.id.profile_button:
                openProfile();
                return true;
            case R.id.add_button:
                addRoom();
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

    public void openProfile(){
        Intent profile  = new Intent(this, AccountDetailsActivity.class);
        startActivity(profile);
    }
    public void addRoom(){
        Intent addRoom = new Intent(this, CreateRoomActivity.class);
        startActivity(addRoom);
    }

}