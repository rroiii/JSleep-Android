package com.netlab.RoyOswaldhaJSleepRJ;



import android.content.Context;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.content.SharedPreferences;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.netlab.RoyOswaldhaJSleepRJ.model.Account;
import com.netlab.RoyOswaldhaJSleepRJ.model.Renter;
import com.netlab.RoyOswaldhaJSleepRJ.request.BaseApiService;
import com.netlab.RoyOswaldhaJSleepRJ.request.UtilsApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {
    BaseApiService mApiService;
    Context mContext;
    TextView accountNameOutput, accountEmailOutput, accountBalanceOutput, renterNameOutput,
            renterAddressOutput, renterPhoneNumberOutput, renterNull, renterNameText, renterAddressText, renterPhoneNumberText,
            topUpTitle, withDrawTitle;
    Button becomeRenter, registerRenter, cancelRegisterRenter, createRoom, manageRoom, topUpButton, withdrwaButton, logoutButton, createVoucher;
    EditText balanceInput, renterName, renterAddress, renterPhoneNumber;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        mApiService = UtilsApi.getApiService();
        mContext = getContext();

        //Account Info
        accountNameOutput = view.findViewById(R.id.fragmentProfile_accountNameOutput);
        accountEmailOutput = view.findViewById(R.id.fragmentProfile_accountEmailOutput);
        accountBalanceOutput = view.findViewById(R.id.fragmentProfile_accountBalanceOutput);

        accountNameOutput.setText(LoginActivity.accountLogin.name);
        accountEmailOutput.setText(LoginActivity.accountLogin.email);
        accountBalanceOutput.setText(LoginActivity.accountLogin.getBalance());

        //Logout
        logoutButton = (Button)view.findViewById(R.id.fragmentProfile_logoutButton);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = LoginActivity.preferences.edit();
                editor.clear();
                editor.commit();
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        //Top Up and withdraw
        topUpTitle = view.findViewById(R.id.fragmentProfile_topUpSectionTitle);
        withDrawTitle = view.findViewById(R.id.fragmentProfile_withDrawSectionTitle);
        balanceInput = (EditText) view.findViewById(R.id.fragmentProfile_balanceInput);
        topUpButton = (Button) view.findViewById(R.id.fragmentProfile_buttonTopUp);
        withdrwaButton = (Button) view.findViewById(R.id.fragmentProfile_buttonWithdraw);

        if(LoginActivity.accountLogin.renter == null){
            topUpButton.setVisibility(View.VISIBLE);
            topUpTitle.setVisibility(View.VISIBLE);

            withDrawTitle.setVisibility(View.INVISIBLE);
            withdrwaButton.setVisibility(View.INVISIBLE);
        }
        if(LoginActivity.accountLogin.renter != null){
            withDrawTitle.setVisibility(View.VISIBLE);
            withDrawTitle.setVisibility(View.VISIBLE);

            topUpButton.setVisibility(View.INVISIBLE);
            topUpTitle.setVisibility(View.INVISIBLE);
        }

        topUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestTopUp();
            }
        });

        withdrwaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestWithdraw();
            }
        });

        //Renter
        renterNull = view.findViewById(R.id.fragmentProfile_renterNullWelcoming);
        becomeRenter = (Button) view.findViewById(R.id.fragmentProfile_becomeRenter);

        renterName = (EditText)view.findViewById(R.id.fragmentProfile_renterNameInput);
        renterAddress = (EditText)view.findViewById(R.id.fragmentProfile_renterAddressInput);
        renterPhoneNumber = (EditText) view.findViewById(R.id.fragmentProfile_renterPhoneNumberInput);
        registerRenter = (Button) view.findViewById(R.id.fragmentProfile_registerRenter);
        cancelRegisterRenter = (Button) view.findViewById(R.id.fragmentProfile_cancelRegister);

        renterNameText = view.findViewById(R.id.fragmentProfile_renterName);
        renterAddressText = view.findViewById(R.id.fragmentProfile_renterAddress);
        renterPhoneNumberText = view.findViewById(R.id.fragmentProfile_renterPhoneNumber);
        renterNameOutput = view.findViewById(R.id.fragmentProfile_renterNameOutput);
        renterAddressOutput = view.findViewById(R.id.fragmentProfile_renterAddressOutput);
        renterPhoneNumberOutput = view.findViewById(R.id.fragmentProfile_renterPhoneNumberOutput);
        createRoom = (Button) view.findViewById(R.id.fragmentProfile_createRoom);
        manageRoom = (Button) view.findViewById(R.id.fragmentProfile_manageRoom);
        createVoucher = (Button) view.findViewById(R.id.fragmentProfile_createVoucher);

        if(LoginActivity.accountLogin.renter == null){
            renterNull();
        }

        if(LoginActivity.accountLogin.renter!=null){
            renterNotNull();
            renterNameOutput.setText(LoginActivity.accountLogin.renter.username);
            renterAddressOutput.setText(LoginActivity.accountLogin.renter.address);
            renterPhoneNumberOutput.setText(LoginActivity.accountLogin.renter.phoneNumber);
        }

        becomeRenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                renterName.setVisibility(View.VISIBLE);
                renterAddress.setVisibility(View.VISIBLE);
                renterPhoneNumber.setVisibility(View.VISIBLE);
                registerRenter.setVisibility(View.VISIBLE);
                cancelRegisterRenter.setVisibility(View.VISIBLE);

                renterNull.setVisibility(View.INVISIBLE);
                becomeRenter.setVisibility(View.INVISIBLE);

                renterNameText.setVisibility(View.INVISIBLE);
                renterAddressText.setVisibility(View.INVISIBLE);
                renterPhoneNumberText.setVisibility(View.INVISIBLE);
                renterNameOutput.setVisibility(View.INVISIBLE);
                renterAddressOutput.setVisibility(View.INVISIBLE);
                renterPhoneNumberOutput.setVisibility(View.INVISIBLE);
                createRoom.setVisibility(View.INVISIBLE);
                manageRoom.setVisibility(View.INVISIBLE);
                createVoucher.setVisibility(View.INVISIBLE);
            }
        });

        manageRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ManageRoomActivity.class);
                startActivity(intent);
            }
        });

        cancelRegisterRenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                renterNull();
            }
        });

        registerRenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestRenter();
                renterNotNull();
            }
        });

        createRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, CreateRoomActivity.class);
                startActivity(intent);
            }
        });

        createVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, CreateVoucherActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private void renterNull(){
        renterNull.setVisibility(View.VISIBLE);
        becomeRenter.setVisibility(View.VISIBLE);

        renterName.setVisibility(View.INVISIBLE);
        renterAddress.setVisibility(View.INVISIBLE);
        renterPhoneNumber.setVisibility(View.INVISIBLE);
        registerRenter.setVisibility(View.INVISIBLE);
        cancelRegisterRenter.setVisibility(View.INVISIBLE);

        renterNameText.setVisibility(View.INVISIBLE);
        renterAddressText.setVisibility(View.INVISIBLE);
        renterPhoneNumberText.setVisibility(View.INVISIBLE);
        renterNameOutput.setVisibility(View.INVISIBLE);
        renterAddressOutput.setVisibility(View.INVISIBLE);
        renterPhoneNumberOutput.setVisibility(View.INVISIBLE);
        createRoom.setVisibility(View.INVISIBLE);
        manageRoom.setVisibility(View.INVISIBLE);
        createVoucher.setVisibility(View.INVISIBLE);
    }

    private void renterNotNull(){
        renterNameText.setVisibility(View.VISIBLE);
        renterAddressText.setVisibility(View.VISIBLE);
        renterPhoneNumberText.setVisibility(View.VISIBLE);
        renterNameOutput.setVisibility(View.VISIBLE);
        renterAddressOutput.setVisibility(View.VISIBLE);
        renterPhoneNumberOutput.setVisibility(View.VISIBLE);
        createRoom.setVisibility(View.VISIBLE);
        manageRoom.setVisibility(View.VISIBLE);
        createVoucher.setVisibility(View.VISIBLE);

        renterNull.setVisibility(View.INVISIBLE);
        becomeRenter.setVisibility(View.INVISIBLE);

        renterName.setVisibility(View.INVISIBLE);
        renterAddress.setVisibility(View.INVISIBLE);
        renterPhoneNumber.setVisibility(View.INVISIBLE);
        registerRenter.setVisibility(View.INVISIBLE);
        cancelRegisterRenter.setVisibility(View.INVISIBLE);
    }

    //Register as renter
    protected Renter requestRenter(){
        mApiService.requestRenter(LoginActivity.accountLogin.id,renterName.getText().toString(),renterAddress.getText().toString(),renterPhoneNumber.getText().toString()).enqueue(new Callback<Renter>() {
            @Override
            public void onResponse(Call<Renter> call, Response<Renter> response) {
                LoginActivity.accountLogin.renter = response.body();
                renterNameOutput.setText(renterName.getText().toString());
                renterAddressOutput.setText(renterAddress.getText().toString());
                renterPhoneNumberOutput.setText(renterPhoneNumber.getText().toString());

                Gson gson = new Gson();
                String jsonRenter = gson.toJson(response.body());
                SharedPreferences.Editor editor = LoginActivity.preferences.edit();
                editor.putString("Renter", jsonRenter);
                editor.commit();

                Toast.makeText(mContext, "Become a Renter!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Renter> call, Throwable t) {
                Toast.makeText(mContext, "Failed!", Toast.LENGTH_LONG).show();
            }
        });
        return null;
    }

    protected Boolean requestTopUp(){
        mApiService.requestTopUp(LoginActivity.accountLogin.id,
                Double.parseDouble(balanceInput.getText().toString())).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.isSuccessful()){
                    double balanceAfterTopUp =  Double.parseDouble(balanceInput.getText().toString()) + LoginActivity.accountLogin.balance;
                    String balanceAfterTopUpStr = String.valueOf(balanceAfterTopUp);
                    accountBalanceOutput.setText("Rp " + balanceAfterTopUpStr.substring(0, balanceAfterTopUpStr.length()-2));
                    Toast.makeText(mContext, "Top Up Success!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(mContext, "Top Up Fail!", Toast.LENGTH_LONG).show();
            }
        });
        return null;
    }

    protected Boolean requestWithdraw(){
        mApiService.requestWithdraw(LoginActivity.accountLogin.id,
                Double.parseDouble(balanceInput.getText().toString())).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.isSuccessful()){
                    double balanceAfterWithdraw =  LoginActivity.accountLogin.balance - Double.parseDouble(balanceInput.getText().toString());
                    String balanceAfterWithdrawStr = String.valueOf(balanceAfterWithdraw);
                    accountBalanceOutput.setText("Rp " + balanceAfterWithdrawStr.substring(0, balanceAfterWithdrawStr.length()-2));
                    Toast.makeText(mContext, "Withdraw Success!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });
        return null;
    }
}