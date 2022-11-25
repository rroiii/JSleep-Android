package com.netlab.RoyOswaldhaJSleepRJ;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.netlab.RoyOswaldhaJSleepRJ.model.Account;
import com.netlab.RoyOswaldhaJSleepRJ.model.Renter;
import com.netlab.RoyOswaldhaJSleepRJ.request.BaseApiService;
import com.netlab.RoyOswaldhaJSleepRJ.request.UtilsApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountDetailsActivity extends AppCompatActivity {
    BaseApiService mApiService;
    EditText renterName, renterAddress, renterPhoneNumber;
    Context mContext;
    CardView accountRenter, registerRenterCD;
    Button registerRenterButton, registerButton, cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_details);
        mApiService = UtilsApi.getApiService();
        mContext = this;

        TextView acccountName = findViewById(R.id.name2AccountDetail);
        acccountName.setText(LoginActivity.accountLogin.name);

        TextView accountEmail = findViewById(R.id.email2AccountDetail);
        accountEmail.setText(LoginActivity.accountLogin.email);

        TextView accountBalance = findViewById(R.id.balance2AccountDetail);
        String balanceToString = String.valueOf(LoginActivity.accountLogin.balance);
        accountBalance.setText(balanceToString);

        accountRenter = (CardView) findViewById(R.id.accountRenter);
        registerRenterCD = (CardView) findViewById(R.id.registerRenter);

        cancelButton = (Button)findViewById(R.id.cancelRenter);
        registerButton = (Button)findViewById(R.id.registerRenterButton2);
        registerRenterButton = (Button)findViewById(R.id.registerRenterButton);

        renterName = findViewById(R.id.renterNameRegister);
        renterAddress = findViewById(R.id.renterAddressRegister);
        renterPhoneNumber = findViewById(R.id.renterPhoneRegister);

        if(LoginActivity.accountLogin.renter==null){
            registerRenterButton.setVisibility(View.VISIBLE);
            accountRenter.setVisibility(View.INVISIBLE);
            registerRenterCD.setVisibility(View.INVISIBLE);
        }
        if(LoginActivity.accountLogin.renter!=null){
            registerRenterButton.setVisibility(View.INVISIBLE);
            accountRenter.setVisibility(View.VISIBLE);
            registerRenterCD.setVisibility(View.INVISIBLE);

            TextView renterName = findViewById(R.id.renterNameOutput);
            renterName.setText(LoginActivity.accountLogin.renter.username);
            TextView renterAddress = findViewById(R.id.renterAddressOutput);
            renterAddress.setText(LoginActivity.accountLogin.renter.address);
            TextView renterPhone = findViewById(R.id.renterPhoneOutput);
            renterPhone.setText(LoginActivity.accountLogin.renter.phoneNumber);
        }

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerRenterButton.setVisibility(View.VISIBLE);
                accountRenter.setVisibility(View.INVISIBLE);
                registerRenterCD.setVisibility(View.INVISIBLE);
            }
        });

        registerRenterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerRenterButton.setVisibility(View.INVISIBLE);
                accountRenter.setVisibility(View.INVISIBLE);
                registerRenterCD.setVisibility(View.VISIBLE);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Account account = requestRenter();
                registerRenterButton.setVisibility(View.INVISIBLE);
                accountRenter.setVisibility(View.VISIBLE);
                registerRenterCD.setVisibility(View.INVISIBLE);
            }
        });
    }

    protected Account requestRenter(){
        mApiService.requestRenter(0,renterName.getText().toString(),renterAddress.getText().toString(),renterPhoneNumber.getText().toString()).enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if(response.isSuccessful()){
                    Toast.makeText(mContext, "Renter Registered!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                Toast.makeText(mContext, "Renter Register Failed!", Toast.LENGTH_LONG).show();
            }
        });
        return null;
    }
}