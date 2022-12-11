package com.netlab.RoyOswaldhaJSleepRJ;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.netlab.RoyOswaldhaJSleepRJ.model.Room;
import com.netlab.RoyOswaldhaJSleepRJ.model.Voucher;

import java.util.List;

public class VoucherAdapter extends RecyclerView.Adapter<VoucherAdapter.VoucherViewHolder> {

    private List<Voucher> vouchers;
    private Context context;
    private static VoucherClickListener voucherClickListener;

    public void setFilteredList(List<Voucher> searchVoucher){
        this.vouchers = searchVoucher;
        notifyDataSetChanged();
    }

    public VoucherAdapter(List<Voucher> rooms, Context context, VoucherClickListener voucherClickListener) {
        this.vouchers= rooms;
        this.context = context;
        this.voucherClickListener = voucherClickListener;
    }
    @NonNull
    @Override
    public VoucherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.voucher_item, parent, false);
        return new VoucherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VoucherViewHolder holder, int position) {
        String cutStr = vouchers.get(position).getCut().substring(0, vouchers.get(position).getCut().length()-2);
        String minimumStr = vouchers.get(position).getMinimum().substring(0, vouchers.get(position).getMinimum().length()-2);
        holder.name.setText(vouchers.get(position).getName());
        holder.minimum.setText("Minimum payment Rp " + minimumStr);
        if(vouchers.get(position).getType().equalsIgnoreCase("discount")){
            holder.cut.setText("Discount " + cutStr +"%");
        }else{
            holder.cut.setText("Cashback Rp " + cutStr);
        }



    }

    @Override
    public int getItemCount() {
        return vouchers.size();
    }

    public interface VoucherClickListener{
        void onItemClick(View view, int position);
    }

    public static class VoucherViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView name,minimum,cut;


        public VoucherViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.voucherList_name);
            minimum = itemView.findViewById(R.id.voucherList_minimum);
            cut = itemView.findViewById(R.id.voucherList_cut);


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view){
            voucherClickListener.onItemClick(view, this.getLayoutPosition());
        }
    }
}