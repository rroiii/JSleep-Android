package com.netlab.RoyOswaldhaJSleepRJ;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.netlab.RoyOswaldhaJSleepRJ.model.Account;
import com.netlab.RoyOswaldhaJSleepRJ.request.BaseApiService;
import com.netlab.RoyOswaldhaJSleepRJ.request.UtilsApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    public static Account accountRegister;
    private Button registerButton;
    BaseApiService mApiService;
    private EditText username, email, password;
    private TextView loginButton;

    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mApiService = UtilsApi.getApiService();
        mContext = this;

        username = findViewById(R.id.registerActivity_usernameForm);
        email = findViewById(R.id.registerAcitivity_emailForm);
        password = findViewById(R.id.registerActivity_passwordForm);

        loginButton = findViewById(R.id.registerActivity_signIn);
        registerButton = (Button)findViewById(R.id.registerActivity_registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Account account = requestRegister();
                openLoginActivity();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLoginActivity();
            }
        });
    }

    protected Account requestRegister(){
        mApiService.requestRegister(username.getText().toString(), email.getText().toString(), password.getText().toString()).enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if(response.isSuccessful()){
                    Account account = response.body();
                    System.out.println(account);
                    Toast.makeText(mContext, "Registered!", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                Toast.makeText(mContext, "Account already registered!", Toast.LENGTH_LONG).show();
            }
        });
        return null;
    }

    public void openLoginActivity(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}