package com.netlab.RoyOswaldhaJSleepRJ;

import retrofit2.*;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.gson.Gson;
import com.netlab.RoyOswaldhaJSleepRJ.model.*;
import com.netlab.RoyOswaldhaJSleepRJ.request.*;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationMenu;
    BaseApiService mApiService;
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mApiService = UtilsApi.getApiService();
        mContext = this;

        //Get login session data
        Gson gson = new Gson();
        String jsonAccount = LoginActivity.preferences.getString("Account", "");
        String jsonRenter = LoginActivity.preferences.getString("Renter", "");

        LoginActivity.accountLogin  = gson.fromJson(jsonAccount, Account.class);
        LoginActivity.accountLogin.renter = gson.fromJson(jsonRenter, Renter.class);

        //Update account data every 2s
        final Handler handler = new Handler();
        Runnable refresh = new Runnable() {
            @Override
            public void run() {
                getAccount();
                handler.postDelayed(this, 2000);
            }
        };
        handler.postDelayed(refresh, 2000);

        //Bottom navigation menu
        bottomNavigationMenu = findViewById(R.id.main_bottomNavigationView);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new MainFragment()).commit();

        bottomNavigationMenu.setSelectedItemId(R.id.bottomMenu_mainButton);
        bottomNavigationMenu.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch(item.getItemId()){
                    case R.id.bottomMenu_bookmarkButton:
                        fragment = new BookmarkFragment();
                        break;
                    case R.id.bottomMenu_mainButton:
                        fragment = new MainFragment();
                        break;
                    case R.id.bottomMenu_paymentListButton:
                        fragment = new TransactionFragment();
                        break;
                    case R.id.bottomMenu_profileMenu:
                        fragment = new ProfileFragment();
                        break;
                    case R.id.bottomMenu_voucherButton:
                        fragment = new VoucherFragment();
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.main_container, fragment).commit();

                return true;
            }

        });
    }

    //Get account data
    protected Account getAccount(){
        mApiService.getAccount(LoginActivity.accountLogin.id).enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                LoginActivity.accountLogin = response.body();
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {

            }
        });
        return null;
    }
}