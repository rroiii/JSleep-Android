package com.netlab.RoyOswaldhaJSleepRJ;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.netlab.RoyOswaldhaJSleepRJ.model.Type;
import com.netlab.RoyOswaldhaJSleepRJ.model.Voucher;
import com.netlab.RoyOswaldhaJSleepRJ.request.BaseApiService;
import com.netlab.RoyOswaldhaJSleepRJ.request.UtilsApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateVoucherActivity extends AppCompatActivity {
    BaseApiService mApiService;
    Context mContext;
    private EditText voucherName, voucherCode, voucherMinimum, voucherCut;
    private Button cancelCreateVoucherButton, createVoucherButton;
    private Type type;
    private Voucher voucher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_voucher);
        mApiService = UtilsApi.getApiService();
        mContext = this;

        //Voucher attribute input
        voucherName = (EditText)findViewById(R.id.CreateVoucherActivity_voucherNameInput);
        voucherCode = (EditText)findViewById(R.id.CreateVoucherActivity_voucherCodeInput);
        voucherMinimum = (EditText)findViewById(R.id.CreateVoucherActivity_voucherMinimumInput);
        voucherCut = (EditText)findViewById(R.id.CreateVoucherActivity_voucherCutInput);

        cancelCreateVoucherButton = (Button)findViewById(R.id.createVoucherCancelButton);
        createVoucherButton = (Button)findViewById(R.id.createVoucherButton);

        //Spinner
        Spinner spinnerVoucherType = (Spinner)findViewById(R.id.CreateVoucherActivity_voucherTypeInput);

        ArrayAdapter<Type> typeList = new ArrayAdapter<Type>(
                mContext,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                Type.values()
        );

        spinnerVoucherType.setAdapter(typeList);

        //Cancel button pressed
        cancelCreateVoucherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, MainActivity.class);
                startActivity(intent);
            }
        });

        //Create voucher button pressed
        createVoucherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = (Type) spinnerVoucherType.getSelectedItem();

                Voucher voucher = requestVoucher();
                Intent intent = new Intent(mContext, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    //Make new voucher
    protected Voucher requestVoucher(){
        mApiService.requestVoucher(voucherName.getText().toString(),
                Integer.parseInt(voucherCode.getText().toString()),
                this.type,
                Double.parseDouble(voucherMinimum.getText().toString()),
                Double.parseDouble(voucherCut.getText().toString())).enqueue(new Callback<Voucher>() {

            @Override
            public void onResponse(Call<Voucher> call, Response<Voucher> response) {
                voucher = response.body();
                Toast.makeText(mContext, "Voucher created!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Voucher> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(mContext, "Voucher didn't created!", Toast.LENGTH_LONG).show();
            }
        });
        return null;
    }
}