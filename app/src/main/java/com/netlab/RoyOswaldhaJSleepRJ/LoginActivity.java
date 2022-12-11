package com.netlab.RoyOswaldhaJSleepRJ;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.netlab.RoyOswaldhaJSleepRJ.model.Account;
import com.netlab.RoyOswaldhaJSleepRJ.request.BaseApiService;
import com.netlab.RoyOswaldhaJSleepRJ.request.UtilsApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    public static SharedPreferences  preferences;
    private TextView registerButton;
    private Button loginButton;
    public static Account accountLogin;
    BaseApiService mApiService;
    EditText email, password;
    Context mContext;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mApiService = UtilsApi.getApiService();
        mContext = this;

        //Create login session
        preferences  = getPreferences(MODE_PRIVATE);;
        if (preferences.contains("Account")) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }

        loginButton = (Button) findViewById(R.id.loginActivity_loginButton);
        registerButton = findViewById(R.id.loginActivity_signUp);

        email = findViewById(R.id.loginActivity_emailForm);
        password = findViewById(R.id.loginActivity_passwordForm);

        //Make new account
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegisterActivity();
            }
        });

        loginButton = (Button)findViewById(R.id.loginActivity_loginButton);
        //Login
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Account account = requestLogin();
            }
        });


    }

    //Login
    protected Account requestLogin(){
        mApiService.requestLogin(email.getText().toString(), password.getText().toString()).enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if(response.isSuccessful()){
                    accountLogin = response.body();

                    //Login session
                    SharedPreferences.Editor editor = preferences.edit();
                    Gson gson = new Gson();
                    String jsonAccount = gson.toJson(accountLogin);
                    editor.putString("Account", jsonAccount);

                    String jsonRenter = gson.toJson(accountLogin.renter);
                    editor.putString("Renter", jsonRenter);


                    editor.commit();

                    Toast.makeText(mContext, "Login Success!", Toast.LENGTH_LONG).show();
                    openMainActivity();
                }
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                Toast.makeText(mContext, "Login Failed!", Toast.LENGTH_LONG).show();
            }
        });
        return null;
    }

    public void openRegisterActivity(){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void openMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}