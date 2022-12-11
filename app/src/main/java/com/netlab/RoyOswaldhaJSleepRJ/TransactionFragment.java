package com.netlab.RoyOswaldhaJSleepRJ;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.netlab.RoyOswaldhaJSleepRJ.model.Payment;
import com.netlab.RoyOswaldhaJSleepRJ.model.Room;
import com.netlab.RoyOswaldhaJSleepRJ.request.BaseApiService;
import com.netlab.RoyOswaldhaJSleepRJ.request.UtilsApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransactionFragment extends Fragment implements PaymentAdapter.PaymentClickListener, HistoryPaymentAdapter.PaymentClickListener {
    BaseApiService mApiService;
    Context mContext;
    public static List<Payment> paymentWaiting, paymentHistory;
    private PaymentAdapter paymentWaitingAdapter;
    private HistoryPaymentAdapter paymentHistoryAdapter;
    private RecyclerView paymentWaitingListView, paymentHistoryListView;
    public static int selectedPaymentOnGoing, selectedPaymentHistory;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transaction, container, false);
        mApiService = UtilsApi.getApiService();
        mContext = getContext();

        paymentWaitingListView = view.findViewById(R.id.fragmentTransaction_paymentWaiting);
        paymentWaitingListView.setLayoutManager(new LinearLayoutManager(getContext()));
        paymentWaitingListView.setHasFixedSize(true);
        paymentWaitingListView.setNestedScrollingEnabled(false);
        paymentHistoryListView = view.findViewById(R.id.fragmentTransaction_paymentHistoryList);
        paymentHistoryListView.setLayoutManager(new LinearLayoutManager(getContext()));
        paymentHistoryListView.setHasFixedSize(true);
        paymentHistoryListView.setNestedScrollingEnabled(false);


        if(LoginActivity.accountLogin.renter == null){
            getPaymentWaitingListAsBuyer(LoginActivity.accountLogin.id);
            getPaymentHistoryListAsBuyer(LoginActivity.accountLogin.id);
        }
        if(LoginActivity.accountLogin.renter != null){
            getPaymentWaitingListAsRenter(LoginActivity.accountLogin.id);
            getPaymentHistoryListAsRenter(LoginActivity.accountLogin.id);
        }

        return view;
    }

    protected List<Payment> getPaymentWaitingListAsBuyer(int buyerId){
        mApiService.getPaymentWaitingBuyer(buyerId).enqueue(new Callback<List<Payment>>() {
            @Override
            public void onResponse(Call<List<Payment>> call, Response<List<Payment>> response) {
                if(response.isSuccessful()){
                    paymentWaiting = response.body();

                    paymentWaitingAdapter = new PaymentAdapter(paymentWaiting,getContext(),(view, position) -> {
                            selectedPaymentOnGoing = position;
                            Intent intent = new Intent(mContext,PayActivity.class);
                            startActivity(intent);
                    });

                    paymentWaitingListView.setAdapter(paymentWaitingAdapter);
                    paymentWaitingAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Payment>> call, Throwable t) {
                t.printStackTrace();
            }
        });
        return null;
    }

    protected List<Payment> getPaymentHistoryListAsBuyer(int buyerId){
        mApiService.getPaymentHistoryBuyer(buyerId).enqueue(new Callback<List<Payment>>() {
            @Override
            public void onResponse(Call<List<Payment>> call, Response<List<Payment>> response) {
                if(response.isSuccessful()) {
                    paymentHistory = response.body();

                    paymentHistoryAdapter = new HistoryPaymentAdapter(paymentHistory,getContext(),(view, position) -> {
                            selectedPaymentHistory = position;
                            Intent intent = new Intent(mContext, HistoryInvoiceActivity.class);
                            startActivity(intent);
                    });

                    paymentHistoryListView.setAdapter(paymentHistoryAdapter);
                    paymentHistoryAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(Call<List<Payment>> call, Throwable t) {

            }
        });
        return null;
    }

    protected List<Payment> getPaymentHistoryListAsRenter(int renterId){
        mApiService.getPaymentHistoryRenter(renterId).enqueue(new Callback<List<Payment>>() {
            @Override
            public void onResponse(Call<List<Payment>> call, Response<List<Payment>> response) {
                paymentHistory = response.body();
                paymentHistoryAdapter = new HistoryPaymentAdapter(paymentHistory,getContext(),(view, position) -> {
                    selectedPaymentHistory = position;
                    Intent intent = new Intent(mContext,HistoryInvoiceActivity.class);
                    startActivity(intent);
                });

                paymentHistoryListView.setAdapter(paymentHistoryAdapter);
                paymentHistoryAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<Payment>> call, Throwable t) {

            }
        });
        return null;
    }

    protected List<Payment> getPaymentWaitingListAsRenter(int renterId){
        mApiService.getPaymentWaitingRenter(renterId).enqueue(new Callback<List<Payment>>() {
            @Override
            public void onResponse(Call<List<Payment>> call, Response<List<Payment>> response) {
                paymentWaiting = response.body();

                paymentWaitingAdapter = new PaymentAdapter(paymentWaiting,getContext(),(view, position) -> {
                    selectedPaymentOnGoing = position;
                    Intent intent = new Intent(mContext,PayActivity.class);
                    startActivity(intent);
                });

                paymentWaitingListView.setAdapter(paymentWaitingAdapter);
                paymentWaitingAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<Payment>> call, Throwable t) {

            }
        });
        return null;
    }

    @Override
    public void onItemClick(View view, int position){
    }

    public static Payment getPaymentSelectedOnGoing(){
        return paymentWaiting.get(selectedPaymentOnGoing);
    }

    public static Payment getPaymentSelectedHistory(){
        return paymentHistory.get(selectedPaymentHistory);
    }
}