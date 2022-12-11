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
import com.bumptech.glide.request.RequestOptions;
import com.netlab.RoyOswaldhaJSleepRJ.model.Payment;
import com.netlab.RoyOswaldhaJSleepRJ.model.Room;


import java.util.ArrayList;
import java.util.List;

public class HistoryPaymentAdapter extends RecyclerView.Adapter<HistoryPaymentAdapter.HistoryPaymentViewHolder> {

    private List<Payment> payments;
    private Context context;
    private static PaymentClickListener paymentClickListener;
    private Room selectedRoomPaymentAdapter;

    public HistoryPaymentAdapter(List<Payment> payments, Context context, PaymentClickListener paymentClickListener) {
        this.payments = payments;
        this.context = context;
        this.paymentClickListener = paymentClickListener;
    }
    @NonNull
    @Override
    public HistoryPaymentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.payment_item, parent, false);
        return new HistoryPaymentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HistoryPaymentViewHolder holder, int position) {
        getRoom(payments.get(position).getRoomId());
        holder.status.setText(payments.get(position).status.toString());
        holder.name.setText(selectedRoomPaymentAdapter.name);
        String totalPriceStr = String.valueOf(payments.get(position).price.price);
        holder.price.setText("Rp " + totalPriceStr.substring(0, totalPriceStr.length()-2));
    }

    @Override
    public int getItemCount() {
        return payments.size();
    }

    public interface PaymentClickListener{
        void onItemClick(View view, int position);
    }

    public static class HistoryPaymentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView status, name, price;

        public HistoryPaymentViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.paymentList_roomName);
            status = itemView.findViewById(R.id.paymentList_paymentStatus);
            price = itemView.findViewById(R.id.paymentList_paymentPrice);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view){
            paymentClickListener.onItemClick(view, this.getLayoutPosition());
        }

    }

    //To get room data
    protected Room getRoom(int roomId){
        List<Room> findRoomById = new ArrayList<>();
        for(Room findRoom : MainFragment.allRooms){
            if(findRoom.id == roomId){
                selectedRoomPaymentAdapter = findRoom;
            }
        }
        return null;
    }

}