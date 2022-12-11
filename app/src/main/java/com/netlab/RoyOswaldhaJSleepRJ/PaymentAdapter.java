package com.netlab.RoyOswaldhaJSleepRJ;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.netlab.RoyOswaldhaJSleepRJ.model.Payment;
import com.netlab.RoyOswaldhaJSleepRJ.model.Room;

import java.util.ArrayList;
import java.util.List;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.PaymentViewHolder>{

    private List<Payment> payments;
    private Context context;
    private static PaymentAdapter.PaymentClickListener paymentClickListener;
    private Room selectedRoomPaymentAdapter;

    public PaymentAdapter(List<Payment> payments, Context context, PaymentAdapter.PaymentClickListener paymentClickListener) {
        this.payments = payments;
        this.context = context;
        this.paymentClickListener = paymentClickListener;
    }

    @NonNull
    @Override
    public PaymentAdapter.PaymentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.payment_item, parent, false);
        return new PaymentAdapter.PaymentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PaymentAdapter.PaymentViewHolder holder, int position) {
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

    public static class PaymentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView status, name, price;


        public PaymentViewHolder(View itemView) {
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
