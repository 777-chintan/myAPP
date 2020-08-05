package com.example.myapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private Context mCtx;
    private List<Order> OrderList;

    public OrderAdapter(Context context,List<Order> Orderlist){
        this.mCtx=context;
        this.OrderList=Orderlist;
    }

    @NonNull
    @Override
    public OrderAdapter.OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.OrderViewHolder holder, int position) {
        Order order = OrderList.get(position);
        holder.service.setText("Service : " + order.service);
        holder.price.setText("Price : " + order.price);
        holder.quantity.setText("Quantity : " + order.quantity);
        holder.total.setText("Total : " + order.total);
    }

    @Override
    public int getItemCount() {
        return OrderList.size();
    }

    class OrderViewHolder extends RecyclerView.ViewHolder {
        private Context context;
        TextView service, price, quantity,total;
        public OrderViewHolder(@NonNull final View itemView) {
            super(itemView);
            context=itemView.getContext();
            service = itemView.findViewById(R.id.service);
            price = itemView.findViewById(R.id.price);
            quantity = itemView.findViewById(R.id.quanity);
            total = itemView.findViewById(R.id.total);
        }
    }
}
