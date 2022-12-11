package com.netlab.RoyOswaldhaJSleepRJ;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.netlab.RoyOswaldhaJSleepRJ.model.Payment;
import com.netlab.RoyOswaldhaJSleepRJ.model.Voucher;
import com.netlab.RoyOswaldhaJSleepRJ.request.BaseApiService;
import com.netlab.RoyOswaldhaJSleepRJ.request.UtilsApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VoucherCanApplyActivity extends AppCompatActivity {
    public static List<Voucher> canApplyVoucher;
    private RecyclerView voucherCanApplyListView;
    private VoucherAdapter voucherAdapter;
    BaseApiService mApiService;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher_can_apply);
        mApiService = UtilsApi.getApiService();
        mContext = this;
        voucherCanApplyListView = findViewById(R.id.VoucherCanApplyActivity_voucherList);
        voucherCanApplyListView.setLayoutManager(new LinearLayoutManager(this));
        voucherCanApplyListView.setHasFixedSize(true);
        getApplyVoucher();
    }

    protected Payment useVoucher(int code) {
        mApiService.useVoucher(BookRoomActivity.newPayment.id, code).enqueue(new Callback<Payment>() {
            @Override
            public void onResponse(Call<Payment> call, Response<Payment> response) {
                BookRoomActivity.newPayment = response.body();
                BookRoomActivity.priceDiscount.setText(String.valueOf(BookRoomActivity.newPayment.price.discount));
                BookRoomActivity.totalPrice.setText(String.valueOf(BookRoomActivity.newPayment.price.price));
                Toast.makeText(mContext, "Cool, Voucher Applied!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Payment> call, Throwable t) {

            }
        });
        return null;
    }

    protected List<Voucher> getApplyVoucher() {
        mApiService.getApply(BookRoomActivity.newPayment.id).enqueue(new Callback<List<Voucher>>() {
            @Override
            public void onResponse(Call<List<Voucher>> call, Response<List<Voucher>> response) {
                canApplyVoucher = response.body();
                voucherAdapter = new VoucherAdapter(canApplyVoucher, mContext, (view, position) -> {
                    useVoucher(canApplyVoucher.get(position).code);
                });

                voucherCanApplyListView.setAdapter(voucherAdapter);
                voucherAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Voucher>> call, Throwable t) {

            }
        });
        return null;
    }
}