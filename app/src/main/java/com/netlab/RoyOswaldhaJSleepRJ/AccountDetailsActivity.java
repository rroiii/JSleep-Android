package com.netlab.RoyOswaldhaJSleepRJ;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class AccountDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_details);
        TextView acccountName = findViewById(R.id.name2AccountDetail);
        acccountName.setText(LoginActivity.accountLogin.name);
        TextView accountEmail = findViewById(R.id.email2AccountDetail);
        accountEmail.setText(LoginActivity.accountLogin.email);
        TextView accountBalance = findViewById(R.id.balance2AccountDetail);
        String balanceToString = String.valueOf(LoginActivity.accountLogin.balance);
        accountBalance.setText(balanceToString);
    }
}