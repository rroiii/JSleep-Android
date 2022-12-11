package com.netlab.RoyOswaldhaJSleepRJ;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.netlab.RoyOswaldhaJSleepRJ.model.Payment;
import com.netlab.RoyOswaldhaJSleepRJ.model.Voucher;
import com.netlab.RoyOswaldhaJSleepRJ.request.BaseApiService;
import com.netlab.RoyOswaldhaJSleepRJ.request.UtilsApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class VoucherFragment extends Fragment {
    public static List<Voucher> availableVoucher;
    private RecyclerView voucherListView;
    private VoucherAdapter voucherAdapter;
    BaseApiService mApiService;
    Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_voucher, container, false);
        mApiService = UtilsApi.getApiService();
        mContext = getContext();
        voucherListView = view.findViewById(R.id.fragmentVoucher_voucherList);
        voucherListView .setLayoutManager(new LinearLayoutManager(getContext()));
        voucherListView .setHasFixedSize(true);
        getAvailableVoucher();
        return view;
    }

    protected List<Voucher>getAvailableVoucher(){
        mApiService.getAvailableVoucher().enqueue(new Callback<List<Voucher>>() {
            @Override
            public void onResponse(Call<List<Voucher>> call, Response<List<Voucher>> response) {
                availableVoucher = response.body();
                voucherAdapter = new VoucherAdapter(availableVoucher,getContext(),(view, position) -> {

                });

                voucherListView.setAdapter(voucherAdapter);
                voucherAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Voucher>> call, Throwable t) {

            }
        });
        return null;
    }
}